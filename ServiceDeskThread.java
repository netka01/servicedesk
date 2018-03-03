package com.netegrity.ims.bootstrap;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.netegrity.ims.businessprocess.InternalServiceAccessor;
import com.netegrity.ims.persistence.PersistenceProvider;
import com.netegrity.ims.persistence.PersistenceProviderFactory;
import com.netegrity.ims.task.TaskSession;
import com.netegrity.llsdk6.imsapi.ImsEnvironment;

public class ServiceDeskThread implements Runnable {
   
      
    private ImsEnvironment ime = null;
    
    private static final Logger logger = Logger.getLogger("ims.bootstrap.NotificationReader");
    

    public ServiceDeskThread(ImsEnvironment env) {
        
       
        ime = env;
       
        
    }
    
    
    @Override
    public   void run() {
        
		
		while (true) {
			
	        try {
	        	
	        	if (InternalServiceAccessor.getEnvironmentService().getEnvironmentByAlias(ime.getAlias()) != null) {  
	        		String dbURL = "jdbc:sqlserver://netka01im2k12:1433;selectMethod=cursor;DatabaseName=objstore";
		    		String username = "sa";
		    		String password = "firewall";
		    		
		    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
					Connection conn = DriverManager.getConnection(dbURL, username, password);
					
		    		if (conn != null) {
		    			PreparedStatement ps = conn.prepareStatement("Select tasksessionid from OFFLINE_ENDPOINT_TASK where IS_ENDPOINT_OFFLINE='false'");
		    			
		    			// call executeUpdate to execute our sql update statement
					    ResultSet rs = ps.executeQuery();
					    String tasksessionid = "";
					    
					    TaskSession taskSession;
					    while(rs.next()) {
					    	tasksessionid = rs.getString("tasksessionid");
					    	
					    	PersistenceProvider pp = PersistenceProviderFactory.getPersistenceProvider(ime.getUniqueName());
					    	taskSession = pp.getTaskSession(tasksessionid);
					    	taskSession.retryInprogressTasks();		    	
					    	
					    } else {
					    	Thread.sleep(5000);
					    }
					    ps.close();
					}
	        	}       	
						    
			    
				
	            
	        } catch (Exception e) {
	        	logger.error("ServiceDeskThread: Exception " + e.getMessage());
	           } finally {
	        	 
	             }
	        
	    }   
    }
    
}

