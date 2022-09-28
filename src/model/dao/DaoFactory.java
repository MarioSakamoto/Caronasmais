package model.dao;

import db.DB;
import model.dao.impl.PassageiroDaoJDBC;
import model.dao.impl.ViagemDaoJDBC;

public class DaoFactory {

	public static PassageiroDao createPassageiroDao() {
		return new PassageiroDaoJDBC(DB.getConnection());
	}
	
	public static ViagemDao createViagemDao() {
		return new ViagemDaoJDBC(DB.getConnection());
	}
}
