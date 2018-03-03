package com.testjar;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJar2 {
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
		
		String dbURL = "jdbc:sqlserver://netka01im2k12:1433;selectMethod=cursor;DatabaseName=objstore";
		String username = "sa";
		String password = "firewall";
		
		try {
		
			Connection conn = null;
			
				
				
				String lock = "update Service_Desk_Tickets set ObjectName='x' where servicedeskticketid='9ec1e4830f3ec780101c48dce1050e37'";
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
					conn = DriverManager.getConnection(dbURL, username, password);
				    conn.setAutoCommit(false);  
				    Statement stmt1=conn.createStatement();  
				    stmt1.execute(lock);  
				    int i = 0;
				    while (true) {            
				        Thread.sleep(5000);    //Sleep 5 seconds
				                        
				    }
				    

				} catch (SQLException e) {
				    conn.rollback();
				    e.printStackTrace();
				}
					    
							
			
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
