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
}


