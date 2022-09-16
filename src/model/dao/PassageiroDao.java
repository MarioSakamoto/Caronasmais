package model.dao;

import java.util.List;

import model.entities.Passageiro;
import model.entities.Viagem;

public interface PassageiroDao {

	void insert(Passageiro obj);
	void update(Passageiro obj);
	void deleteById(String nome);
	Passageiro findById(String nome);
	List<Passageiro> findAll();
	List<Passageiro> findByDepartment(Passageiro passageiro);
	List<Passageiro> findByViagem(Viagem viagem);
}
