package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class GameDB {
	//VARIABLES DE CLASE
	private static Statement stmt; //PARA CREAR LAS SENTENCIAS DE SQL
	private static Connection conn; //PARA CREAR LA CONEXION DE LA BD
	private static ResultSet rs; //COGER LA INFO DE LAS TABLAS
	private static GameDB instance = null; //INSTANCIAR LA CLASE 
	/////////////////////////////////////////////////////////////////
	//					CREAR CONEXION A LA BD					   //
	/////////////////////////////////////////////////////////////////
	public static GameDB getInstance() {
		if (instance == null) {
			instance = new GameDB();
			instance.initSqlite();
			instance.createTableUSUARIO();
		}
		return instance;
	}
	
	public void initSqlite() {
		try {
			Class.forName("org.sqlite.JDBC");
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha inicializado sqlite correctamente.");
		} catch (ClassNotFoundException e) {
			Game.LOGGER.log(Level.SEVERE,"No se ha podido inicializar sqlite");
			
		}
	}
	public void createTableUSUARIO() {
		try (
				Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();
				
			){
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USUARIO ( COD_MUNDO INT(1) NOT NULL, NOMBRE VARCHAR(15), PRIMARY KEY (COD_MUNDO) );");			
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Tabla cargada correctamente.");
		} catch (SQLException e) {
			Game.LOGGER.log(Level.SEVERE,"Ha ocurrido un error al ejecutar una sentencia de la base de datos. "+Game.getStackTrace(e));
		}
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	/*
	 * 			METODOS PARA CREAR USUARIOS ---> INSERT INTO <>
	 */
	/////////////////////////////////////////////////////////////////////////////////
	public static boolean checkGamePlayer(int world) {
		try (
				Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();
				
			){
			
			rs = stmt.executeQuery("SELECT * FROM USUARIO;");			
			while(rs.next()) {
				if (world == rs.getInt("COD_MUNDO"))
					return true; // NO HAY QUE CREAR EL USUARIO
			}
		} catch (SQLException e) {
			System.out.println("Excepción en el resultSet: " + e + " no da problema en la base de datos");
		}
		return false; // HAY QUE CREAR USUARIO NUEVO
	}
	public static void createGamePlayer(int world, String name) {
		try (
				Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();
				
			){
			
			stmt.executeUpdate(String.format("INSERT INTO USUARIO VALUES(%d,'%s');",world, name));
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha creado correctamente el usuario nuevo.");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 
	 */
	////////////////////////////////////////////////////////////////////////////////////
	public static String getGamePlayer(int world) {
		try (
				Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();
				
			){
			
			rs = stmt.executeQuery(String.format("SELECT NOMBRE FROM USUARIO WHERE COD_MUNDO = %d;",world));
			if (rs != null) {
				return rs.getString("NOMBRE");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// EN ESTE CASO NO HAY USUARIO EN LA BD
		return "EMPTY WORLD: "+world;
		
	}
	
//	public static void printUsers() {
//		try (	
//				Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
//			){
//			
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery("SELECT NOMBRE FROM USUARIO;");
//			
//			while(rs.next() && rs != null) {
//				System.out.println(rs.getString("NOMBRE"));
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
	public static void deleteUsers() {
		try (	
				Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
			){
			
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE USUARIO;");
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha borrado toda la información de los usuarios");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
