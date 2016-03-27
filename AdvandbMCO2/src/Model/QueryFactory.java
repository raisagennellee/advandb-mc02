package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

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
		String select = "SELECT household_id";
		String from = 	"FROM crop C, land L, harvest H, primecrop P \n";
		String where = 	"WHERE C.land_id = L.land_id \n" + 
							"AND C.harvest_id = H.harvest_id \n" + 
							"AND C.primecrop_id = P.primecrop_id \n";
		String groupby = "";
		String having = "";
		String orderby = "";
		String query = "";
		try{
			if (!(upperChoices.isEmpty())){
				groupby = "GROUP BY ";
				orderby = "ORDER BY ";
				for (int i = 0; i < upperChoices.size(); i++){
					String name = upperChoices.get(i);
					select += ", " + ComboBoxConstants.findColumn(name).getColName() + " ";
					//if (i != upperChoices.size()-1)
					groupby += i+1 + ", ";
					orderby += i+1 + ", ";
				}
				if (groupby.contains("1")){
					groupby = groupby.substring(0, groupby.lastIndexOf(",")) + " \n";
				}
				else {
					groupby = "";
				}
				orderby += upperChoices.size() + 1 + ", ";
				orderby = orderby.substring(0, orderby.lastIndexOf(",")) + " \n";
			}
			if (!(lowerChoices.isEmpty())){
				Collections.sort(lowerChoices, new SecondWordComparator());
				boolean isSame = false, editedWhere = false, editedHaving = false;
				String addWhere = "";
				for (int i = 0; i < lowerChoices.size(); i++){
					String condition1 = lowerChoices.get(i);
					String[] temp1 = condition1.split(" ");
					if (condition1.contains("SUM")){
						if (!editedHaving){
							having = "HAVING (" + condition1.substring(4, condition1.length()) + " \n";
						}
						else{
							having += condition1 + " \n";
						}
						editedHaving = true;
					}
					else{
						if (!editedWhere){
							where += "AND (" + condition1.substring(4, condition1.length()) + " \n";
						}
						if (i!=0){
							if (isSame ){
								where += condition1 + " \n";
							}
							else{
								where += ") \n" + temp1[0] + "(" + condition1.substring(temp1[0].length(), condition1.length()) + " \n";
							}
						}
						editedWhere = true;
					}
					if (i == lowerChoices.size()-1){
						break;
					}
					String condition2 = lowerChoices.get(i+1);
					String[] temp2 = condition2.split(" ");
					isSame = temp1[1].equals(temp2[1]);
				}
				if (editedHaving)
					having += ") \n";
				if (editedWhere)
					where += ") \n";
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
