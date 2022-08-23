package br.com.loja.mensageria.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private Calendar dataPedido;
	private Calendar dataPagamento;
	private BigDecimal valor;
	
	@XmlElementWrapper(name = "itens")
	@XmlElement(name = "item")
	private Set<Item> itens = new LinkedHashSet<>();
	
	public Pedido() {
	}

	public Pedido(Integer codigo, Calendar dataPedido, Calendar dataPagamento, BigDecimal valor) {
		this.codigo = codigo;
		this.dataPedido = dataPedido;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
	}

	public void adicionaItem(Item item) {
		itens.add(item);
	}

	public Set<Item> getItens() {
		return itens;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public Calendar getDataPagamento() {
		return dataPagamento;
	}
	
	public Calendar getDataPedido() {
		return dataPedido;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
