package com.nfespy.service;

import static com.nfespy.model.Nfe.Status.ERROR;
import static com.nfespy.model.Nfe.Status.PROCESSED;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nfespy.model.Nfe;
import com.nfespy.repository.NfeRepository;

@Service
public class NfeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NfeService.class);

	@Autowired
	private NfeRepository nfeRepository;

	@Async
	public void processNfe(Nfe nfe) {
		LOGGER.info("Processing nfe {}", nfe.getKey());
		try {
			nfe.setStatus(PROCESSED);
			LOGGER.info("Nfe {} processed with successful", nfe.getKey());
		} catch (Exception ex) {
			nfe.setStatus(ERROR);
			LOGGER.error("Nfe {} processed with error", nfe.getKey(), ex);
		} finally {
			nfeRepository.save(nfe);
		}
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
