 package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Passageiro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String telefone;
	private String enderecoDeSaida;
	private String enderecoDeChegada;
	private Integer volumeDeBagagem;
	
	private Viagem viagem;
	
	public Passageiro() {
	}

	public Passageiro(String nome, String telefone, String enderecoDeSaida, String enderecoDeChegada,
			Integer volumeDeBagagem, Viagem viagem) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.enderecoDeSaida = enderecoDeSaida;
		this.enderecoDeChegada = enderecoDeChegada;
		this.volumeDeBagagem = volumeDeBagagem;
		this.viagem = viagem;
	}

	public String getName() {
		return nome;
	}

	public void setName(String nome) {
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

	public Integer getVolumeDeBagagem() {
		return volumeDeBagagem;
	}

	public void setVolumeDeBagagem(Integer volumeDeBagagem) {
		this.volumeDeBagagem = volumeDeBagagem;
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
		return Objects.hash(enderecoDeChegada, enderecoDeSaida, nome, telefone, viagem, volumeDeBagagem);
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
				&& Objects.equals(enderecoDeSaida, other.enderecoDeSaida) && Objects.equals(nome, other.nome)
				&& Objects.equals(telefone, other.telefone) && Objects.equals(viagem, other.viagem)
				&& Objects.equals(volumeDeBagagem, other.volumeDeBagagem);
	}

	@Override
	public String toString() {
		return "Passageiro [nome=" + nome + ", telefone=" + telefone + ", enderecoDeSaida=" + enderecoDeSaida
				+ ", enderecoDeChegada=" + enderecoDeChegada + ", volumeDeBagagem=" + volumeDeBagagem + ", viagem="
				+ viagem + "]";
	}

	
}
