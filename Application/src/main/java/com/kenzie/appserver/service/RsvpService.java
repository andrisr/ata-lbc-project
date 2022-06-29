package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.RsvpRecord;
import com.kenzie.appserver.repositories.RsvpRepository;
import com.kenzie.appserver.service.model.Rsvp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        System.out.println("rsvpService.findByName: " + record.isAttending());

        return record;
    }

    public List<RsvpRecord> findByAttending(boolean isAttending) {
        return rsvpRepository.findByAttending(isAttending);
    }

    public Rsvp createRsvp(Rsvp rsvp) {
        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(rsvp.getName());
        rsvpRecord.setEmail(rsvp.getEmail());
        rsvpRecord.setAttending(rsvp.isAttending());
        rsvpRecord.setMealChoice(rsvp.getMealChoice());
        rsvpRecord.setPlus1Name(rsvp.getPlus1Name());
        rsvpRecord.setPlus1MealChoice(rsvp.getPlus1MealChoice());

        System.out.println("rsvpService.createRsvp: " + rsvpRecord.isAttending());

        rsvpRepository.save(rsvpRecord);
        return rsvp;
    }

    public void updateRsvp(RsvpRecord rsvpRecord) {

        rsvpRepository.save(rsvpRecord);
    }

    public void deleteRsvp(RsvpRecord rsvpRecord) {

        rsvpRepository.delete(rsvpRecord);
    }
}
