package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import main.states.GameState;

public class GameDB {
	//VARIABLES DE CLASE
	private static Statement stmt; //PARA CREAR LAS SENTENCIAS DE SQL
	private static Connection conn; //PARA CREAR LA CONEXION DE LA BD
	private static ResultSet rs; //COGER LA INFO DE LAS TABLAS
	private static GameDB instance = null; //INSTANCIAR LA CLASE 
	/////////////////////////////////////////////////////////////////
	//					CREAR CONEXION A LA BD					   //
	/////////////////////////////////////////////////////////////////
	public static GameDB getInstance() throws GameDBException {
		if (instance == null) {
			instance = new GameDB();
			instance.initSqlite();
			instance.createTableUSUARIO();
			instance.createTablePOSICIONES();
		}
		return instance;
	}	
	public void initSqlite() throws GameDBException{
		try {
			Class.forName("org.sqlite.JDBC");
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha inicializado sqlite correctamente.");
		} catch (ClassNotFoundException e) {
			throw new GameDBException("The JDBC for sqlite has not being found: ", e);
		}
	}
	/////////////////////////////////////////////////////////////////
	//				METODOS PARA CREAR TABLAS					   //
	/////////////////////////////////////////////////////////////////
	public void createTableUSUARIO() throws GameDBException {
		try (	Connection conn= DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			// #COD_MUNDO	NOMBRE	SESIONES
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USUARIO ( COD_MUNDO INT(1) NOT NULL, NOMBRE VARCHAR(15),"
					+ "SESIONES INT(2), PRIMARY KEY (COD_MUNDO) );");			
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Tabla cargada correctamente.");
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos: ", e);
		}
		
	}
	public void createTablePOSICIONES() throws GameDBException {
		try (	Connection conn= DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			// #COD_MUNDO		SESION	 PLAYER_X 	PLAYER_Y 	
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS POSICIONES ( COD_MUNDO INT(1) NOT NULL, SESION INT(2) NOT NULL, PLAYER_X INT(3) NOT NULL,"
					+ "PLAYER_Y INT(3) NOT NULL, PRIMARY KEY (COD_MUNDO,SESION), FOREIGN KEY (COD_MUNDO) REFERENCES USUARIO (COD_MUNDO) ON DELETE CASCADE);");			
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Tabla cargada correctamente.");
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos: ", e);
		}
	}
	/////////////////////////////////////////////////////////////////
	//				METODOS PARA CREAR USUARIOS					   //
	/////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param world (Primary key)
	 * @return boolean if Game Player with primary key world exists 
	 * @throws GameDBException 
	 */
	public static boolean existsGamePlayer(int world) throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			
			rs = stmt.executeQuery("SELECT * FROM USUARIO;");			
			while(rs.next()) {
				if (world == rs.getInt("COD_MUNDO"))
					return true; 
			}
		} catch (SQLException e) {
			throw new GameDBException("Excepción en el resultSet que no da problema en la base de datos: ", e);
		}
		return false; 
	}
	/**
	 * Creates a new game player with the following information
	 * @param world primary key of game player
	 * @param name Name of game Player 
	 * @throws GameDBException 
	 */
	public static void createGamePlayer(int world, String name) throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			
			stmt.executeUpdate(String.format("INSERT INTO USUARIO VALUES(%d,'%s',0);",world, name));
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha creado correctamente el usuario nuevo.");
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos: ", e);
		}		
	}
	/**
	 * 
	 * @param world (primary key)
	 * @return the Name of the player with primary key world
	 * @throws GameDBException 
	 */
	public static String getGamePlayer(int world) throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();
			){			
			
			rs = stmt.executeQuery(String.format("SELECT NOMBRE FROM USUARIO WHERE COD_MUNDO = %d;",world));
			while (rs.next()) {
				return rs.getString("NOMBRE");
			}			
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos: ", e);
		}
		return "EMPTY WORLD"; // EN ESTE CASO NO HAY USUARIO EN LA BD; NOMBRE SE INICIA CON "EMPTY WORLD"		
	}
	/////////////////////////////////////////////////////////////////
	// 					METODOS PARA GUARDAR POSICIONES 		   //
	/////////////////////////////////////////////////////////////////
	public static boolean existsUserPosition(int world) throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			
			rs = stmt.executeQuery(String.format("SELECT * FROM POSICIONES WHERE COD_MUNDO = %d;",world));			
			while(rs.next()) {
				if (world == rs.getInt("COD_MUNDO"))
					return true; 
			}
		} catch (SQLException e) {
			throw new GameDBException("Excepción en el resultSet que no da problema en la base de datos: ", e);
		}
		return false; 
	}
	
	public static void updatePosition() throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			
			PreparedStatement ps = conn.prepareStatement("UPDATE POSICIONES SET SESION = ?, PLAYER_X = ?, PLAYER_Y = ? WHERE COD_MUNDO = ?;");
			ps.setInt(1, getNumberSessions(GameState.getUser()));
			ps.setInt(2, GameState.getPlayerXPosition());
			ps.setInt(3, GameState.getPlayerYPosition());
			ps.setInt(4, GameState.getUser());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos: ", e);
		}
	}
	public static void insertIntoPosiciones(int world, int x, int y) throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			int sesiones = getNumberSessions(world);
			stmt.executeUpdate(String.format("INSERT INTO POSICIONES VALUES(%d,%d,%d,%d);",world, sesiones,x,y));
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha creado correctamente la nueva posicion del usuario.");
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos: ", e);
		}
	}
	public static int getNumberSessions(int world) throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			
			rs = stmt.executeQuery(String.format("SELECT SESIONES FROM USUARIO WHERE COD_MUNDO = %d;",world));
			while (rs.next()) {
				return rs.getInt("SESIONES");
			}
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha creado correctamente el usuario nuevo.");
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos: ", e);
		}
		return -1;
	}
	/////////////////////////////////////////////////////////////////
	//					METODOS PARA ACTUALIZAR USUARIOS		   //
	/////////////////////////////////////////////////////////////////	
	/**
	 * Increases by 1 the sessions logged in of the player
	 * @param world (primary key)	 *
	 */
	public static void incSessionNumber(int world) {
		new Thread(()->{
				try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
						Statement stmt = conn.createStatement();						
					){
					
					rs = stmt.executeQuery(String.format("SELECT SESIONES FROM USUARIO WHERE COD_MUNDO = %d", world));
					int num;
					if (rs != null) {					
							num = rs.getInt("SESIONES");
							num++;
							updateSessionNumber(num,world);
					} 				
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}).start();
	}
	private static void updateSessionNumber(int sessions, int world) {
		new Thread( () ->{
			try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
					Statement stmt = conn.createStatement();					
				){
					PreparedStatement ps = conn.prepareStatement("UPDATE USUARIO SET SESIONES = ? WHERE COD_MUNDO = ?;");
					ps.setInt(1, sessions);
					ps.setInt(2, world);
					ps.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();	
			} 
		}).start();		
	}	
	/////////////////////////////////////////////////////////////////
	//					METODO PARA ELIMINAR USUARIOS			   //
	/////////////////////////////////////////////////////////////////
	/**
	 * deletes table users
	 * @throws GameDBException 
	 */
	public static void deleteUsers() throws GameDBException {
		try (	
				Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");				
			){
			
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE USUARIO;");
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha borrado toda la información de los usuarios");
			
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos: ", e);
		}
	}
	/**
	 * deletes a specific user with the primary key world
	 * @param world (primary key)
	 * @throws GameDBException 
	 */
	public static void deleteUsers(int world) throws GameDBException {
		try (	
				Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
			){
			
			stmt = conn.createStatement();
			stmt.executeUpdate(String.format("DELETE FROM USUARIO WHERE COD_MUNDO = %d;",world));
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha borrado toda la información del usuario "+world);
			
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos: ", e);
		}
	}
	
}