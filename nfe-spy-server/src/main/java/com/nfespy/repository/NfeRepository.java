package com.nfespy.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nfespy.domain.NfeStatusReport;
import com.nfespy.entity.NfeEntity;
import com.nfespy.entity.NfeEntity.Status;

public interface NfeRepository extends MongoRepository<NfeEntity, String> {

	List<NfeEntity> findByLotIdAndStatus(UUID lotId, Status status);

}
