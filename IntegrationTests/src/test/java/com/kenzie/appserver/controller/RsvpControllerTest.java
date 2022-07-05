package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RsvpCreateRequest;
import com.kenzie.appserver.controller.model.RsvpResponse;
import com.kenzie.appserver.repositories.model.RsvpRecord;
import com.kenzie.appserver.service.RsvpService;
import com.kenzie.appserver.service.model.Rsvp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RsvpControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    RsvpService rsvpService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    private QueryUtility queryUtility;

    @BeforeAll
    public void setup() {queryUtility = new QueryUtility(mvc);}


   @Test
    public void getRsvp_validName_isSuccessful() throws Exception {
        // GIVEN
        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());

        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        // WHEN
        queryUtility.rsvpControllerClient.getRsvp(rsvpCreateRequest.getName())
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("name")
                        .value(is(rsvpCreateRequest.getName())))
                .andExpect(jsonPath("email")
                        .value(is(rsvpCreateRequest.getEmail())));
    }

    @Test
    public void getRsvp_invalidName_throwsException() throws Exception {
        assertThrows(NestedServletException.class, () -> {
            queryUtility.rsvpControllerClient.getRsvp("invalidName");
        });
    }

    @Test
    public void createRsvp_validInput_isSuccessful() throws Exception {
        // GIVEN
        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());

        // WHEN
        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest)
                // THEN
                .andExpect(jsonPath("name")
                        .value(is(rsvpCreateRequest.getName())))
                .andExpect(jsonPath("email")
                        .value(is(rsvpCreateRequest.getEmail())))
                .andExpect(status().isOk());
    }

    @Test
    public void createRsvp_invalidInput_throwsException() {
        RsvpCreateRequest request = new RsvpCreateRequest();
        request.setName(null);
        request.setEmail(mockNeat.strings().get());

        assertThrows(NestedServletException.class, () -> {
            queryUtility.rsvpControllerClient.createRsvp(request);
        });

        request.setName("");
        request.setEmail(mockNeat.strings().get());

        assertThrows(NestedServletException.class, () -> {
            queryUtility.rsvpControllerClient.createRsvp(request);
        });

        request.setName(mockNeat.strings().get());
        request.setEmail(null);

        assertThrows(NestedServletException.class, () -> {
            queryUtility.rsvpControllerClient.createRsvp(request);
        });

        request.setName(mockNeat.strings().get());
        request.setEmail("");

        assertThrows(NestedServletException.class, () -> {
            queryUtility.rsvpControllerClient.createRsvp(request);
        });
    }

    @Test
    public void findAllRsvps() throws Exception {
        // GIVEN
        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());
        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        RsvpCreateRequest updateRequest = new RsvpCreateRequest();
        updateRequest.setName(rsvpCreateRequest.getName());
        updateRequest.setEmail(rsvpCreateRequest.getEmail());
        updateRequest.setAttending(true);
        queryUtility.rsvpControllerClient.updateRsvp(updateRequest);

        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());
        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        updateRequest.setName(rsvpCreateRequest.getName());
        updateRequest.setEmail(rsvpCreateRequest.getEmail());
        updateRequest.setAttending(false);
        queryUtility.rsvpControllerClient.updateRsvp(updateRequest);

        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());
        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        updateRequest.setName(rsvpCreateRequest.getName());
        updateRequest.setEmail(rsvpCreateRequest.getEmail());
        updateRequest.setAttending(true);
        queryUtility.rsvpControllerClient.updateRsvp(updateRequest);

        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());
        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        updateRequest.setName(rsvpCreateRequest.getName());
        updateRequest.setEmail(rsvpCreateRequest.getEmail());
        updateRequest.setAttending(true);
        queryUtility.rsvpControllerClient.updateRsvp(updateRequest);

        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());
        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        updateRequest.setName(rsvpCreateRequest.getName());
        updateRequest.setEmail(rsvpCreateRequest.getEmail());
        updateRequest.setAttending(false);
        queryUtility.rsvpControllerClient.updateRsvp(updateRequest);

        // WHEN/THEN

        int serviceLength = rsvpService.findAll().size();

        MockHttpServletResponse resultActions = queryUtility.rsvpControllerClient.getAllRsvps()
                .andReturn().getResponse();

//        List<RsvpResponse> rsvpResponses = mapper.readValue(resultActions, RsvpResponse.class);

        System.out.println("restultActions: " + resultActions);

        int resultActionsSize = resultActions.getContentLength();

        Assertions.assertEquals(serviceLength, resultActionsSize);
    }

    @Test
    public void updateRsvp_validInput_isSuccessful() throws Exception {
        // GIVEN
        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());

        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        // WHEN
        RsvpCreateRequest updateRequest = new RsvpCreateRequest();
        updateRequest.setName(rsvpCreateRequest.getName());
        updateRequest.setEmail(rsvpCreateRequest.getEmail());
        updateRequest.setAttending(true);
        updateRequest.setMealChoice("Beef");
        updateRequest.setPlus1Name(mockNeat.strings().get());
        updateRequest.setPlus1MealChoice("Salmon");

        queryUtility.rsvpControllerClient.updateRsvp(updateRequest);

        queryUtility.rsvpControllerClient.getRsvp(rsvpCreateRequest.getName())
                // THEN
                .andExpect(jsonPath("name")
                        .value(is(updateRequest.getName())))
                .andExpect(jsonPath("name")
                        .value(is(updateRequest.getName())))
                .andExpect(jsonPath("email")
                        .value(is(updateRequest.getEmail())))
                .andExpect(jsonPath("isAttending")
                        .value(is(updateRequest.getIsAttending())))
                .andExpect(jsonPath("mealChoice")
                        .value(is(updateRequest.getMealChoice())))
                .andExpect(jsonPath("plus1Name")
                        .value(is(updateRequest.getPlus1Name())))
                .andExpect(jsonPath("plus1MealChoice")
                        .value(is(updateRequest.getPlus1MealChoice())))
                .andExpect(status().isOk());

        RsvpRecord record = rsvpService.findByName(rsvpCreateRequest.getName());
    }

    @Test
    public void updateRsvp_invalidName_throwsException() {
        assertThrows(NestedServletException.class, () -> {
            queryUtility.rsvpControllerClient.getRsvp("invalidName");
        });
    }

    @Test
    public void deleteRsvp_validName_isSuccessful() throws Exception {
        // GIVEN
        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());

        // WHEN
        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        queryUtility.rsvpControllerClient.deleteRsvp(rsvpCreateRequest.getName())
                // THEN
                .andExpect(status().isOk());

        assertThrows(NestedServletException.class, () -> {
            queryUtility.rsvpControllerClient.getRsvp("invalidName");
        });
    }

    @Test
    public void deleteRsvp_invalidName_throwsException() {
        assertThrows(NestedServletException.class, () -> {
            queryUtility.rsvpControllerClient.getRsvp("invalidName");
        });
    }
}