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
			instance.connectDB();
			instance.createTableUSUARIO();
		}
		return instance;
	}
	public void connectDB() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha conectado correctamente a la base de datos.");
		} catch (SQLException | ClassNotFoundException e) {
			Game.LOGGER.log(Level.SEVERE,"No se ha podido conectar a la base de datos. Error al conectar a la BD.");
			
		}
	}
	public void createTableUSUARIO() {
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USUARIO ( COD_MUNDO INT(1) NOT NULL, NOMBRE VARCHAR(15), PRIMARY KEY (COD_MUNDO) );");			
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Tabla cargada correctamente.");
		} catch (SQLException e) {
			Game.LOGGER.log(Level.SEVERE,"Ha ocurrido un error al ejecutar una sentencia de la base de datos. "+Game.getStackTrace(e));
		}
		
	}
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			Game.LOGGER.log(Level.SEVERE,"Ha ocurrido un error al cerrar la BD. "+Game.getStackTrace(e));
		}
	}
	/////////////////////////////////////////////////////////////////////////////////
	/*
	 * 			METODOS PARA CREAR USUARIOS ---> INSERT INTO <>
	 */
	/////////////////////////////////////////////////////////////////////////////////
	public static boolean checkGamePlayer(int world) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM USUARIO;");			
			while(rs.next()) {
				if (world == rs.getInt("COD_MUNDO"))
					return false; // NO HAY QUE CREAR EL USUARIO
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true; // HAY QUE CREAR USUARIO NUEVO
	}
	public static void createGamePlayer(int world, String name) {
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(String.format("INSERT INTO USUARIO VALUES(%d,'%s');",world,name));
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
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format("SELECT NOMBRE FROM USUARIO WHERE COD_MUNDO = %d;",world));
			if (!(rs==null)) {
				return rs.toString();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// EN ESTE CASO DEVOLVEMOS EN EFECTO UN STRING PERO ESTE NO APARECE EN LA TABLA USUARIOS
		return "DEFALT WORLD NAME - "+world;
	}
	
}
