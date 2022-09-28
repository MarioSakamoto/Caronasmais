package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.PassageiroDao;
import model.entities.Passageiro;
import model.entities.Viagem;

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
					+ "(Id, Nome, Telefone, SaindoDe, IndoPara, ViagemId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getId());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getTelefone());
			st.setString(4, obj.getSaindoDe());
			st.setString(5, obj.getIndoPara());
			st.setInt(6, obj.getViagem().getId());
			
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
					+ "SET Id = ?, Nome = ?, Telefone = ?, SaindoDe = ?, IndoPara = ?, ViagemId = ? "
					+ "WHERE Id = ?");
			
			st.setInt(1, obj.getId());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getTelefone());
			st.setString(4, obj.getSaindoDe());
			st.setString(5, obj.getIndoPara());
			st.setInt(6, obj.getViagem().getId());
			
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
					"SELECT passageiro.*,viagem.Nome as ViagemName "
					+ "FROM passageiro INNER JOIN viagem "
					+ "ON passageiro.ViagemId = viagem.Id "
					+ "WHERE passageiro.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Viagem viagem = instantiateViagem(rs);
				Passageiro obj = instantiatePassageiro(rs, viagem);
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

	private Passageiro instantiatePassageiro(ResultSet rs, Viagem viagem) throws SQLException {
		Passageiro obj = new Passageiro();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Nome"));
		obj.setTelefone(rs.getString("Telefone"));
		obj.setSaindoDe(rs.getString("SaindoDe"));
		obj.setIndoPara(rs.getString("IndoPara"));
		obj.setViagem(viagem);
				
		return obj;
	}

	private Viagem instantiateViagem(ResultSet rs) throws SQLException {
		Viagem viagem = new Viagem();
		viagem.setId(rs.getInt("ViagemId"));
		viagem.setData(rs.getString("Data"));
		return viagem;
	}

	@Override
	public List<Passageiro> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT passageiro.*,viagem.Nome as ViagemNome "
					+ "FROM passageiro INNER JOIN viagem "
					+ "ON passageiro.ViagemId = viagem.Id "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Passageiro> list = new ArrayList<>();
			Map<Integer, Viagem> map = new HashMap<>();
			
			while (rs.next()) {
				
				Viagem viagem = map.get(rs.getInt("ViagemId"));
				
				if (viagem == null) {
					viagem = instantiateViagem(rs);
					map.put(rs.getInt("ViagemId"), viagem);
				}
				
				Passageiro obj = instantiatePassageiro(rs, viagem);
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
	public List<Passageiro> findByViagem(Viagem viagem) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT passageiro.*,viagem.Nome as ViagemNome "
					+ "FROM passageiro INNER JOIN viagem "
					+ "ON passageiro.ViagemId = viagem.Id "
					+ "WHERE ViagemId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, viagem.getId());
			
			rs = st.executeQuery();
			
			List<Passageiro> list = new ArrayList<>();
			Map<Integer, Viagem> map = new HashMap<>();
			
			while (rs.next()) {
				
				Viagem viagemId = map.get(rs.getInt("ViagemId"));
				
				if (viagem == null) {
					viagem = instantiateViagem(rs);
					map.put(rs.getInt("ViagemId"), viagem);
				}
				
				Passageiro obj = instantiatePassageiro(rs, viagem);
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
