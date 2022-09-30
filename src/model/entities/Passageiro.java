package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Passageiro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String telefone;
	
	private Viagem viagem;
	
	public Passageiro() {
	}

	public Passageiro(Integer id, String nome, String telefone, Viagem viagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
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

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}

	
}
