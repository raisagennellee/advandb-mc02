package Controller;
import java.util.ArrayList;

import Model.QueryFactory;
import View.MainFrame;

public class Controller {
	
	MainFrame mainFrame;
	QueryFactory queryFactory;

	public Controller(){
		queryFactory = new QueryFactory();
		mainFrame = new MainFrame(this, queryFactory.getQuery(null, null));
	}
	
	public void getResult(ArrayList<String> upperChoices, ArrayList<String> lowerChoices){
		mainFrame.updateTable(queryFactory.getQuery(upperChoices, lowerChoices));
	}
}
