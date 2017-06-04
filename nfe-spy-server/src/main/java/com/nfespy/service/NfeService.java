package com.nfespy.service;

import static com.nfespy.model.Nfe.Status.ERROR;
import static com.nfespy.model.Nfe.Status.FAIL;
import static com.nfespy.model.Nfe.Status.PROCESSED;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nfespy.model.CanonicNfe;
import com.nfespy.model.Nfe;
import com.nfespy.site.HttpService;
import com.nfespy.repository.NfeRepository;

@Service
public class NfeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NfeService.class);

	private static final String NACIONAL = "nacional";

	@Autowired
	private NfeRepository nfeRepository;

	@Autowired
	private ApplicationContext applicationContext;

	@Async
	public void processNfe(Nfe nfe) {
		LOGGER.info("Processing nfe {}", nfe.getKey());
		try {
			CanonicNfe canonicNfe = getHttpService(NACIONAL).getNfe(nfe.getKey());
			if (!canonicNfe.wasProcessedOk()) {
				canonicNfe = getHttpService(nfe.getState()).getNfe(nfe.getKey());
			}

			if (canonicNfe.wasProcessedOk()) {
				nfe.setStatus(PROCESSED);
				LOGGER.info("Nfe {} processed with successful", nfe.getKey());
			} else {
				LOGGER.error("Nfe {} cannot be processed", nfe.getKey());
				nfe.setStatus(FAIL);
			}

		} catch (Exception ex) {
			nfe.setStatus(ERROR);
			LOGGER.error("Nfe {} processed with error", nfe.getKey(), ex);
		} finally {
			nfeRepository.save(nfe);
		}
	}

	private HttpService getHttpService(String state) {
		return (HttpService) applicationContext.getAutowireCapableBeanFactory()
											   .getBean(state);
	}

	public UUID saveAll(final List<Nfe> nfes) {
		final UUID lotId = UUID.randomUUID();
		nfes.forEach(nfe -> nfe.setLotId(lotId));
		nfeRepository.save(nfes);
		return lotId;
	}

	public List<Nfe> findByKeyOrLotId(final String key, final UUID lotId) {
		if (key != null) {
			return Collections.singletonList(nfeRepository.findOne(key));
		}
		return nfeRepository.findByLotIdAndStatus(lotId, PROCESSED);
	}

}
