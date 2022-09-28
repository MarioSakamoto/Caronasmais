package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PassageiroDao;
import model.entities.Passageiro;

public class PassageiroService {

	private PassageiroDao dao = DaoFactory.createPassageiroDao();

	public List<Passageiro> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Passageiro obj) {
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Passageiro obj) {
		dao.deleteById(obj.getId());
	}
}
