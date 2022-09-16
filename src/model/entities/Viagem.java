package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Viagem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dia;
	private String hora;
	private String saindoDe;
	private String indoPara;
	
	
	public Viagem() {
	}

	public Viagem(String dia, String hora, String saindoDe, String indoPara) {
		super();
		this.dia = dia;
		this.hora = hora;
		this.saindoDe = saindoDe;
		this.indoPara = indoPara;
	}



	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(dia, hora, indoPara, saindoDe);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Viagem other = (Viagem) obj;
		return Objects.equals(dia, other.dia) && Objects.equals(hora, other.hora)
				&& Objects.equals(indoPara, other.indoPara) && Objects.equals(saindoDe, other.saindoDe);
	}

	@Override
	public String toString() {
		return "Viagem [dia=" + dia + ", hora=" + hora + ", saindoDe=" + saindoDe + ", indoPara=" + indoPara + "]";
	}



	
}
