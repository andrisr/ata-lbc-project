package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RsvpCreateRequest;
import com.kenzie.appserver.controller.model.RsvpResponse;
import com.kenzie.appserver.repositories.model.RsvpRecord;
import com.kenzie.appserver.service.RsvpService;

import com.kenzie.appserver.service.model.Rsvp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        RsvpRecord rsvpRecord = rsvpService.findByName(name);
        if (rsvpRecord == null) {
            return ResponseEntity.notFound().build();
        }

        RsvpResponse rsvpResponse = new RsvpResponse();
        rsvpResponse.setName(rsvpRecord.getName());
        return ResponseEntity.ok(rsvpResponse);
    }

    @GetMapping("/attending/{attending}")
    public List<ResponseEntity<RsvpResponse>> get(@PathVariable("attending") boolean isAttending) {
        List<ResponseEntity<RsvpResponse>> responseEntities = new ArrayList<>();
        List<Rsvp> rsvpList= rsvpService.findByAttending(isAttending);

        for (Rsvp rsvp : rsvpList) {
            RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest(
                    rsvp.getName(),
                    rsvp.getEmail(),
                    rsvp.isAttending(),
                    rsvp.getMealChoice(),
                    rsvp.getPlus1Name(),
                    rsvp.getPlus1MealChoice());
            responseEntities.add(ResponseEntity.ok(createRsvpResponse(rsvpCreateRequest)));
        }
        return responseEntities;
    }

    @PostMapping
    public ResponseEntity<RsvpResponse> createRsvp(@RequestBody RsvpCreateRequest rsvpCreateRequest) {
        Rsvp rsvp = new Rsvp(rsvpCreateRequest.getName());
        rsvp.setName(rsvpCreateRequest.getName());
        rsvp.setEmail((rsvpCreateRequest.getEmail()));

        rsvpService.createRsvp(rsvp);

        return ResponseEntity.ok(createRsvpResponse(rsvpCreateRequest));
    }

    @PutMapping
    public ResponseEntity<RsvpResponse> updateRsvp(@RequestBody RsvpCreateRequest rsvpCreateRequest) {
        RsvpRecord rsvpRecord = rsvpService.findByName(rsvpCreateRequest.getName());
        rsvpRecord.setName(rsvpCreateRequest.getName());
        rsvpRecord.setAttending(rsvpCreateRequest.isAttending());
        rsvpRecord.setMealChoice(rsvpCreateRequest.getMealChoice());
        rsvpRecord.setPlus1Name(rsvpCreateRequest.getPlus1Name());
        rsvpRecord.setPlus1MealChoice(rsvpCreateRequest.getPlus1MealChoice());

        rsvpService.updateRsvp(rsvpRecord);

        return ResponseEntity.ok(createRsvpResponse(rsvpCreateRequest));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<RsvpResponse> delete(@PathVariable("name") String name) {

        RsvpRecord rsvpRecord = rsvpService.findByName(name);
        if (rsvpRecord == null) {
            return ResponseEntity.notFound().build();
        }

        RsvpResponse rsvpResponse = new RsvpResponse();
        rsvpResponse.setName(rsvpRecord.getName());

        rsvpService.deleteRsvp(rsvpRecord);

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

}
