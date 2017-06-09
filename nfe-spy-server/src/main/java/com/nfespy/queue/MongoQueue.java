package com.nfespy.queue;

import static com.nfespy.entity.NfeEntity.Status.PROCESSANDO;
import static com.nfespy.entity.NfeEntity.Status.ESPERANDO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nfespy.entity.NfeEntity;
import com.nfespy.service.NfeService;

@Component
public class MongoQueue {

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoQueue.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private NfeService nfeService;

	@Async
	public void consume() {
		final Query query = Query.query(Criteria.where("status")
												.is(ESPERANDO));

		final Update update = Update.update("status", PROCESSANDO);
		while (true) {
			final NfeEntity nfeEntity = mongoTemplate.findAndModify(query, update, NfeEntity.class);
			if (nfeEntity != null) {
				LOGGER.debug("Consumindo chave: {}", nfeEntity.getChave());
				nfeService.processNfe(nfeEntity);
			} else {
				LOGGER.debug("Todas as mensagens foram consumidas");
				break;
			}
		}

	}

}
