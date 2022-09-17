package model.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Viagem implements Serializable {

	private static final long serialVersionUID = 1L;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private Integer id;
	private Date dataHora;
	
	public Viagem() {
	}

	public Viagem( Integer id, Date dataHora) {
		this.id = id;
		this.dataHora = dataHora;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataHora, id);
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
		return Objects.equals(dataHora, other.dataHora) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Viagem [id=" + id + ", dataHora=" + dataHora + "]";
	}
}
