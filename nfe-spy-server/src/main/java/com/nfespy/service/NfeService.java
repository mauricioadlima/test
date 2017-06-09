package com.nfespy.service;

import static com.nfespy.entity.NfeEntity.Status.ERRO;
import static com.nfespy.entity.NfeEntity.Status.FALHA;
import static com.nfespy.entity.NfeEntity.Status.PROCESSADA;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nfespy.domain.Nfe;
import com.nfespy.entity.NfeEntity;
import com.nfespy.repository.NfeRepository;
import com.nfespy.site.HttpService;

@Service
public class NfeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NfeService.class);

	private static final String NACIONAL = "nacional";

	@Autowired
	private NfeRepository nfeRepository;

	@Autowired
	private ApplicationContext applicationContext;

	@Async
	public void processNfe(NfeEntity nfeEntity) {
		LOGGER.info("Processing nfeEntity {}", nfeEntity.getChave());
		try {
			Nfe nfe = getHttpService(nfeEntity.getEstado()).getNfe(nfeEntity.getChave());
			if (nfe == null) {
				nfe = getHttpService(NACIONAL).getNfe(nfeEntity.getChave());
			}

			if (nfe != null) {
				nfeEntity.setStatus(PROCESSADA);
				LOGGER.info("NfeEntity {} processed with successful", nfeEntity.getChave());
			} else {
				LOGGER.error("NfeEntity {} cannot be processed", nfeEntity.getChave());
				nfeEntity.setStatus(FALHA);
			}

		} catch (Exception ex) {
			nfeEntity.setStatus(ERRO);
			LOGGER.error("NfeEntity {} processed with error", nfeEntity.getChave(), ex);
		} finally {
			nfeRepository.save(nfeEntity);
		}
	}

	private HttpService getHttpService(String state) {
		return (HttpService) applicationContext.getAutowireCapableBeanFactory()
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
		return nfeRepository.findByLotIdAndStatus(lotId, PROCESSADA);
	}

}
