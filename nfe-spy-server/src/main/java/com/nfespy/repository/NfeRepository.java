package com.nfespy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nfespy.model.Nfe;
import com.nfespy.model.Nfe.Status;

public interface NfeRepository extends MongoRepository<Nfe, String> {

	List<Nfe> findByLotIdAndStatus(UUID lotId, Status status);
}
