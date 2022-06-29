package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RsvpCreateRequest;
import com.kenzie.appserver.controller.model.RsvpResponse;
import com.kenzie.appserver.service.RsvpService;
import com.kenzie.appserver.service.model.Rsvp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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


//    @Test
//    public void getByName_Exists() throws Exception {
//        String id = UUID.randomUUID().toString();
//        String name = mockNeat.strings().valStr();
//
//        Rsvp rsvp = new Rsvp(name);
//        Rsvp persistedRsvp = rsvpService.createRsvp(rsvp);
//        mvc.perform(get("/rsvp/{name}", persistedRsvp.getId())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("name")
//                        .value(is(name)))
//                .andExpect(status().isOk());
//    }

//    @Test
//    public void createRsvp_CreateSuccessful() throws Exception {
//        String name = mockNeat.strings().valStr();
//
//        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
//        rsvpCreateRequest.setName(name);
//
//        mapper.registerModule(new JavaTimeModule());
//
//        mvc.perform(post("/rsvp")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(rsvpCreateRequest)))
////                .andExpect(jsonPath("id")
////                        .exists())
//                .andExpect(jsonPath("name")
//                        .value(is(name)))
//                .andExpect(status().isCreated());
//    }


    @Test
    public void can_get_rsvp() throws Exception {
        // GIVEN
        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());
        rsvpCreateRequest.setAttending(true);
        rsvpCreateRequest.setMealChoice("Beef");
        rsvpCreateRequest.setPlus1Name(mockNeat.strings().get());
        rsvpCreateRequest.setPlus1MealChoice("Salmon");


        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        // WHEN
        queryUtility.rsvpControllerClient.getRsvp(rsvpCreateRequest.getName())
                // THEN
                .andExpect(status().isOk());
//                .andExpect(jsonPath("name")
//                        .value(is(rsvpCreateRequest.getName())))
//                .andExpect(jsonPath("email")
//                        .value(is(rsvpCreateRequest.getEmail())))
//                .andExpect(jsonPath("isAttending")
//                        .value(is(rsvpCreateRequest.isAttending())));
    }

    @Test
    public void can_get_rsvp_by_attending() throws Exception {
        // GIVEN
        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());
        rsvpCreateRequest.setAttending(true);
        rsvpCreateRequest.setMealChoice("Beef");
        rsvpCreateRequest.setPlus1Name(mockNeat.strings().get());
        rsvpCreateRequest.setPlus1MealChoice("Salmon");

        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        // WHEN
        queryUtility.rsvpControllerClient.getRsvpByAttending(rsvpCreateRequest.getName())
                // THEN
                .andExpect(status().isOk());
//                .andExpect(jsonPath("name")
//                        .value(is(rsvpCreateRequest.getName())))
//                .andExpect(jsonPath("email")
//                        .value(is(rsvpCreateRequest.getEmail())))
//                .andExpect(jsonPath("isAttending")
//                        .value(is(rsvpCreateRequest.isAttending())));
    }

    @Test
    public void can_update_rsvp() throws Exception {
        // GIVEN
        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());
//        rsvpCreateRequest.setAttending();
        rsvpCreateRequest.setMealChoice(null);
        rsvpCreateRequest.setPlus1Name(null);
        rsvpCreateRequest.setPlus1MealChoice(null);

        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);

        // WHEN
        RsvpCreateRequest updateRequest = new RsvpCreateRequest();
        updateRequest.setName(rsvpCreateRequest.getName());
        updateRequest.setEmail(rsvpCreateRequest.getEmail());
        updateRequest.setAttending(true);
        updateRequest.setMealChoice("Beef");
        updateRequest.setPlus1Name(mockNeat.strings().get());
        updateRequest.setPlus1MealChoice("Salmon");

        queryUtility.rsvpControllerClient.updateRsvp(updateRequest)

                // THEN
                .andExpect(status().isOk());
//                .andExpect(jsonPath("name")
//                        .value(is(updateRequest.getName())))
//                .andExpect(jsonPath("email")
//                        .value(is(updateRequest.getEmail())))
//                .andExpect(jsonPath("isAttending")
//                        .value(is(updateRequest.isAttending())))
//                .andExpect(jsonPath("mealChoice")
//                        .value(is(updateRequest.getMealChoice())))
//                .andExpect(jsonPath("plus1Name")
//                        .value(is(updateRequest.getPlus1Name())))
//                .andExpect(jsonPath("plus1MealChoice")
//                        .value(is(updateRequest.getPlus1MealChoice())));
    }

    @Test
    public void can_delete_rsvp() throws Exception {
        // GIVEN
        RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest();
        rsvpCreateRequest.setName(mockNeat.strings().get());
        rsvpCreateRequest.setEmail(mockNeat.strings().get());

        // WHEN
        queryUtility.rsvpControllerClient.createRsvp(rsvpCreateRequest);
        queryUtility.rsvpControllerClient.deleteRsvp(rsvpCreateRequest.getName())
                // THEN
                .andExpect(status().isOk());
=======
        RsvpCreateRequest request = new RsvpCreateRequest();
        request.setName(name);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/rsvp")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().isOk());
    }

    @Test
    public void getByAttending_returnsListSuccessfully() throws Exception {
        String name1 = mockNeat.strings().valStr();
        Rsvp rsvp1 = new Rsvp(name1);
        rsvp1.setName(name1);
        System.out.println("top of test: " + rsvp1.getName());
        rsvp1.setAttending(true);
        rsvpService.createRsvp(rsvp1);

//        String name2 = mockNeat.strings().valStr();
//        Rsvp rsvp2 = new Rsvp(name2);
//        rsvp2.setName(name2);
//        rsvp2.setAttending(true);
//        rsvpService.createRsvp(rsvp2);
//
//        String name3 = mockNeat.strings().valStr();
//        Rsvp rsvp3 = new Rsvp(name3);
//        rsvp3.setName(name3);
//        rsvp3.setAttending(false);
//        rsvpService.createRsvp(rsvp3);
//
//        String name4 = mockNeat.strings().valStr();
//        Rsvp rsvp4 = new Rsvp(name4);
//        rsvp4.setName(name4);
//        rsvp4.setAttending(false);
//        rsvpService.createRsvp(rsvp4);
//
//        String name5 = mockNeat.strings().valStr();
//        Rsvp rsvp5 = new Rsvp(name5);
//        rsvp5.setName(name5);
//        rsvp5.setAttending(true);
//        rsvpService.createRsvp(rsvp5);

        mvc.perform(get("/rsvp/{name}", rsvp1.getName())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name")
                        .value(is(name1)))
                .andExpect(status().isOk());

        ResultActions rsvpList = mvc
                .perform(get("/rsvp/{name}", rsvp1.getName()));

        System.out.println("response: " + rsvpList.andReturn().getResponse().getContentAsString());
    }
}