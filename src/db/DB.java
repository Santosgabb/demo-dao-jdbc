package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	// P connectar com o banco de dados
	private static Connection conn = null;

	// criar metodo p conectar com o banco dedados
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();// pegou as propiedades do banco de dados atraves do loadPropied
				String url = props.getProperty("dburl");
				// pegar conexao com o banco de dados
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;// a conexao esta salva agr no conn
	}

	// fechar a conexao c metodo
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());

			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());// se de erro vai ser acionado esse bloco que vai ser acionado a outra
													// classe com uma mensagem personalizada
		}
	}

//metodos auxiliares do statetam e ResultSet
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	
}
