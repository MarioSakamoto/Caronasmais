package model.dao;

import java.util.List;

import model.entities.Viagem;
import model.entities.Passageiro;

public interface PassageiroDao {

	void insert(Passageiro obj);
	void update(Passageiro obj);
	void deleteById(Integer id);
	Passageiro findById(Integer id);
	List<Passageiro> findAll();
}
