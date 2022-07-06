package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RsvpCreateRequest;
import com.kenzie.appserver.controller.model.RsvpResponse;
import com.kenzie.appserver.repositories.model.RsvpRecord;
import com.kenzie.appserver.service.RsvpService;

import com.kenzie.appserver.service.model.Rsvp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    // todo keep for now just in case
//    @GetMapping("/attending/{attending}")
//    public ResponseEntity<List<RsvpResponse>> get(@PathVariable("attending") Boolean isAttending) {
//        List<RsvpResponse> responses = new ArrayList<>();
//        List<RsvpRecord> rsvpList= rsvpService.findByAttending(isAttending);
//
//        for (RsvpRecord record : rsvpList) {
//            RsvpCreateRequest rsvpCreateRequest = new RsvpCreateRequest(
//                    record.getName(),
//                    record.getEmail(),
//                    record.isAttending(),
//                    record.getMealChoice(),
//                    record.getPlus1Name(),
//                    record.getPlus1MealChoice());
//            responses.add(createRsvpResponse(rsvpCreateRequest));
//        }
//
//        return ResponseEntity.ok(responses);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<RsvpResponse>> getAllRsvps() {
        List<RsvpResponse> returnedRsvpList = new ArrayList<>();
        List<RsvpResponse> isAttendingTrue = new ArrayList<>();
        List<RsvpResponse> isAttendingFalse = new ArrayList<>();
        List<RsvpResponse> isAttendingNull = new ArrayList<>();
        List<RsvpRecord> rsvpList= rsvpService.findAll();

        for (RsvpRecord record : rsvpList) {
            RsvpCreateRequest request = new RsvpCreateRequest(
                    record.getName(),
                    record.getEmail(),
                    record.isAttending(),
                    record.getMealChoice(),
                    record.getPlus1Name(),
                    record.getPlus1MealChoice());

            if (record.isAttending() == null) {
                isAttendingNull.add(createRsvpResponse(request));
            } else if (record.isAttending()) {
                isAttendingTrue.add(createRsvpResponse(request));
            } else {
                isAttendingFalse.add(createRsvpResponse(request));
            }
        }

            returnedRsvpList.addAll(isAttendingTrue);
            returnedRsvpList.addAll(isAttendingFalse);
            returnedRsvpList.addAll(isAttendingNull);

        return ResponseEntity.ok(returnedRsvpList);

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
