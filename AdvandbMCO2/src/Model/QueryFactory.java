package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryFactory {
	
	public ResultSet query(String query) {
		Connection conn = (Connection) DBConnection.getConnection();
		PreparedStatement stmt;
		ResultSet rs = null;
		try {
			stmt = (PreparedStatement) conn.prepareStatement(query);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getQuery(ArrayList<String> upperChoices, ArrayList<String> lowerChoices){
		String select = "SELECT hpq_hh_id ";
		String from = 	"FROM crop C, land L, harvest H, primecrop P \n";
		String where = 	"WHERE C.land_id = L.land_id \n" + 
							"AND C.harvest_id = H.harvest_id \n" + 
							"AND C.primecrop_id = P.primecrop_id \n";
		String groupby = "";
		String having = "";
		String orderby = "";
		String query = "";
		try{
			if (!(upperChoices.isEmpty() && lowerChoices.isEmpty())){
				groupby = "GROUP BY ";
				orderby = "ORDER BY ";
				for (int i = 0; i < upperChoices.size(); i++){
					String name = upperChoices.get(i);
					select += ", " + ComboBoxConstants.findColumn(name).getColName() + " ";
					if (i != upperChoices.size()-1)
						groupby += i+1 + ", ";
					orderby += i+1 + ", ";
				}
				if (groupby.contains("1")){
					groupby = groupby.substring(0, groupby.lastIndexOf(",")) + " \n";
				}
				else {
					groupby = "";
				}
				orderby = orderby.substring(0, orderby.lastIndexOf(",")) + " \n";
			}
			
			query = select + "\n" + from + where + groupby + having + orderby;
			System.out.println(query);
			return this.query(query);
		}
		catch (NullPointerException e){
			query = select + "\n" + from + where + groupby + having + orderby;
			System.out.println(query);
			return this.query(query);
		}
	}

}
