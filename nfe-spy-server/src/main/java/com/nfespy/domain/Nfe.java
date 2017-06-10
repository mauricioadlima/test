package com.nfespy.domain;

public class Nfe {

	private String modelo;

	private String serie;

	private String numero;

	private String dataEmissao;

	private String dataSaidaOuEntrada;

	private String valorTotal;

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

	@Override
	public String toString() {
		return "Nfe{" + "modelo='" + modelo + '\'' + ", serie='" + serie + '\'' + ", numero='" + numero + '\'' + ", dataEmissao='" + dataEmissao + '\'' + ", dataSaidaOuEntrada='" + dataSaidaOuEntrada + '\''
				+ ", valorTotal='" + valorTotal + '\'' + '}';
	}
}
