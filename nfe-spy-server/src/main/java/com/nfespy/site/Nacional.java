package com.nfespy.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nfespy.model.CanonicNfe;

@Component("nacional")
public class Nacional extends AbstractHttpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaoPaulo.class);

	@Override
	public CanonicNfe getNfe(final String keyValue) {
		LOGGER.info(loadNfe(keyValue, stateConfigPropertires.getNacional()));
		return null;
	}

}
