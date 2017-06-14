package com.mixconnector.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mixconnector.entity.StateEntity;

public interface StateRepository extends MongoRepository<StateEntity, String> {

}
