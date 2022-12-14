package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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
	public Viagem findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM viagem WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Viagem obj = new Viagem();
				obj.setId(rs.getInt("Id"));
				obj.setTrajeto(rs.getString("Trajeto"));
				obj.setData(new java.util.Date(rs.getTimestamp("Data").getTime()));
				obj.setHora(rs.getTime("Hora").toLocalTime());				
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
				"SELECT * FROM viagem ORDER BY Data");
			rs = st.executeQuery();

			List<Viagem> list = new ArrayList<>();

			while (rs.next()) {
				Viagem obj = new Viagem();
				obj.setId(rs.getInt("Id"));
				obj.setTrajeto(rs.getString("Trajeto"));
				obj.setData(new java.util.Date(rs.getTimestamp("Data").getTime()));
				obj.setHora(rs.getTime("Hora").toLocalTime());
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
				"(Id, Trajeto, Data, Hora) " +
				"VALUES (DEFAULT,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getTrajeto());
			st.setDate(2, new java.sql.Date(obj.getData().getTime()));
			st.setTime(3, Time.valueOf(obj.getHora()));

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
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
				"SET Trajeto = ?, Data = ?, Hora = ?" +
				"WHERE Id = ?");

			st.setString(1, obj.getTrajeto());
			st.setDate(2, new java.sql.Date(obj.getData().getTime()));
			st.setTime(3, Time.valueOf(obj.getHora()));
			st.setInt(4, obj.getId());

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
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM viagem WHERE Id = ?");

			st.setInt(1, id);
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
}
