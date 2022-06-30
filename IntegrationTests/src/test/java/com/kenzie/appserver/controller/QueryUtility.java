package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.RsvpCreateRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class QueryUtility {

    public RsvpControllerClient rsvpControllerClient;

    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public QueryUtility(MockMvc mvc) {
        this.mvc = mvc;
        this.rsvpControllerClient = new RsvpControllerClient();
    }

    public class RsvpControllerClient {
        public ResultActions getRsvp(String name) throws Exception {
            return mvc.perform(get("/rsvp/{name}", name)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions getRsvpByAttending(boolean attending) throws Exception {
            System.out.println("query get by attending");
            return mvc.perform(get("/rsvp/attending/{attending}", attending)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions createRsvp(RsvpCreateRequest rsvpCreateRequest) throws Exception {
            return mvc.perform(post("/rsvp/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(rsvpCreateRequest)));
        }

        public ResultActions updateRsvp(RsvpCreateRequest rsvpCreateRequest) throws Exception {
            return mvc.perform(put("/rsvp/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(rsvpCreateRequest)));
        }

        public ResultActions deleteRsvp(String name) throws Exception {
            return mvc.perform(delete("/rsvp/{name}",name)
                    .accept(MediaType.APPLICATION_JSON));
        }
    }

    }

