import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectDatabase {

	public static Statement st1;
	private Connection con1;

	public ConnectDatabase() {
		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establish the database connection
			con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/signature", "root", "");

			// Create the statement object
			st1 = con1.createStatement();

			System.out.println("Database connection established successfully.");
		} catch (Exception e) {
			System.out.println("Exception occurred: " + e);
		}
	}

	// Ensure connection is properly closed
	public void closeConnection() {
		try {
			if (con1 != null && !con1.isClosed()) {
				con1.close();
				System.out.println("Connection closed successfully.");
			}
		} catch (Exception e) {
			System.out.println("Error while closing connection: " + e);
		}
	}
}