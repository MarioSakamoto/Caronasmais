package model.entities;

import java.io.Serializable;
import java.util.Objects;

import javafx.scene.input.DataFormat;

public class Viagem implements Serializable {

	private static final long serialVersionUID = 1L;

	private DataFormat data;
	private DataFormat hora;
	private String saindoDe;
	private String indoPara;
	
	
	public Viagem() {
	}

	public Viagem(DataFormat data, DataFormat hora, String saindoDe, String indoPara) {
		super();
		this.data = data;
		this.hora = hora;
		this.saindoDe = saindoDe;
		this.indoPara = indoPara;
	}



	public DataFormat getData() {
		return data;
	}

	public void setData(DataFormat data) {
		this.data = data;
	}

	public DataFormat getHora() {
		return hora;
	}

	public void setHora(DataFormat hora) {
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
		return Objects.hash(data, hora, indoPara, saindoDe);
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
		return Objects.equals(data, other.data) && Objects.equals(hora, other.hora)
				&& Objects.equals(indoPara, other.indoPara) && Objects.equals(saindoDe, other.saindoDe);
	}

	@Override
	public String toString() {
		return "Viagem [data=" + data + ", hora=" + hora + ", saindoDe=" + saindoDe + ", indoPara=" + indoPara + "]";
	}

	
}
