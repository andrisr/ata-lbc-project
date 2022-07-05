package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.RsvpRecord;

import com.kenzie.appserver.service.model.Rsvp;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableScan
@Repository
public interface RsvpRepository extends CrudRepository<RsvpRecord, String> {

    RsvpRecord findByName(String name);
}
