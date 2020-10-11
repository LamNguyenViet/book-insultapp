package org.openshift;

import java.sql.*;
import java.util.Random;

public class InsultGenerator {
	public String generateInsult() {
		String words[][] = {{"Artless", "Bawdy", "Beslubbering"}, {"Base-court", "Bat-fowling", "Beef-witted"}, {"Apple-john", "Baggage", "Barnacle"}};
		String vowels = "AEIOU";
		String article = "an";
		String theInsult = "";
		try {
			String databaseURL = "jdbc:postgresql://172.30.156.227/insults";
			String username = "insult";
			String password = "insult";
			Connection connection = DriverManager.getConnection(databaseURL, username, password);
			if(connection != null){
				String sql = "select a.string AS first, b.string AS second, c.string AS noun from short_adjective a , long_adjective b, noun c ORDER BY random() limit 1";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()){
					if (vowels.indexOf(rs.getString("first").charAt(0)) == -1){
						article = "a";
					}
					theInsult = String.format("Thou art %s %s %s %s!", article,
							rs.getString("first"), rs.getString("second"),
							rs.getString("noun"));
				}
				rs.close();
				connection.close();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return "Database connection problem";
		}
		return theInsult;
	}

}
