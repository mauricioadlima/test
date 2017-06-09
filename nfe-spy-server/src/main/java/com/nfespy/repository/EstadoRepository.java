package com.nfespy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nfespy.entity.EstadoEntity;

public interface EstadoRepository extends MongoRepository<EstadoEntity, String> {

}
