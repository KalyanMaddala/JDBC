package com.nt.jdbc;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertTestWithAutoPidForPostgreSQL {
   private static final String INSERT_QUERY="INSERT INTO PRODUCT VALUES(NEXTVAL('PID_SEQ'),?,?,?)";
	public static void main(String[] args) {
		Scanner  sc=null;
		String name=null;
		float price=0.0f,qty=0.0f;
		Connection con=null;
		PreparedStatement ps=null;
		String query=null;
		int count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			 if(sc!=null) {
				 System.out.println("Enter  product name ::");
				 name=sc.next();  //gives table
				 System.out.println("Enter Product price :");
				 price=sc.nextFloat(); // gives 8999.55
				 System.out.println("Enter Product qty::");
				 qty=sc.nextFloat(); // gives  12
			 }
			   //register JDBC driver s/w
			   Class.forName("org.postgresql.Driver");
			   //establish the connection
			   con=DriverManager.getConnection("jdbc:postgresql:ntaj412DB","postgres","root");
			   //create JdbcStatement object
			   if(con!=null)
				   ps=con.prepareStatement(INSERT_QUERY);
			   //set query param values
			   if(ps!=null) {
				   ps.setString(1,name);
				   ps.setFloat(2,price);
				   ps.setFloat(3,qty );
			   }
			   //send and execute SQL Query in DB s/w
			   if(ps!=null)
				   count=ps.executeUpdate();
			   //process the result
			   if(count==0)
				   System.out.println("record not inserted");
			   else
				   System.out.println("record inserted");
		}//try
		catch(SQLException se) {
			System.out.println("record not inserted");
			
			if(se.getErrorCode()==1)
				System.out.println("duplicates can not be inserted to sno column");
			else if(se.getErrorCode()==12899)
				System.out.println("values are larger than col size");
			else if(se.getErrorCode()>=900 && se.getErrorCode()<=1000)
				System.out.println("SQL Query  Syntax problem");
			else
				se.printStackTrace();
				
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objs
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
	}//main
}//class
