package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class ComboBoxConstants {
	
	private static int HAVING = 0;
	private static int FILTER = 1;

	public static ArrayList<Column> OPTIONS_QUERY = new ArrayList<Column>(Arrays.asList(
			new Column("croptype", "Type of Crop", FILTER),
			new Column("flood_freq", "Flood Frequency", FILTER),
			new Column("drought", "Drought Frequency", FILTER),
			new Column("u_chng_pcrop_y", "Reason of Crop Change", FILTER),
			new Column("alp_area", "Farm Area", FILTER),
			new Column("u_low_harv", "Reason of Low Harvest", FILTER),
			new Column("u_low_harv_o_lb", "Other Low Harvest Reason", FILTER),
			new Column("SUM(crop_vol)", "Crop Volume", FILTER)));
		
	public static Column findColumn(String name){
		ArrayList<Column> cols = OPTIONS_QUERY;
		for (Column c: cols){
			if (name.equals(c.getColName()) || name.equals(c.getName())){
				return c;
			}
		}
		return null;
	}
}