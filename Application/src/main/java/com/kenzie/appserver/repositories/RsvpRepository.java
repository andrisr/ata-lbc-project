package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.RsvpRecord;

import com.kenzie.appserver.service.model.Rsvp;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface RsvpRepository extends CrudRepository<RsvpRecord, String> {
    Rsvp findByName(String name);

    List<Rsvp> findByAttending(boolean isAttending);
}
