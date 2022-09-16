package model.dao;

import java.util.List;

import model.entities.Viagem;

public interface ViagemDao {

	void insert(Viagem obj);
	void update(Viagem obj);
	void deleteById(String dia);
	Viagem findById(String dia);
	List<Viagem> findAll();
	Viagem findByDia(String dia);
	void deleteByDia(String dia);
}
