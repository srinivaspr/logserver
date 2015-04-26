package com.logger.server;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
import com.ltrf.LogMessage;
 
public class DBMessageLogWriter {
	 // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/logs";
 
	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "root";
	  
	   public  void writeLogMessage(LogMessage message) {
	   Connection conn = null;
	   Statement stmt = null;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");
 
	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
 
	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      
	      sql = "INSERT INTO LOG_MESSAGE(APPLICATION_NAME, KEY_STRING, MESSAGE, ADDITIONAL_INFO) VALUES('"+message.getAppName()+"', '"+message.getKey()+"', '"+message.getMessage()+"', '"+message.getAdditionalDetails()+"')";
	     
          int rs1=stmt.executeUpdate(sql);
	     
	     
	      
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
		   System.out.println("DBMessageLogWriter.writeLogMessage(): "+se.getMessage());
	      //Handle errors for JDBC
	     // se.printStackTrace();
	   }catch(Exception e){
		   System.out.println("DBMessageLogWriter.writeLogMessage(): "+e.getMessage());
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         //se.printStackTrace();
	      }//end finally try
	   }//end try
	}//end main
 
	   
	   
	   public  List<LogMessage> readLogMessage() {
		   Connection conn = null;
		   Statement stmt = null;
		   List<LogMessage> logMessageList = new ArrayList<LogMessage>();
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
 
		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
 
		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT APPLICATION_NAME, KEY_STRING, MESSAGE, ADDITIONAL_INFO FROM LOG_MESSAGE ORDER BY LOG_MESSAGE_ID LIMIT 20";
		      //sql1 = "INSERT INTO LOG_MESSAGE(APPLICATION_NAME, KEY_STRING, MESSAGE, ADDITIONAL_INFO) VALUES("+applicationName+", "+keyString+", "+message+", "+additionalInfo+")";
		      ResultSet rs = stmt.executeQuery(sql);
	          //ResultSet rs1=stmt.executeQuery(sql1);
		      //STEP 5: Extract data from result set
		     
		      while(rs.next()){
		         //Retrieve by column name
		       
		         String appName = rs.getString("APPLICATION_NAME");
		         String keyStr = rs.getString("KEY_STRING");
		         String msg = rs.getString("MESSAGE");
		         String addInfo = rs.getString("ADDITIONAL_INFO");
                System.out.println("DBMessageLogWriter.readLogMessage(): "+appName);
                System.out.println("DBMessageLogWriter.readLogMessage(): "+keyStr);
                System.out.println("DBMessageLogWriter.readLogMessage(): "+msg);
                System.out.println("DBMessageLogWriter.readLogMessage(): "+addInfo);
                
		         LogMessage logMessage= new LogMessage();
		         logMessage.setAppName(appName);
		         logMessage.setKey(keyStr);
		         logMessage.setMessage(msg);
		         logMessage.setAdditionalDetails(addInfo);
		         logMessageList.add(logMessage);
		         //Display values
		        
		      }
		      
		      //STEP 6: Clean-up environment
		      rs.close();
		      //rs1.close();
		      stmt.close();
		      conn.close();
		      
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   return logMessageList;
		}
}