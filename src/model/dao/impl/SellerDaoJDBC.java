package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
				try {
					st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "	VALUES "
					+"(?, ?, ?, ?, ?)" , 
					Statement.RETURN_GENERATED_KEYS);
					
				st.setString(1, obj.getName());
				st.setString(2, obj.getEmail());
				st.setDate(3, new java.sql.Date(obj.getBirhDate().getTime()));
				st.setDouble(4, obj.getBaseSalary());
				st.setInt(5, obj.getDepartment().getId());
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
					throw new DbException("Erro inesperado");
				}
				}
				catch(SQLException e) {
					throw new DbException(e.getMessage());
				}
				finally {
					DB.closeStatement(st);
				}
	}

	@Override
	public void update(Seller obj) {

	}

	@Override
	public void deleteById(Integer id) {
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instatiateDepartment(rs);
				Seller obj = intatiateSeller(rs, dep);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	// foi criado um método auxiliar , e sera propagado a excecao
	private Seller intatiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirhDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);

		return obj;
	}

	private Department instatiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<Seller>();

			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				// vai ser guardado dentro do map qualquer departament que passar
				Department dep = map.get(rs.getInt("DepartmentId"));// vai testar se o departament ja existe vai ser
																	// pego
				// ai o if vai dar falso e vai continuar o codigo
				if (dep == null) {
					// se o departament nao existir , o if vai da verdadeiro e vai salvar no map
					dep = instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = intatiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department departmrnt) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			st.setInt(1, departmrnt.getId());
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<Seller>();
			// vai ser utilizado o map para nao repetir o department
			Map<Integer, Department> map = new HashMap<>();// foi criado um map vazio

			while (rs.next()) {
				// vai ser guardado dentro do map qualquer departament que passar
				Department dep = map.get(rs.getInt("DepartmentId"));// vai testar se o departament ja existe vai ser
																	// pego
				// ai o if vai dar falso e vai continuar o codigo
				if (dep == null) {
					// se o departament nao existir , o if vai da verdadeiro e vai salvar no map
					dep = instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = intatiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
