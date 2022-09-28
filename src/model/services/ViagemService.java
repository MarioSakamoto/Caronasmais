package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ViagemDao;
import model.entities.Viagem;

public class ViagemService {

	private ViagemDao dao = DaoFactory.createViagemDao();

	public List<Viagem> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Viagem obj) {
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Viagem obj) {
		dao.deleteById(obj.getId());
	}
}
