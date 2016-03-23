package Controller;
import java.sql.ResultSet;
import java.util.ArrayList;

import View.MainFrame;

public class Controller {

	public Controller(){
		new MainFrame(this);
	}
	
	public ResultSet getResult(ArrayList<String> upperChoices, ArrayList<String> lowerChoices){
		for (String text: upperChoices){
			System.out.println(text);
		}
		for (String text: lowerChoices){
			System.out.println(text);
		}
		return null;
	}
}
