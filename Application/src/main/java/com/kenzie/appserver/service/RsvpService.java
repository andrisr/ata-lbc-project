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


    public Rsvp findByName(String name) {

        RsvpRecord rsvpRecord = rsvpRepository.findByName(name);
        if (rsvpRecord == null){
            return null;
        }
        Rsvp rsvp = new Rsvp(rsvpRecord.getName());
        rsvp.setEmail(rsvpRecord.getEmail());
        rsvp.setAttending(rsvpRecord.isAttending());
        rsvp.setMealChoice(rsvpRecord.getMealChoice());
        rsvp.setPlus1Name(rsvpRecord.getPlus1Name());
        rsvp.setPlus1MealChoice(rsvpRecord.getPlus1MealChoice());

        return rsvp;
    }

    public List<Rsvp> findByAttending(boolean isAttending) {

        List<RsvpRecord> rsvpRecord = rsvpRepository.findByAttending(isAttending);
        if (rsvpRecord == null){
            return null;
        }
        List<Rsvp> attendingList = new ArrayList<>();

        for (RsvpRecord record : rsvpRecord){
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
