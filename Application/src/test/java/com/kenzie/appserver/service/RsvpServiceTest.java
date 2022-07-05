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
import static org.junit.jupiter.api.Assertions.assertThrows;
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

//    @Test
//    void findAll() {
//        // GIVEN
//        RsvpRecord record = new RsvpRecord();
//        record.setName("Mr.Kenzie");
//        record.setEmail(mockNeat.strings().get());
//        record.setAttending(true);
//        record.setMealChoice("Beef");
//        record.setPlus1Name(mockNeat.strings().get());
//        record.setPlus1MealChoice("Salmon");
//
//        RsvpRecord record2 = new RsvpRecord();
//        record2.setName("Mrs.Kenzie");
//        record2.setEmail(mockNeat.strings().get());
//        record2.setAttending(false);
//        record2.setMealChoice("Beef");
//        record2.setPlus1Name(mockNeat.strings().get());
//        record2.setPlus1MealChoice("Salmon");
//
//        List<RsvpRecord> rsvpList = new ArrayList<>();
//        rsvpList.add(record);
//        rsvpList.add(record2);
//
//        // WHEN
//        when(rsvpRepository.findAll()).thenReturn(rsvpList);
//        List<RsvpRecord> rsvpTrue = rsvpService.findByAttending(true);
//        List<RsvpRecord> rsvpFalse = rsvpService.findByAttending(false);
//
//        // THEN
//        Assertions.assertNotNull(rsvpTrue, "Rsvp list has been returned");
//        Assertions.assertEquals(1, rsvpTrue.size(), "List should have one value");
//        Assertions.assertEquals(record.getName(), rsvpTrue.get(0).getName());
//
//        Assertions.assertNotNull(rsvpFalse, "Rsvp list has been returned");
//        Assertions.assertEquals(1, rsvpFalse.size(), "List should have one value");
//        Assertions.assertEquals(record2.getName(), rsvpFalse.get(0).getName());
//    }


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

        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(rsvp.getName());
        rsvpRecord.setEmail(rsvp.getEmail());
        rsvpRecord.setAttending(rsvp.isAttending());
        rsvpRecord.setMealChoice(rsvp.getMealChoice());
        rsvpRecord.setPlus1MealChoice(rsvp.getPlus1MealChoice());

        // WHEN
        when(rsvpService.createRsvp(rsvp)).thenReturn(rsvpRecord);
        RsvpRecord createdRsvp = rsvpService.createRsvp(rsvp);

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

        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(name);
        rsvpRecord.setEmail(mockNeat.strings().get());
        rsvpRecord.setAttending(true);
        rsvpRecord.setMealChoice("Beef");
        rsvpRecord.setPlus1Name(mockNeat.strings().get());
        rsvpRecord.setPlus1MealChoice("Salmon");

        when(rsvpRepository.save(rsvpRecord)).thenReturn(rsvpRecord);
        rsvpService.updateRsvp(rsvpRecord);

        verify(rsvpRepository, times(1)).save(rsvpRecord);
    }

    @Test
    void deleteRsvp() {
        // GIVEN
        String name = mockNeat.strings().get();

        RsvpRecord rsvpRecord = new RsvpRecord();
        rsvpRecord.setName(name);
        rsvpRecord.setEmail(mockNeat.strings().get());
        rsvpRecord.setAttending(true);
        rsvpRecord.setMealChoice("Beef");
        rsvpRecord.setPlus1Name(mockNeat.strings().get());
        rsvpRecord.setPlus1MealChoice("Salmon");

        // WHEN
        rsvpService.deleteRsvp(rsvpRecord);
        verify(rsvpRepository).delete(rsvpRecord);
    }


}
