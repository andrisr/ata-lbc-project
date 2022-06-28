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

    public Rsvp findByName(String name) {
        return rsvpRepository.findByName(name);
    }

    public List<Rsvp> findByAttending(boolean isAttending) {
        return rsvpRepository.findByAttending(isAttending);
    }

    public Rsvp createRsvp(Rsvp rsvp) {
        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(rsvp.getName());
        rsvpRecord.setEmail(rsvpRecord.getEmail());

        rsvpRepository.save(rsvpRecord);
        return rsvp;
    }

    public void updateRsvp(Rsvp rsvp) {
        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(rsvp.getName());
        rsvpRecord.setEmail(findByName(rsvp.getName()).getEmail());
        rsvpRecord.setAttending(rsvp.isAttending());
        rsvpRecord.setMealChoice(rsvp.getMealChoice());
        rsvpRecord.setPlus1Name(rsvp.getPlus1Name());
        rsvpRecord.setPlus1MealChoice(rsvp.getPlus1MealChoice());

        rsvpRepository.save(rsvpRecord);
    }

    public void deleteRsvp(Rsvp rsvp) {
        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(rsvp.getName());

        rsvpRepository.delete(rsvpRecord);
    }
}
