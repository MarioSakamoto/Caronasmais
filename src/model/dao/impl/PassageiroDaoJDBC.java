package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.PassageiroDao;
import model.entities.Passageiro;

public class PassageiroDaoJDBC implements PassageiroDao {

	private Connection conn;
	
	public PassageiroDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Passageiro obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO passageiro "
					+ "(Id, Nome, Telefone, Viagem_Id) "
					+ "VALUES "
					+ "(DEFAULT,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getId());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getTelefone());
			st.setInt(4, obj.getViagem());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
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
	public void update(Passageiro obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE passageiro "
					+ "SET Nome = ?, Telefone = ?, Viagem_Id = ?"
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getTelefone());
			st.setInt(3, obj.getViagem());
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
			st = conn.prepareStatement("DELETE FROM passageiro WHERE Id = ?");
			
			st.setInt(1, id);
			
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
	public Passageiro findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM passageiro"
					+ "WHERE passageiro.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Passageiro obj = new Passageiro();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				obj.setTelefone(rs.getString("Telefone"));
				obj.setViagem(rs.getInt("Viagem_Id"));
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
	public List<Passageiro> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM passageiro"
					+ " ORDER BY Nome");
			
			rs = st.executeQuery();
			
			List<Passageiro> list = new ArrayList<>();
			
			while (rs.next()) {
				
				Passageiro obj = new Passageiro();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				obj.setTelefone(rs.getString("Telefone"));
				obj.setViagem(rs.getInt("Viagem_Id"));
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

}
