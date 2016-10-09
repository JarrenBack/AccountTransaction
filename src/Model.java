import java.sql.*;
import java.io.*;

public class Model {
	private Connection con;
	private Statement stmt;
	
	public Model () {
		connectToDB();
	}
	
	private void connectToDB() {
        String url = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/jwbpp5";
        String userID = "jwbpp5";
        String password = "ABHtdq4kbT";
   
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch(java.lang.ClassNotFoundException e) {
            System.out.println(e);
            System.exit(0);
        }
        try {
			con = DriverManager.getConnection(url,userID,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean transfer (int accountFrom, int accountTo, int amount) {
		int amountInFrom, amountInTo;
		try {
			amountInFrom = getCurrentAmountInAccount (accountFrom);
			amountInTo = getCurrentAmountInAccount (accountTo);	
		} catch (SQLException e) {
			System.out.println("Sql exception caught in model.transfer");
			return false;
		}
		if (amountInFrom >= amount) {
			updateAmount (accountFrom, amountInFrom - amount);
			updateAmount (accountTo, amountInTo + amount);
			return true;
		}
		return false;
		
	}
	
	private int getCurrentAmountInAccount (int accountID) throws SQLException {
		String sqlAmountInAccount = "SELECT account_balance FROM account WHERE account_id = " + Integer.toString(accountID) + ";";
		ResultSet result = stmt.executeQuery(sqlAmountInAccount);
		result.next();
		return result.getInt(1);
	}
	
	private void updateAmount(int accountID, int ammount) {
		String sqlUpdateAmount = "UPDATE account " + 
								 "SET account_balance = " + Integer.toString(ammount) +
								 " WHERE account_id = " + Integer.toString(accountID) + ";";
		System.out.println(sqlUpdateAmount);
		try {
			stmt.executeUpdate(sqlUpdateAmount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getCurrentState() {
		String sqlSelectAll = "SELECT * FROM account;";
		ResultSet result = null;
		try {
			result = stmt.executeQuery(sqlSelectAll);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
