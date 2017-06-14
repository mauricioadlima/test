package com.mixconnector.domain;

import static com.mixconnector.entity.NfeEntity.Status.ERROR;
import static com.mixconnector.entity.NfeEntity.Status.FAIL;
import static com.mixconnector.entity.NfeEntity.Status.PROCESSED;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mixconnector.entity.NfeEntity;
import com.mixconnector.html.parse.HtmlParse;
import com.mixconnector.repository.NfeRepository;

@Service
public class NfeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NfeService.class);

	private static final String NACIONAL = "nacional";

	@Autowired
	private NfeRepository nfeRepository;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Async
	public void processNfe(NfeEntity nfeEntity) {
		LOGGER.info("Processing nfeEntity {}", nfeEntity.getKey());
		try {
			Nfe nfe = getHttpService(nfeEntity.getState()).getNfe(nfeEntity.getKey());
			if (nfe == null) {
				nfe = getHttpService(NACIONAL).getNfe(nfeEntity.getKey());
			}

			if (nfe != null) {
				nfeEntity.setStatus(PROCESSED);
				nfeEntity.setNfe(nfe);
				LOGGER.info("NfeEntity {} processed with successful", nfeEntity.getKey());
			} else {
				LOGGER.error("NfeEntity {} cannot be processed", nfeEntity.getKey());
				nfeEntity.setStatus(FAIL);
			}

		} catch (Exception ex) {
			nfeEntity.setStatus(ERROR);
			LOGGER.error("NfeEntity {} processed with error", nfeEntity.getKey(), ex);
		} finally {
			nfeRepository.save(nfeEntity);
		}
	}

	private HtmlParse getHttpService(String state) {
		return (HtmlParse) applicationContext.getAutowireCapableBeanFactory()
											 .getBean(state);
	}

	public UUID saveAll(final List<NfeEntity> nfeEntities) {
		final UUID lotId = UUID.randomUUID();
		nfeEntities.forEach(nfe -> nfe.setLotId(lotId));
		nfeRepository.save(nfeEntities);
		return lotId;
	}

	public List<NfeEntity> findByKeyOrLotId(final String key, final UUID lotId) {
		if (key != null) {
			return Collections.singletonList(nfeRepository.findOne(key));
		}
		return nfeRepository.findByLotIdAndStatus(lotId, PROCESSED);
	}

	public List<NfeStatusReport> getAllGroupedByStatus() {
		GroupOperation groupOperation = group("status", "lotId").count()
																.as("total");

		AggregationResults<NfeStatusReport> aggregate = mongoTemplate.aggregate(newAggregation(groupOperation), "nfes", NfeStatusReport.class);
		return aggregate.getMappedResults();
	}

}
