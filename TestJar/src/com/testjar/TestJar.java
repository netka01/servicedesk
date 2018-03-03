package com.testjar;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJar {
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
		
		
			if(args.length > 1)
			{
				String incidentId = args[0];
				String status = args[1];
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
				Connection conn = DriverManager.getConnection(dbURL, username, password);
				
				if (conn != null) {
					System.out.println("Connected");
				}
				
			    
			    PreparedStatement ps = conn.prepareStatement("Select ObjectName, ObjectType from Service_Desk_Tickets WHERE ServiceDeskTicketId = ?");
				
			    // set the preparedstatement parameters
			    ps.setString(1,incidentId);			    
			   
			    // call executeUpdate to execute our sql update statement
			    ResultSet rs = ps.executeQuery();
			    String endpointName = "";
			    String endpointType = "";
			    
			    while(rs.next()) {
			    	endpointName = rs.getString("ObjectName");
			    	endpointType = rs.getString("ObjectType");
			    	System.out.println("endpointname: " + endpointName + " endpointtype: " + endpointType);
			    	
			    }
			    ps.close();
			    
			    ps = conn.prepareStatement("UPDATE Service_Desk_Tickets SET ServiceDeskTicketStatus = ? WHERE ServiceDeskTicketId = ?");
				
			    // set the preparedstatement parameters
			    ps.setString(1,status);
			    ps.setString(2,incidentId);
			   
			    // call executeUpdate to execute our sql update statement
			    ps.executeUpdate();
			    ps.close();
			    
			    ps = conn.prepareStatement("UPDATE OFFLINE_ENDPOINT SET IS_OFFLINE = 'false' WHERE ENDPOINT_NAME = ? and ENDPOINT_TYPE = ?");
				
			    // set the preparedstatement parameters
			    ps.setString(1,endpointName);
			    ps.setString(2,endpointType);
			   
			    // call executeUpdate to execute our sql update statement
			    int i = ps.executeUpdate();
			    System.out.println("updated records: " + i);
			    ps.close();
			    
			    ps = conn.prepareStatement("UPDATE OFFLINE_ENDPOINT_TASK SET IS_ENDPOINT_OFFLINE = 'false' WHERE ENDPOINT_NAME = ? and ENDPOINT_TYPE = ?");
				
			    // set the preparedstatement parameters
			    ps.setString(1,endpointName);
			    ps.setString(2,endpointType);
			   
			    // call executeUpdate to execute our sql update statement
			    int j = ps.executeUpdate();
			    System.out.println("updated records: " + j);
			    ps.close();
					    
							
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
