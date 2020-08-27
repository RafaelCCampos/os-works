package com.rafaelcampos.osworks.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.rafaelcampos.osworks.domain.exception.NegocioException;

@Entity
public class OrdemServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")//por padrão se não colocar o JoinColumn ele já concatena o nome da variável com _id
	private Cliente cliente;
	
	private String descricao;
	private BigDecimal preco;
	
	@Enumerated(EnumType.STRING) // O Padrão é tipo ordinal.
	private StatusOrdemServico status;
	
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataFechamento;
	
	@OneToMany(mappedBy = "ordemServico")
	private List<Comentario> comentarios;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public StatusOrdemServico getStatus() {
		return status;
	}
	public void setStatus(StatusOrdemServico status) {
		this.status = status;
	}
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public OffsetDateTime getDataFechamento() {
		return dataFechamento;
	}
	public void setDataFechamento(OffsetDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public boolean podeSerFechada() {
		return StatusOrdemServico.ABERTA.equals(getStatus());
	}
	
	public void fechar(Long ordemServicoId) {
		if(!podeSerFechada()) {
			throw new NegocioException("Ordem de serviço não pode ser fechada.");
		}
		
		setStatus(StatusOrdemServico.FECHADA);
		setDataFechamento(OffsetDateTime.now());
	}
	
	public boolean podeSerCancelada() {
		return StatusOrdemServico.ABERTA.equals(getStatus());
	}
	
	public void cancelar(Long ordemServicoId) {
		if(!podeSerCancelada()) {
			throw new NegocioException("Ordem de serviço não pode ser cancelada.");
		}
		
		setStatus(StatusOrdemServico.CANCELADA);
		setDataFechamento(OffsetDateTime.now());
	}
	
}
