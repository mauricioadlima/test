package com.nfespy.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nfespy.model.CanonicNfe;

@Component("sp")
public class SaoPaulo extends AbstractHttpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaoPaulo.class);

	@Override
	public CanonicNfe getNfe(final String keyValue) {
		LOGGER.info(loadNfe(keyValue, stateConfigPropertires.getSp()));
		return null;
	}
}