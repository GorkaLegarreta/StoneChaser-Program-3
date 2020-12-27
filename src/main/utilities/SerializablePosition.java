package main.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import main.Game;
import main.GameDB;
import main.GameDBException;

public class SerializablePosition implements Serializable {
	//TODO clase window gestiona de momento la E/S fichero mirar donde
	private static final long serialVersionUID = 4406811622769873024L;
	private static int cod_mundo;
	private static int session;
	private static int player_x;
	private static int player_y;
	
	public SerializablePosition(int cod_mundo, int session,int player_x,int player_y) {
		this.cod_mundo = cod_mundo;
		this.session = session;
		this.player_x = player_x;
		this.player_y = player_y;
	}
	@Override
	public String toString() {
		return String.format("cod_mundo: %d, session: %d, player_x: %d, player_y: %d",cod_mundo,session,player_x,player_y);
	}
	
	public int getCod_mundo() {
		return cod_mundo;
	}
	public int getSession() {
		return session;
	}
	public int getPlayer_x() {
		return player_x;
	}
	public int getPlayer_y() {
		return player_y;
	}
	/////////////////////////////////////////////////////////////////
	//				METODOS PARA SERIALIZAR OBJETO				   //
	/////////////////////////////////////////////////////////////////
	public static void saveIntoBinaryFile(SerializablePosition prueba) {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("positions.bin"))) {
	        os.writeObject(prueba);
	        System.out.println(prueba);
	        Game.LOGGER.log(Level.FINEST, "Datos serializados correctamente");
	    } catch (IOException e) {
	    	Game.LOGGER.log(Level.SEVERE, "Error al serializar los datos");
	    }	
	}
	
	public static void readFromBinaryFile() throws GameDBException {
		try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("positions.bin"))) {
			for (int i=1; i<5; i++) {
				try {
					if (GameDB.existsUserPosition(i)) {
						SerializablePosition p = (SerializablePosition) is.readObject();
						System.out.println(p);
					}
				} catch (GameDBException e) {
					throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
				}
				
			}
	    } catch (IOException e) {
	    	e.printStackTrace();
	        System.out.println("Error. No se pudo deserializar el objeto. " + e.getMessage());
	    } catch (ClassNotFoundException e) {
	        System.out.println("Error. No se pudo encontrar la clase asociada. " + e.getMessage());
	    }
	}
	
	public static void gameDBTableQuery() throws GameDBException  {
		for (int i=1; i<5; i++) {
			ResultSet rs;
			try {
				rs = GameDB.getPositions(i);
				cod_mundo = rs.getInt("COD_MUNDO");
				session = rs.getInt("SESION");
				player_x = rs.getInt("PLAYER_X");
				player_y = rs.getInt("PLAYER_Y");
				new Thread ( () ->{
					SerializablePosition prueba = new SerializablePosition(cod_mundo, session, player_x, player_y);
					saveIntoBinaryFile(prueba);
				}).start();
				Thread.currentThread().join();
			} catch (SQLException e) {
				throw new GameDBException("Ha ocurrido un error al ejecutar una sentencia de la base de datos. ", e);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
		}
	}	
}


//private static void uploadPositionBinaryFile() {
//	try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("datos.bin"))) {
//		for (int i=1; i<5; i++) {
//			try {
//				if (GameDB.existsUserPosition(i)) {
//					Position p = (Position) is.readObject();
//					System.out.println(p);
//				}
//			} catch (GameDBException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//    } catch (IOException e) {
//    	e.printStackTrace();
//        System.out.println("Error. No se pudo deserializar el objeto. " + e.getMessage());
//    } catch (ClassNotFoundException e) {
//        System.out.println("Error. No se pudo encontrar la clase asociada. " + e.getMessage());
//    }
//}



//
//public void processTablePOSICIONES() throws GameDBException {
//	int cod_mundo;
//	int session;
//	int player_x;
//	int player_y;
//	for (int i=1; i<5; i++) {
//		if (GameDB.existsUserPosition(i)) {
//			cod_mundo = i;
//			session = GameDB.getNumberSessions(i);
//			player_x = GameDB.getGamePlayerXPosition(i);
//			player_y = GameDB.getGamePlayerYPosition(i);
//			Position prueba = new Position(cod_mundo, session, player_x, player_y);
//			savePositionBinaryFile(prueba);
//			System.out.println(prueba);
//		}
//	}
//	Game.LOGGER.log(Game.LOGGER.getLevel(), "Datos de las posiciones sobreescritos al cerrar la ventana");
//}









//private static void savePositionBinaryFile(Position prueba) {
//try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("datos.bin"))) {
//    os.writeObject(prueba);
//    Game.LOGGER.log(Level.FINEST, "Datos serializados correctamente");
//} catch (IOException e) {
//	Game.LOGGER.log(Level.SEVERE, "Error al serializar los datos");
//}		
//}