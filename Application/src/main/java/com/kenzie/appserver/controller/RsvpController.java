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
        RsvpRecord record = rsvpService.findByName(name);
        if (record == null) {
            return ResponseEntity.notFound().build();
        }

        RsvpCreateRequest request = new RsvpCreateRequest(
                record.getName(),
                record.getEmail(),
                record.isAttending(),
                record.getMealChoice(),
                record.getPlus1Name(),
                record.getPlus1MealChoice());

        RsvpResponse response = createRsvpResponse(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/attending/{attending}")
    public List<ResponseEntity<RsvpResponse>> get(@PathVariable("attending") boolean isAttending) {
        System.out.println("controller attending");

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
        RsvpRecord record = rsvpService.findByName(rsvpCreateRequest.getName());
        record.setName(rsvpCreateRequest.getName());
        record.setEmail((rsvpCreateRequest.getEmail()));
        record.setAttending(rsvpCreateRequest.isAttending());
        record.setMealChoice(rsvpCreateRequest.getMealChoice());
        record.setPlus1Name(rsvpCreateRequest.getPlus1Name());
        record.setPlus1MealChoice(rsvpCreateRequest.getPlus1MealChoice());

        rsvpService.updateRsvp(record);

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
