
package practica1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class ConexionBD {
	public static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver"; 
	public static final String URL_MYSQL = "jdbc:mysql://localhost:3306/Practica_2";

	
		private static ConexionBD conexionMySql = null;
	
		private ConexionBD(){
			cargarDriver();
		}
		
		public static ConexionBD getInstancia(){
			if(conexionMySql == null){
				conexionMySql = new ConexionBD();
			}
			return conexionMySql;
		}
				
		public void cargarDriver() {
			try {
				Class.forName(DRIVER_MYSQL);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	
		public Connection getConexion(){
			Connection conexion = null;
			try {
				conexion = DriverManager.getConnection(URL_MYSQL,"root","password");
			} catch (SQLException e) {
				e.printStackTrace();
			} 	
			return conexion;
		}

		public void ejecutar(final String sql)  {
			try {
				Statement stmt = getConexion().createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public ResultSet ejecutarConsulta(final String sql) {
			ResultSet rs = null;
			try {
				rs = getConexion().createStatement().executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error en >>>>> ejecutaSELECT(String select 1) "+sql);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error en >>>>> ejecutaSELECT(String select 2) "+sql);
			}
			return rs;
		}
		
		public void cerrarconexion(){
			try {
				getConexion().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
	
