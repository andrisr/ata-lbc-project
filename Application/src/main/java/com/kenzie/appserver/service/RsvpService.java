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

        return rsvpRepository.findByName(name);
    }

    public List<Rsvp> findByAttending(boolean isAttending) {

        Iterable<RsvpRecord> recordList = rsvpRepository.findAll();

        System.out.println("service findByAttending");

        List<Rsvp> attendingList = new ArrayList<>();

        for (RsvpRecord record : recordList){
            System.out.println("inside attending loop");
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
