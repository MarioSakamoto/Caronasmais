 package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Passageiro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String telefone;
	private String enderecoDeSaida;
	private String enderecoDeChegada;
	
	private Viagem viagem;
	
	public Passageiro() {
	}

	

	public Passageiro(Integer id, String nome, String telefone, String enderecoDeSaida, String enderecoDeChegada,
			Viagem viagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.enderecoDeSaida = enderecoDeSaida;
		this.enderecoDeChegada = enderecoDeChegada;
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

	public String getEnderecoDeSaida() {
		return enderecoDeSaida;
	}
	public void setEnderecoDeSaida(String enderecoDeSaida) {
		this.enderecoDeSaida = enderecoDeSaida;
	}

	public String getEnderecoDeChegada() {
		return enderecoDeChegada;
	}
	public void setEnderecoDeChegada(String enderecoDeChegada) {
		this.enderecoDeChegada = enderecoDeChegada;
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
		return Objects.hash(enderecoDeChegada, enderecoDeSaida, id, nome, telefone, viagem);
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
		return Objects.equals(enderecoDeChegada, other.enderecoDeChegada)
				&& Objects.equals(enderecoDeSaida, other.enderecoDeSaida) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(telefone, other.telefone)
				&& Objects.equals(viagem, other.viagem);
	}



	@Override
	public String toString() {
		return "Passageiro [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", enderecoDeSaida="
				+ enderecoDeSaida + ", enderecoDeChegada=" + enderecoDeChegada + ", viagem=" + viagem + "]";
	}	
}
