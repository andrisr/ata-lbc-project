package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.RsvpRepository;
import com.kenzie.appserver.repositories.model.RsvpRecord;
import com.kenzie.appserver.service.model.Rsvp;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class RsvpServiceTest {
    private RsvpRepository rsvpRepository;
    private RsvpService rsvpService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    @BeforeEach
    void setup() {
        rsvpRepository = mock(RsvpRepository.class);
        rsvpService = new RsvpService(rsvpRepository);
    }

    @Test
    void findByName() {
        // GIVEN
        String name = mockNeat.strings().get();

        RsvpRecord record = new RsvpRecord();
        record.setName(name);
        record.setEmail(mockNeat.strings().get());
        record.setAttending(true);
        record.setMealChoice("Beef");
        record.setPlus1Name(mockNeat.strings().get());
        record.setPlus1MealChoice("Salmon");

        // WHEN
        when(rsvpRepository.findByName(name)).thenReturn(record);
        RsvpRecord rsvpRecord = rsvpService.findByName(name);

        // THEN
        Assertions.assertNotNull(rsvpRecord, "Rsvp has been returned");
        Assertions.assertEquals(rsvpRecord.getEmail(), record.getEmail(), "Email matches");
        Assertions.assertEquals(rsvpRecord.isAttending(), record.isAttending(), "Attending matches");
        Assertions.assertEquals(rsvpRecord.getMealChoice(), record.getMealChoice(), "MealChoice matches");
        Assertions.assertEquals(rsvpRecord.getPlus1Name(), record.getPlus1Name(), "plus1Name matches");
        Assertions.assertEquals(rsvpRecord.getPlus1MealChoice(), record.getPlus1MealChoice(), "plus1MealChoice matches");
    }

    @Test
    void findByAttending() {
        // GIVEN

        RsvpRecord record = new RsvpRecord();
        record.setName("Mr.Kenzie");
        record.setEmail(mockNeat.strings().get());
        record.setAttending(true);
        record.setMealChoice("Beef");
        record.setPlus1Name(mockNeat.strings().get());
        record.setPlus1MealChoice("Salmon");

        RsvpRecord record2 = new RsvpRecord();
        record2.setName("Mrs.Kenzie");
        record2.setEmail(mockNeat.strings().get());
        record2.setAttending(false);
        record2.setMealChoice("Beef");
        record2.setPlus1Name(mockNeat.strings().get());
        record2.setPlus1MealChoice("Salmon");

        List<RsvpRecord> rsvpList = new ArrayList<>();
        rsvpList.add(record);
        rsvpList.add(record2);

        // WHEN
        when(rsvpRepository.findByAttending(true)).thenReturn(rsvpList);
        List<Rsvp> rsvp = rsvpService.findByAttending(true);

        // THEN
        Assertions.assertNotNull(rsvp, "Rsvp list has been returned");
        Assertions.assertEquals(1, rsvp.size(), "List should have one value");

        for (Rsvp guest: rsvp){
            if (guest.isAttending()){
                Assertions.assertEquals(guest.getEmail(), record.getEmail(), "Email matches");
                Assertions.assertEquals(guest.isAttending(), record.isAttending(), "Attending matches");
                Assertions.assertEquals(guest.getMealChoice(), record.getMealChoice(), "MealChoice matches");
                Assertions.assertEquals(guest.getPlus1Name(), record.getPlus1Name(), "plus1Name matches");
                Assertions.assertEquals(guest.getPlus1MealChoice(), record.getPlus1MealChoice(), "plus1MealChoice matches");
            }
        }
    }

    @Test
    void createRsvp() {
        // GIVEN
        String name = mockNeat.strings().get();

        Rsvp rsvp = new Rsvp(name);
        rsvp.setEmail(mockNeat.strings().get());
        rsvp.setAttending(true);
        rsvp.setMealChoice(mockNeat.strings().get());
        rsvp.setPlus1Name(mockNeat.strings().get());
        rsvp.setPlus1MealChoice(mockNeat.strings().get());

        // WHEN
        when(rsvpService.createRsvp(rsvp)).thenReturn(rsvp);
        Rsvp createdRsvp = rsvpService.createRsvp(rsvp);

        // THEN
        Assertions.assertNotNull(createdRsvp, "Rsvp has been returned");
        Assertions.assertEquals(createdRsvp.getEmail(), rsvp.getEmail(), "Email matches");
        Assertions.assertEquals(createdRsvp.isAttending(), rsvp.isAttending(), "Attending matches");
        Assertions.assertEquals(createdRsvp.getMealChoice(), rsvp.getMealChoice(), "MealChoice matches");
        Assertions.assertEquals(createdRsvp.getPlus1Name(), rsvp.getPlus1Name(), "plus1Name matches");
        Assertions.assertEquals(createdRsvp.getPlus1MealChoice(), rsvp.getPlus1MealChoice(),"plus1MealChoice matches");
    }

    @Test
    void updateRsvp() {
        // GIVEN
        String name = mockNeat.strings().get();

        Rsvp rsvp = new Rsvp(name);
        rsvp.setEmail(mockNeat.strings().get());
        rsvp.setAttending(true);
        rsvp.setMealChoice(null);
        rsvp.setPlus1Name(null);
        rsvp.setPlus1MealChoice(null);

        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(name);
        rsvpRecord.setEmail(rsvp.getEmail());
        rsvpRecord.setAttending(true);
        rsvpRecord.setMealChoice("Beef");
        rsvpRecord.setPlus1Name(mockNeat.strings().get());
        rsvpRecord.setPlus1MealChoice("Salmon");

        when(rsvpService.createRsvp(rsvp)).thenReturn();
        RsvpRecord rsvpRecords = rsvpService.createRsvp(rsvp);
        rsvpService.updateRsvp(rsvpRecord);

        verify(rsvpRepository).save(rsvpRecord);

//        RsvpRecord findRecord = rsvpRepository.findByName(rsvpRecord.getName());
//        // THEN
//        Assertions.assertNotNull(findRecord, "Rsvp has been returned");
//        Assertions.assertEquals(findRecord.getEmail(), rsvpRecord.getEmail(), "Email matches");
//        Assertions.assertEquals(findRecord.isAttending(), rsvpRecord.isAttending(), "Attending matches");
//        Assertions.assertEquals(findRecord.getMealChoice(), rsvpRecord.getMealChoice(), "MealChoice matches");
//        Assertions.assertEquals(findRecord.getPlus1Name(), rsvpRecord.getPlus1Name(), "plus1Name matches");
//        Assertions.assertEquals(findRecord.getPlus1MealChoice(), rsvpRecord.getPlus1MealChoice(),"plus1MealChoice matches");
    }


}
