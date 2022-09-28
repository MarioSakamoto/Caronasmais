package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Passageiro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String telefone;
	private String saindoDe;
	private String indoPara;
	
	private Viagem viagem;
	
	public Passageiro() {
	}

	public Passageiro(Integer id, String nome, String telefone, String saindoDe, String indoPara, Viagem viagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.saindoDe = saindoDe;
		this.indoPara = indoPara;
		this.viagem = viagem;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSaindoDe() {
		return saindoDe;
	}

	public void setSaindoDe(String saindoDe) {
		this.saindoDe = saindoDe;
	}

	public String getIndoPara() {
		return indoPara;
	}

	public void setIndoPara(String indoPara) {
		this.indoPara = indoPara;
	}

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, indoPara, nome, saindoDe, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passageiro other = (Passageiro) obj;
		return Objects.equals(id, other.id) && Objects.equals(indoPara, other.indoPara)
				&& Objects.equals(nome, other.nome) && Objects.equals(saindoDe, other.saindoDe)
				&& Objects.equals(telefone, other.telefone);
	}

	@Override
	public String toString() {
		return "Passageiro [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", saindoDe=" + saindoDe
				+ ", indoPara=" + indoPara + "]";
	}

	
}
