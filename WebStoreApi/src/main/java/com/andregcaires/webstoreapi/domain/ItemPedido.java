package com.andregcaires.webstoreapi.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId // id embutido em tipo auxiliar
	private ItemPedidoPK id = new ItemPedidoPK();
	
	private Double desconto;
	
	private Integer quantidade;
	
	private Double preco;

	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		this.id.setPedido(pedido);
		this.id.setProduto(produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	public ItemPedido() {
		
	}
	
	public double getSubTotal() {		
		return (preco - desconto) * quantidade;		
	}

	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}
	
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
	
	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}
	
	@JsonIgnore
	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		// inicia formatter para a moeda brasileira
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		builder.append(getProduto().getNome());
		builder.append(", Quantidade: ");
		builder.append(getQuantidade());
		builder.append(", Preço unitário: ");
		builder.append(nf.format(getPreco()));
		builder.append(", Subtoral: ");
		builder.append(nf.format(getSubTotal()));
		builder.append("\n");
		return builder.toString();
	}
	
}
