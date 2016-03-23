package Controller;
import java.util.ArrayList;

import Model.QueryFactory;
import View.MainFrame;

public class Controller {
	
	MainFrame mainFrame;
	QueryFactory queryFactory;

	public Controller(){
		mainFrame = new MainFrame(this);
		queryFactory = new QueryFactory();
	}
	
	public void getResult(ArrayList<String> upperChoices, ArrayList<String> lowerChoices){
		mainFrame.updateTable(queryFactory.query(upperChoices, lowerChoices));
	}
}
