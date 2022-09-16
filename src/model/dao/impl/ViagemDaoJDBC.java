package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.ViagemDao;
import model.entities.Viagem;

public class ViagemDaoJDBC implements ViagemDao {

	private Connection conn;
	
	public ViagemDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Viagem findByDia(String dia) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM viagem WHERE dia = ?");
			st.setString(0, dia);
			rs = st.executeQuery();
			if (rs.next()) {
				Viagem obj = new Viagem();
				obj.setDia(rs.getString("Dia"));
				
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Viagem> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM department ORDER BY Name");
			rs = st.executeQuery();

			List<Viagem> list = new ArrayList<>();

			while (rs.next()) {
				Viagem obj = new Viagem();
				obj.setDia(rs.getString("dia"));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Viagem obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO viagem " +
				"(Nome) " +
				"VALUES " +
				"(?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDia());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					String dia = rs.getString("dia");
					obj.setDia(dia);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Viagem obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE viagem " +
				"WHERE Dia = ?");

			st.setString(1, obj.getDia());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteByDia(String dia) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM viagem WHERE Dia = ?");

			st.setString (0, dia);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(String dia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Viagem findById(String dia) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
