package com.nfespy.site;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nfespy.domain.Nfe;
import com.nfespy.repository.StateRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaoPauloTest {

	@Autowired
	private SaoPaulo saoPaulo;

	@Autowired
	private StateRepository stateRepository;

	@Test
	public void getNfe() {
		final Nfe nfe = saoPaulo.getNfe("35170560627429000135550020011701121473193288");
		assertNotNull(nfe);
		assertEquals("55", nfe.getModelo());
		assertEquals("2", nfe.getSerie());
		assertEquals("1170112", nfe.getNumero());
		assertEquals("23/05/2017 07:57:32-03:00", nfe.getDataEmissao());
		assertEquals("23/05/2017 07:57:32-03:00", nfe.getDataSaidaOuEntrada());
		assertEquals("89,89", nfe.getValorTotal());
	}
}