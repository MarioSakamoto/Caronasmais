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
					+ "(Nome, Telefone, EnderecoDeSaida, EnderecoDeChegada, VolumeDeBagagem, ViagemDia) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getTelefone());
			st.setString(3, obj.getEnderecoDeSaida());
			st.setString(4, obj.getEnderecoDeChegada());
			st.setInt(5, obj.getVolumeDeBagagem());
			st.setString(6, obj.getViagem().getDia());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					String nome = rs.getString(nome);
					obj.setNome(nome);
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
					+ "SET Nome = ?, Telefone = ?, EnderecoDeSaida = ?, EnderecoDeChegada = ?,"
					+ "VolumeDeBagagem = ?, ViagemDia =?"
					+ "WHERE Nome = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getTelefone());
			st.setString(3, obj.getEnderecoDeSaida());
			st.setString(4, obj.getEnderecoDeChegada());
			st.setInt(5, obj.getVolumeDeBagagem());
			st.setString(6, obj.getViagem().getDia());
			
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
	public void deleteById(String nome) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM passageiro WHERE Nome = ?");
			
			st.setString(nome);
			
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
	public Passageiro findById(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT passageiro.*, viagem.dia as viagemDia "
					+ "FROM passageiro INNER JOIN viagem "
					+ "ON passageiro.ViagemDia = viagem.Dia "
					+ "WHERE passageiro.Nome = ?");
			
			st.setString(nome);
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
		
		
		obj.getNome(rs.getString("Nome"));
		obj.setTelefone(rs.getString("Telefone"));
		obj.setEnderecoDeSaida(rs.getString("EnderecoDeSaida"));
		obj.setEnderecoDeChegada(rs.getString("EnderecoDeChegada"));
		obj.setVolumeDeBagagem(rs.getInt("VolumeDeBagagem"));
		obj.setViagem(viagem);
		return obj;
	}

	private Viagem instantiateViagem(ResultSet rs) throws SQLException {
		Viagem viagem = new Viagem();
		viagem.setDia(rs.getString("ViagemDia"));
		return viagem;
	}

	@Override
	public List<Passageiro> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT passageiro.*,viagem.dia as ViagemDia"
					+ "FROM passageiro INNER JOIN viagem "
					+ "ON passageiro.ViagemDia = viagem.Dia "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Passageiro> list = new ArrayList<>();
			Map<String, Viagem> map = new HashMap<>();
			
			while (rs.next()) {
				
				Viagem viagem = map.get(rs.getString("ViagemDia"));
				
				if (viagem == null) {
					viagem = instantiateViagem(rs);
					map.put(rs.getInt("ViagemDia"), viagem);
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
					"SELECT passageiro.*,viagem.dia as ViagemDia "
					+ "FROM passageiro INNER JOIN viagem "
					+ "ON passageiro.ViagemDia = Viagem.Dia "
					+ "WHERE ViagemDia = ? "
					+ "ORDER BY Name");
			
			st.setString(viagem.getDia());
			
			rs = st.executeQuery();
			
			List<Passageiro> list = new ArrayList<>();
			Map<String, Viagem> map = new HashMap<>();
			
			while (rs.next()) {
				
				Viagem viagem = map.get(rs.getString("ViagemDia"));
				
				if (viagem == null) {
					viagem = instantiateViagem(rs);
					map.put(rs.getString("ViagemDia"), viagem);
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
