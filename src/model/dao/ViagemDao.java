package model.dao;

import java.util.List;

import model.entities.Viagem;

public interface ViagemDao {

	void insert(Viagem obj);
	void update(Viagem obj);
	void deleteById(Integer id);
	Viagem findById(Integer id);
	List<Viagem> findAll();
}
