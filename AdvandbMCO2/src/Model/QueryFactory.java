package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryFactory {
	
	public ResultSet query(ArrayList<String> upperChoices, ArrayList<String> lowerChoices) {
		Connection conn = (Connection) DBConnection.getConnection();
		PreparedStatement stmt;
		ResultSet rs = null;
		String query = this.getQuery(upperChoices, lowerChoices);
		try {
			stmt = (PreparedStatement) conn.prepareStatement(query);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public String getQuery(ArrayList<String> upperChoices, ArrayList<String> lowerChoices){
		try{
			if (upperChoices.isEmpty() && lowerChoices.isEmpty())
				return "SELECT * FROM mc02.land";
			else
				return "SELECT * FROM mc02.harvest";

		}
		catch (NullPointerException e){
			return "SELECT * FROM mc02.land";
		}
	}

}
