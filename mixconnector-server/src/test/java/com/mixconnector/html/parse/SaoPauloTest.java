package com.mixconnector.html.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mixconnector.domain.Destinatario;
import com.mixconnector.domain.Emitente;
import com.mixconnector.domain.Nfe;
import com.mixconnector.entity.StateEntity;
import com.mixconnector.repository.StateRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaoPauloTest {

	@Autowired
	private SaoPaulo saoPaulo;

	@Autowired
	private StateRepository stateRepository;

	@Before
	public void before() {
		StateEntity stateEntity = new StateEntity("sp", "https://nfe.fazenda.sp.gov.br/consultanfe/consulta/publica/consultarnfe.aspx");
		stateRepository.save(stateEntity);
	}

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
		assertEquals("23/05/2017 às 08:44:58", nfe.getDataAutorizacao());
		assertEquals("23/05/2017 às 08:44:58", nfe.getDataInclusao());
		assertEquals("pjiOw9Wi4TulMFSevCaZqKDLx78=", nfe.getDigest());

		Emitente emitente = nfe.getEmitente();
		assertNotNull(emitente);
		assertEquals("SARRUF S/A.", emitente.getRazaoSocial());
		assertEquals("60.627.429/0001-35", emitente.getCnpj());
		assertEquals("Centro", emitente.getBairro());
		assertEquals("3550308 - Sao Paulo", emitente.getMunicipio());
		assertEquals("SP", emitente.getUf());
		assertEquals("101033000118", emitente.getInscricaoEstadual());
		assertEquals("", emitente.getInscricaoMunicipal());
		assertEquals("", emitente.getCnae());
		assertEquals("REI DO ARMARINHO", emitente.getNomeFantasia());
		assertEquals("Rua Cavalheiro Basilio Jafet, 99", emitente.getEndereco());
		assertEquals("01022-020", emitente.getCep());
		assertEquals("(11)4083-5555", emitente.getTelefone());
		assertEquals("1058 - Brasil", emitente.getPais());
		assertEquals("", emitente.getInscriçãoEstadualSubstitutoTributário());
		assertEquals("3550308", emitente.getMunicipioOcorrencia());
		assertEquals("3 - Regime Normal", emitente.getCodigoRegimeTributario());

		Destinatario destinatario = nfe.getDestinatario();
		assertEquals("MAURICIO LIMA", destinatario.getRazaoSocial());
		assertEquals("338.296.178-44", destinatario.getCpf());
		assertEquals("LOTEAMENTO PLANALTO DO SOL", destinatario.getBairro());
		assertEquals("3545803 - SANTA BARBARA DOESTE", destinatario.getMunicipio());
		assertEquals("SP", destinatario.getUf());
		assertEquals("09 - Não Contribuinte, que pode ou não possuir Inscrição Estadual no Cadastro de Contribuintes do ICMS", destinatario.getIndicadorIE());
		assertEquals("GOIANIA,, 564", destinatario.getEndereco());
		assertEquals("13454-335", destinatario.getCep());
		assertEquals("(19)3629-7128", destinatario.getTelefone());
		assertEquals("1058 - Brasil", destinatario.getPais());
		assertEquals("mauricioadlima@gmail.com", destinatario.getEmail());
		assertEquals("", destinatario.getInscricaoEstadual());
		assertEquals("", destinatario.getInscricaoSuframa());
		assertEquals("", destinatario.getIm());

	}
}