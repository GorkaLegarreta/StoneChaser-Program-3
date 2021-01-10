package main.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import main.GameDB;

public class UserNameListModel extends DefaultListModel<String>{
	
	private static final long serialVersionUID = 1L;

	public UserNameListModel() {
		
	}
	public List<String> getUserNames() {
		List<String> userNameListModel = new ArrayList<String>();
		for (int i=0; i<4; i++) {
			userNameListModel.add(GameDB.getGameUserName(i+1));
		}
		return userNameListModel;
	}
}
