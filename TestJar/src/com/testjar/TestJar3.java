package com.testjar;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJar3 {
	static String fileName = "C://UpdatedIncidetns.txt";

	public static void main(String[] args) throws IOException {
		/*try (FileWriter file = new FileWriter(fileName, true)) 
		{
			//file.append("==========Updated Incidents From ServiceNow========\n\n\n");
			System.out.println("Iam Printing from TestJar.......");
			if(args.length > 1)
			{
				String incidentId = args[0];
				String status = args[1];
				updateToFile(incidentId, status);				
			}			
		}
		catch (Exception ex) {
			try (FileWriter file = new FileWriter(fileName, true)) {
				file.append("\n\n\n***Exception start***\n");
				file.append(ex.getMessage());
				file.append("\n***Exception end***\n\n\n");
			}
		}*/
		
		String dbURL = "jdbc:sqlserver://netka01inbound:1433;selectMethod=cursor;DatabaseName=objstore";
		String username = "sa";
		String password = "firewall";
		
		try {
		
		
			
				String incidentId = "xxxx";
				
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
				Connection conn = DriverManager.getConnection(dbURL, username, password);
				
				if (conn != null) {
					System.out.println("Connected");
				}
				
			    
			    PreparedStatement ps = conn.prepareStatement("update notify_lock set lockid='x' WHERE lockid = ?");
				
			    // set the preparedstatement parameters
			    ps.setString(1,incidentId);			    
			   
			    // call executeUpdate to execute our sql update statement
			    int rs = ps.executeUpdate();
			    System.out.println("Test3 after result set");
					    
							
			
		}
			
		 catch (Exception ex) {
				ex.printStackTrace();
			}
		
		
	
	}
	
	public static void updateToFile(String incidentId, String staus) throws IOException
	{
		try (FileWriter file = new FileWriter(fileName, true)) 
		{
			file.append("Updated Incident Id : "+incidentId +"         Updated Status : "+staus+"\n\n\n");
			System.out.println("Updated Incident Id : "+incidentId +"         Updated Status : "+staus+"\n\n\n");
		}
		
	}
	

}
