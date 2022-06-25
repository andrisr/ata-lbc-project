package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RsvpCreateRequest;
import com.kenzie.appserver.controller.model.RsvpResponse;
import com.kenzie.appserver.service.RsvpService;

import com.kenzie.appserver.service.model.Rsvp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/rsvp")
public class RsvpController {

    private final RsvpService rsvpService;

    RsvpController(RsvpService rsvpService) {
        this.rsvpService = rsvpService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<RsvpResponse> get(@PathVariable("name") String name) {

        Rsvp rsvp = rsvpService.findByName(name);
        if (rsvp == null) {
            return ResponseEntity.notFound().build();
        }

        RsvpResponse rsvpResponse = new RsvpResponse();
        rsvpResponse.setName(rsvp.getName());
        return ResponseEntity.ok(rsvpResponse);
    }

    @GetMapping("/{attending}")
    public List<ResponseEntity<RsvpResponse>> get(@PathVariable("attending") boolean isAttending) {
        List<ResponseEntity<RsvpResponse>> responseEntities = new ArrayList<>();
        List<Rsvp> rsvpList= rsvpService.findByAttending(isAttending);
        for (Rsvp rsvp : rsvpList) {
            responseEntities.add(ResponseEntity.ok(createRsvpResponse(rsvp)));
        }

        return responseEntities;
    }

    @PostMapping
    public ResponseEntity<RsvpResponse> addNewRsvp(@RequestBody RsvpCreateRequest rsvpCreateRequest) {
        Rsvp rsvp = new Rsvp(rsvpCreateRequest.getName());
        rsvp.setName(rsvpCreateRequest.getName());

        rsvpService.addNewRsvp(rsvp);

        return ResponseEntity.ok(createRsvpResponse(rsvpCreateRequest));
    }

    @PutMapping
    public ResponseEntity<RsvpResponse> updateRsvp(@RequestBody RsvpCreateRequest rsvpCreateRequest) {
        Rsvp rsvp = rsvpService.findByName(rsvpCreateRequest.getName());
        rsvp.setName(rsvpCreateRequest.getName());
        rsvp.setEmail(rsvpCreateRequest.getEmail());
        rsvp.setAttending(rsvpCreateRequest.isAttending());
        rsvp.setMealChoice(rsvpCreateRequest.getMealChoice());
        rsvp.setPlus1Name(rsvpCreateRequest.getPlus1Name());
        rsvp.setPlus1MealChoice(rsvpCreateRequest.getPlus1MealChoice());

        return ResponseEntity.ok(createRsvpResponse(rsvpCreateRequest));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<RsvpResponse> delete(@PathVariable("name") String name) {

        Rsvp rsvp = rsvpService.findByName(name);
        if (rsvp == null) {
            return ResponseEntity.notFound().build();
        }

        RsvpResponse rsvpResponse = new RsvpResponse();
        rsvpResponse.setName(rsvp.getName());

        rsvpService.deleteRsvp(rsvp);

        return ResponseEntity.ok(rsvpResponse);
    }

    public RsvpResponse createRsvpResponse(RsvpCreateRequest rsvpCreateRequest) {
        RsvpResponse rsvpResponse = new RsvpResponse();
        rsvpResponse.setName(rsvpCreateRequest.getName());
        rsvpResponse.setEmail(rsvpCreateRequest.getEmail());
        rsvpResponse.setAttending(rsvpCreateRequest.isAttending());
        rsvpResponse.setMealChoice(rsvpCreateRequest.getMealChoice());
        rsvpResponse.setPlus1Name(rsvpCreateRequest.getPlus1Name());
        rsvpResponse.setPlus1MealChoice(rsvpCreateRequest.getPlus1MealChoice());

        return rsvpResponse;
    }

    public RsvpResponse createRsvpResponse(Rsvp rsvp) {
        return createRsvpResponse(new RsvpCreateRequest(
                rsvp.getName(),
                rsvp.getEmail(),
                rsvp.isAttending(),
                rsvp.getMealChoice(),
                rsvp.getPlus1Name(),
                rsvp.getPlus1MealChoice()));
    }
}
