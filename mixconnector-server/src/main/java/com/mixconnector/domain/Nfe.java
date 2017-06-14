package com.mixconnector.domain;

public class Nfe {

	private String modelo;

	private String serie;

	private String numero;

	private String dataEmissao;

	private String dataSaidaOuEntrada;

	private String valorTotal;

	private String dataAutorizacao;

	private String dataInclusao;

	private String digest;

	private Emitente emitente = new Emitente();

	private Destinatario destinatario = new Destinatario();

	public Destinatario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(final Destinatario destinatario) {
		this.destinatario = destinatario;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(final Emitente emitente) {
		this.emitente = emitente;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(final String valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(final String modelo) {
		this.modelo = modelo;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(final String serie) {
		this.serie = serie;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(final String numero) {
		this.numero = numero;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(final String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getDataSaidaOuEntrada() {
		return dataSaidaOuEntrada;
	}

	public void setDataSaidaOuEntrada(final String dataSaidaOuEntrada) {
		this.dataSaidaOuEntrada = dataSaidaOuEntrada;
	}

	public String getDataAutorizacao() {
		return dataAutorizacao;
	}

	public void setDataAutorizacao(final String dataAutorizacao) {
		this.dataAutorizacao = dataAutorizacao;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(final String dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(final String digest) {
		this.digest = digest;
	}
}
