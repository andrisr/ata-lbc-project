package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.RsvpRecord;
import com.kenzie.appserver.repositories.RsvpRepository;
import com.kenzie.appserver.service.model.Rsvp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Rsvp> findByAttending(boolean isAttending) {

        Iterable<RsvpRecord> recordList = rsvpRepository.findAll();

        List<Rsvp> attendingList = new ArrayList<>();

        for (RsvpRecord record : recordList){

            if (record.isAttending()){

                 Rsvp rsvp = new Rsvp(record.getName());
                 rsvp.setEmail(record.getEmail());
                 rsvp.setAttending(record.isAttending());
                 rsvp.setMealChoice(record.getMealChoice());
                 rsvp.setPlus1Name(record.getPlus1Name());
                 rsvp.setPlus1MealChoice(record.getPlus1MealChoice());

                 attendingList.add(rsvp);
            }
        }
        return attendingList;
    }


    public Rsvp createRsvp(Rsvp rsvp) {
        if (rsvp.getName() == null || rsvp.getEmail() == null
                || rsvp.getName().trim().isEmpty() || rsvp.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Name and email cannot be null");
        }


        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(rsvp.getName());
        rsvpRecord.setEmail(rsvp.getEmail());

        rsvpRepository.save(rsvpRecord);
        return rsvpRecord;
    }

    public void updateRsvp(RsvpRecord rsvpRecord) {
        RsvpRecord record = findByName(rsvpRecord.getName());

        if (rsvpRecord.getName() == null || rsvpRecord.getEmail() == null
                || rsvpRecord.getName().trim().isEmpty() || rsvpRecord.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Name and email cannot be null");
        }

        rsvpRepository.save(rsvpRecord);
    }

    public void deleteRsvp(RsvpRecord rsvpRecord) {
        RsvpRecord record = findByName(rsvpRecord.getName());

        rsvpRepository.delete(record);
    }
}
