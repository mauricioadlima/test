package com.nfespy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nfespy.entity.StateEntity;

public interface StateRepository extends MongoRepository<StateEntity, String> {

}
