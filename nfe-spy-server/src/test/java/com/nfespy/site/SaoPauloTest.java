package com.nfespy.site;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nfespy.config.EstadoTipoValor;
import com.nfespy.config.EstadoProperties;
import com.nfespy.domain.Nfe;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaoPauloTest {

	@Autowired
	private SaoPaulo saoPaulo;

	@Autowired
	private EstadoProperties estadoProperties;

	private WebDriver driver;

	@Before
	public void before() {
		driver = saoPaulo.getDriver();
		driver.navigate()
			  .to("http://localhost:8888/sp/index.html");
	}

	@After
	public void after() {
		driver.close();
	}

	@Test
	public void getNfe() {
		final Map<String, EstadoTipoValor> fields = estadoProperties.getCampos()
																	.get("sp");

		final Nfe nfe = saoPaulo.parse(fields, driver);
		assertNotNull(nfe);
		assertEquals("55", nfe.getModelo());
		assertEquals("2", nfe.getSerie());
		assertEquals("1170112", nfe.getNumero());
		assertEquals("23/05/2017 07:57:32-03:00", nfe.getDataEmissao());
		assertEquals("23/05/2017 07:57:32-03:00", nfe.getDataSaidaOuEntrada());
		assertEquals("89,89", nfe.getValorTotal());
	}
}