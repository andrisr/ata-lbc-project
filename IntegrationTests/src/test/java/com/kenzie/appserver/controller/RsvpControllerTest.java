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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
class RsvpControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    RsvpService rsvpService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getByName_validName_isSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();
        Rsvp rsvp = new Rsvp(name);
        rsvp.setAttending(true);
        Rsvp persistedRsvp = rsvpService.createRsvp(rsvp);

        mvc.perform(get("/rsvp/{name}", persistedRsvp.getName())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(jsonPath("isAttending")
                        .value(is(true)))
                .andExpect(status().isOk());
    }

    @Test
    public void createRsvp_CreateSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();
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

    @Test
    public void updateRsvp_validRsvp_isSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();
        Rsvp rsvp = new Rsvp(name);
        rsvp.setAttending(true);
        Rsvp persistedRsvp = rsvpService.createRsvp(rsvp);

        RsvpCreateRequest request = new RsvpCreateRequest();
        request.setName(name);
        request.setAttending(true);
        request.setPlus1Name("plus 1 name");

        mvc.perform(put("/rsvp")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)));

        mvc.perform(get("/rsvp/{name}", persistedRsvp.getName())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(jsonPath("plus1Name")
                        .value(is("plus 1 name")))
                .andExpect(status().isOk());

        ResultActions rsvpList = mvc
                .perform(get("/rsvp/{name}", persistedRsvp.getName()));

        System.out.println("response: " + rsvpList.andReturn().getResponse().getContentAsString());
    }
}