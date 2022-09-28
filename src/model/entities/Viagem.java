package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Viagem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String data;
	
	public Viagem() {
	}

	public Viagem(Integer id, String data) {
		super();
		this.id = id;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, id);
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
		return Objects.equals(data, other.data) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Viagem [id=" + id + ", data=" + data + "]";
	}
}
