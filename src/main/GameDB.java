package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.states.GameState;

public class GameDB {
		
	private static ResultSet rs; //COGER LA INFO DE LAS TABLAS
	private static GameDB instance = null; //INSTANCIAR LA CLASE 
	/////////////////////////////////////////////////////////////////
	//					CREAR CONEXION A LA BD					   //
	/////////////////////////////////////////////////////////////////
	public static GameDB getInstance() throws GameDBException {
		if (instance == null) {
			instance = new GameDB();
			instance.initSqlite();
			instance.createTableUSERS();
			instance.createTableINVENTORY();
		}
		return instance;
	}	
	public void initSqlite() {
		try {
			Class.forName("org.sqlite.JDBC");
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha inicializado sqlite correctamente.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		}
	}
	/////////////////////////////////////////////////////////////////
	//				METODOS PARA CREAR TABLAS					   //
	/////////////////////////////////////////////////////////////////
	public void createTableUSERS() throws GameDBException {
		try (	Connection conn= DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			// #USER_CODE	USERNAME	SESSIONS	PLAYER_X	PLAYER_Y
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USERS ( USER_CODE INT(1) NOT NULL, USERNAME VARCHAR(15),"
								+ "SESSIONS INT(2), PLAYER_X INT(3), PLAYER_Y INT(3), PRIMARY KEY (USER_CODE) );" );			
			
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Tabla USER cargada correctamente.");
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}
		
	}
	public void createTableINVENTORY() throws GameDBException {
		try (	Connection conn= DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			//#USER_CODE	#ITEM_ID	ITEM_NAME	ITEM_X	ITEM_Y		ITEM_INDEX		QUANTITY				 	
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS INVENTORY ( USER_CODE INT(1) NOT NULL, ITEM_ID INT(1) "
							  + "NOT NULL,ITEM_NAME VARCHAR(25), ITEM_X INT(3), ITEM_Y INT(3), ITEM_INDEX INT(1) "
							  + ",QUANTITY INT(2), PRIMARY KEY (USER_CODE, ITEM_ID), FOREIGN KEY (USER_CODE)	 "
							  + "REFERENCES USERS (USER_CODE) ON DELETE CASCADE ); ");			
			
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Tabla INVENTORY cargada correctamente.");
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
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
			
			rs = stmt.executeQuery("SELECT * FROM USERS;");			
			while(rs.next()) {
				if (world == rs.getInt("USER_CODE"))
					return true; 
			}
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
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
			
			stmt.executeUpdate(String.format("INSERT INTO USERS VALUES(%d,'%s',0,null,null);",world, name));
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha creado correctamente el usuario nuevo.");
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}		
	}
	/**
	 * 
	 * @param world (primary key)
	 * @return the Name of the player with primary key world
	 */
	public static String getGameUserName(int world) {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();
			){			
			
			rs = stmt.executeQuery(String.format("SELECT USERNAME FROM USERS WHERE USER_CODE = %d;",world));
			while (rs.next()) {
				return rs.getString("USERNAME");
			}			
		} catch (SQLException e) {
			e.printStackTrace();		}
		return "EMPTY WORLD"; // EN ESTE CASO NO HAY USUARIO EN LA BD; NOMBRE SE INICIA CON "EMPTY WORLD"		
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
					
					rs = stmt.executeQuery(String.format("SELECT SESSIONS FROM USERS WHERE USER_CODE = %d", world));
					int num;
					if (rs != null) {					
							num = rs.getInt("SESSIONS");
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
					PreparedStatement ps = conn.prepareStatement("UPDATE USERS SET SESSIONS = ? WHERE USER_CODE = ?;");
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
				Statement stmt = conn.createStatement();
			){
			
			
			stmt.executeUpdate("DROP TABLE USERS;");
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha borrado toda la información de los usuarios");
			
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
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
				Statement stmt = conn.createStatement();
			){
			
			stmt.executeUpdate(String.format("DELETE FROM USERS WHERE USER_CODE = %d;",world));
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha borrado toda la información del usuario "+world);
			
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}
	}
	/////////////////////////////////////////////////////////////////
	// 					METODOS PARA GUARDAR POSICIONES 		   //
	/////////////////////////////////////////////////////////////////
	/**
	 * position already existed and its being updated
	 * @throws GameDBException 
	 */
	public static void updatePosition() throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			
			PreparedStatement ps = conn.prepareStatement("UPDATE USERS SET SESSIONS = ?, PLAYER_X = ?, PLAYER_Y = ? WHERE USER_CODE = ?;");
			ps.setInt(1, getNumberSessions(GameState.getUser()));
			ps.setInt(2, GameState.getPlayerXPosition());
			ps.setInt(3, GameState.getPlayerYPosition());
			ps.setInt(4, GameState.getUser());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}
	}
	/////////////////////////////////////////////////////////////////
	// 					METODOS PARA LEER POSICIONES	 		   //
	/////////////////////////////////////////////////////////////////
	public static int getGamePlayerXPosition(int world) {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			rs = stmt.executeQuery(String.format("SELECT PLAYER_X FROM USERS WHERE USER_CODE = %d",world));
			return rs.getInt("PLAYER_X");
		} catch (SQLException e) {
			//throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}
		return 150;
	}
	public static int getGamePlayerYPosition(int world) {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			rs = stmt.executeQuery(String.format("SELECT PLAYER_Y FROM USERS WHERE USERS = %d",world));
			return rs.getInt("PLAYER_Y");
		} catch (SQLException e) {
			//throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}
		return 170;
	}
	public static int getNumberSessions(int world) throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			
			rs = stmt.executeQuery(String.format("SELECT SESSIONS FROM USERS WHERE USER_CODE = %d;",world));
			while (rs.next()) {
				return rs.getInt("SESSIONS");
			}
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha creado correctamente el usuario nuevo.");
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}
		return -1; 
	}
	/////////////////////////////////////////////////////////////////////////
	//				METODOS PARA CREAR FILAS EN INVENTORY				   //
	/////////////////////////////////////////////////////////////////////////
	public static boolean existsObjectsInPlayersInventory(int user_code) throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			
			rs = stmt.executeQuery(String.format("SELECT * FROM INVENTORY WHERE USER_CODE = %d AND ITEM_ID = %d;",user_code));			
			while(rs.next()) {
				if (user_code == rs.getInt("USER_CODE"))
					return true; 
			}
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}
		return false; 
	}
	public static void insertIntoInventory(int id, String name, int x, int y, int index , int quantity) throws GameDBException {
		try (	Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();				
			){
			
			PreparedStatement ps = conn.prepareStatement(	"INSERT INTO INVENTORY VALUES (?,?,?,?,?,?,?);"		);
			ps.setInt(1, GameState.getUser());
			ps.setInt(2, id);
			ps.setString(3, name);
			ps.setInt(4, x);
			ps.setInt(5, y);			
			ps.setInt(6, index);
			ps.setInt(7, quantity);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}
	}
	public static void deleteInventory(int world) throws GameDBException {
		try (	
				Connection conn = DriverManager.getConnection("jdbc:sqlite:StoneChaserDB.db");
				Statement stmt = conn.createStatement();
			){
			
			stmt.executeUpdate(String.format("DELETE FROM INVENTORY WHERE USER_CODE = %d;",world));
			Game.LOGGER.log(Game.LOGGER.getLevel(),"Se ha borrado toda la información del usuario "+world);
			
		} catch (SQLException e) {
			throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
		}
	}
}	