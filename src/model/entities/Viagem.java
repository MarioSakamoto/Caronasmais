package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Viagem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private LocalDate data;
	private LocalTime hora;
	
	public Viagem() {
	}
	
	public Viagem(Integer id, LocalDate data, LocalTime hora) {
		super();
		this.id = id;
		this.data = data;
		this.hora = hora;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
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
		Viagem other = (Viagem) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Viagem [id=" + id + ", data=" + data + ", hora=" + hora + "]";
	}
	
	

	
}
