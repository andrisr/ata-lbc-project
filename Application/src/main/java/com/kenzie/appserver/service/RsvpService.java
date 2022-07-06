package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.RsvpRecord;
import com.kenzie.appserver.repositories.RsvpRepository;
import com.kenzie.appserver.service.model.Rsvp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RsvpService {
    private RsvpRepository rsvpRepository;

    @Autowired
    public RsvpService(RsvpRepository rsvpRepository) {
        this.rsvpRepository = rsvpRepository;
    }

    public RsvpRecord findByName(String name) {
        RsvpRecord record = rsvpRepository.findByName(name);

        if (record == null) {
            throw new IllegalArgumentException("No RSVP found");
        }

        return record;
    }

//    public List<RsvpRecord> findAll(Boolean isAttending) {
//        Iterable<RsvpRecord> recordList = rsvpRepository.findByAttending(isAttending);
//        List<RsvpRecord> attendingList = new ArrayList<>();
//
//        for (RsvpRecord record : recordList){
//            if (record.isAttending()){
//                 attendingList.add(record);
//            }
//        }
//
//        return attendingList;
//    }

    public List<RsvpRecord> findAll() {
        Iterable<RsvpRecord> recordList = rsvpRepository.findAll();
        List<RsvpRecord> attendingList = new ArrayList<>();

        for (RsvpRecord record : recordList){
            attendingList.add(record);
        }

        return attendingList;
    }

    public RsvpRecord createRsvp(Rsvp rsvp) {

        if (rsvp.getName() == null || rsvp.getEmail() == null
                || rsvp.getName().trim().isEmpty() || rsvp.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Name and email cannot be null");
        }

        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(rsvp.getName());
        rsvpRecord.setEmail(rsvp.getEmail());
        rsvpRecord.setAttending(false);

        rsvpRepository.save(rsvpRecord);
        return rsvpRecord;
    }

    public void updateRsvp(RsvpRecord rsvpRecord) {

        rsvpRepository.save(rsvpRecord);
    }

    public void deleteRsvp(RsvpRecord rsvpRecord) {

        rsvpRepository.delete(rsvpRecord);
    }
}
