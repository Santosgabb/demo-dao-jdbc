package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityExcepition;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = DB.getConnection();

			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE " // E NECESSESARIO CLOCAR O WHERE ,SE NAO APAGA TODO MUNDO
					+ "Id = ? ");
			st.setInt(1, 96);
		//	st.setInt(1, 87);
			int linhasAfetada = st.executeUpdate();

			System.out.println("DONE! Linhas Atualizadas  " + linhasAfetada);
		} catch (SQLException e) {
			throw new DbIntegrityExcepition(e.getMessage());//mensagem personalizada 
		} finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}