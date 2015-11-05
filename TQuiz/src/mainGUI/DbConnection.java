package mainGUI;

/**
 * @author Mohit Uniyal
 * in this code user is getting connected to the database, for first time database is getting created 
 * Date - 2-8-2015, 6:45pm
 * (imp) : port no still missing with the address, needs to be concatenated at the time of final integration
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import dataEntry.Question;

public class DbConnection {
	public static Connection con;

	public void connectToDb() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// this method will only create the database if it doesn't exist
		try (Connection con = DriverManager
				.getConnection(AdminConnectDatabase.dbType
						+ AdminConnectDatabase.serverAddress + ":"
						+ AdminConnectDatabase.portNo + "/?user="
						+ AdminConnectDatabase.userName + "&password="
						+ AdminConnectDatabase.dbPassword);
				Statement st = con.createStatement();) {

			st.executeUpdate("CREATE DATABASE if not exists techquiz");

			// System.out.println("dbtype 2 : "+AdminConnectDatabase.dbType);

			// new instance for future use
			DbConnection.con = DriverManager.getConnection(
					AdminConnectDatabase.dbType
							+ AdminConnectDatabase.serverAddress + ":"
							+ AdminConnectDatabase.portNo + "/techquiz",
					AdminConnectDatabase.userName,
					AdminConnectDatabase.dbPassword);

			try (Statement querySt = DbConnection.con.createStatement()) {
				if ("jdbc:mysql://".equals(AdminConnectDatabase.dbType)) {
					querySt.executeUpdate("create Table TBLPARTICIPENTS (username varchar(200) primary key, password varchar(200), result integer(5))");
					querySt.executeUpdate("create TABLE TBLQUESTION (question_id varchar(30) primary key,question varchar(2000), description varchar(4000), option1 varchar(200), option2 varchar(200), option3 varchar(200), option4 varchar(200), answer varchar(200))");
				} else {
					querySt.executeUpdate("create Table TBLPARTICIPENTS (username varchar2(200) primary key, password varchar2(200), result number(5,0)");
					querySt.executeUpdate("create TABLE TBLQUESTION (question_id varchar2(30) primary key,question varchar2(2000), description varchar2(4000), option1 varchar2(200), option2 varchar2(200), option3 varchar2(200), option4 varchar2(200), answer char(4))");
				}
			} catch (Exception e) {
				System.out.println("Table creation : " + e.getMessage());
			}
			System.out.println("dbcon : " + DbConnection.con);

		} catch (CommunicationsException e) {
			HandleException.displayPrompt(HandleException.WRONG_PATH);
		} catch (MySQLSyntaxErrorException e) {
			HandleException.displayPrompt(HandleException.WRONG_UNAME);
		} catch (SQLException e) {
			String str = "" + e;
			int pos = str.indexOf(":");
			// checking if password is incorrect or driver path is invalid
			if (str.substring(pos + 2, pos + 15).equalsIgnoreCase(
					"Access denied"))
				HandleException.displayPrompt(HandleException.WRONG_PASSWD);
			else if (str.substring(pos + 2, pos + 13).equalsIgnoreCase(
					"No suitable"))
				HandleException.displayPrompt(HandleException.WRONG_PATH);

			System.out.println("DbConnection inner : " + e);
		} catch (Exception e) {
			System.out.println("DbConnection outer : " + e);
		}
	}

	static int quesCtr = 0; 

	//reads the xml files and stores the question and other details to the database
	
	public static void addToQuestionTbl(ArrayList<Question> ques, String subName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("addToQuestionTbl 1 : " + e.getMessage());
		}

		String query = "insert into tblquestion values(?,?,?,?,?,?,?,?)";
		System.out.println(DbConnection.con);
		try (
				PreparedStatement pst = DbConnection.con.prepareStatement(query);) {
			
			
			//updating question table with values
			Question q; String options[];
			for(int i=0; i<ques.size(); i++){
				q = ques.get(i);
				pst.setString(1, subName+quesCtr++);
				pst.setString(2, q.getQuestion());
				pst.setString(3, q.getDescription());
				options = q.getOptions();
				pst.setString(4, options[0]);
				pst.setString(5, options[1]);
				pst.setString(6, options[2]);
				pst.setString(7, options[3]);
				pst.setString(8, q.getAns());
	
				pst.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println("addToQuestionTbl  2: " +e); 
		e.printStackTrace();
		}
	}
}
