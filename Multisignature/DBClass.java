import java.sql.*;
import java.math.BigInteger;

public class DBClass {
	Connection con;
	Statement st;
	ResultSet rs;

	DBClass() {
		try {
			// Load MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establish a connection to the MySQL database
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signature", "root", ""); // Update with correct credentials
			st = con.createStatement();
		} catch (Exception e) {
			System.out.println("Database Connectivity Error " + e);
		}
	}

	public int getResult(String id, String pass, String gid) throws Exception {
		rs = st.executeQuery("SELECT * FROM UsersInfo WHERE UserId = '" + id + "' AND Password = '" + pass + "' AND GrpId = '" + gid + "'");
		if (rs.next()) {
			System.out.println("The Values are Found");
			return 1;
		} else {
			return 0;
		}
	}

	public int sgetResult(String id, String gid) throws Exception {
		rs = st.executeQuery("SELECT * FROM UsersInfo WHERE UserId = '" + id + "' AND GrpId = '" + gid + "'");
		if (rs.next()) {
			System.out.println("The Values are Found");
			return 1;
		} else {
			return 0;
		}
	}

	public int getUsersCount(String gid) throws Exception {
		int c = 0;
		rs = st.executeQuery("SELECT UserId FROM UsersInfo WHERE GrpId LIKE '" + gid + "%'");
		while (rs.next()) {
			c++;
		}
		return c;
	}

	public String[] getUsers(String gid, int n) throws Exception {
		int i = 0;
		String users[] = new String[n];
		rs = st.executeQuery("SELECT UserId FROM UsersInfo WHERE GrpId LIKE '" + gid + "%'");
		while (rs.next()) {
			users[i] = rs.getString("UserId");
			i++;
		}
		return users;
	}

	public int getResultInfo(String id, String pass, String gid) throws Exception {
		String tableName = "";
		if (gid.equalsIgnoreCase("Group 1")) {
			tableName = "tree1";
		} else {
			tableName = "tree2";
		}
		System.out.println("The Selected Group Table = " + tableName);
		rs = st.executeQuery("SELECT * FROM " + tableName + " WHERE memname = '" + id + "' AND pass = '" + pass + "'");
		if (rs.next()) {
			System.out.println("The Values are Found");
			return 1;
		} else {
			return 0;
		}
	}

	public String getHostAdrs(String gid) throws Exception {
		String adrs = "";
		rs = st.executeQuery("SELECT UserIpAdrs FROM UsersInfo WHERE UserId = '" + gid + "'");
		if (rs.next()) {
			adrs = rs.getString(1);
		}
		return adrs;
	}

	public int addMember(String id, String pass, String gid) throws Exception {
		st.executeUpdate("INSERT INTO UsersInfo (UserId, Password, GrpId) VALUES ('" + id + "', '" + pass + "', '" + gid + "')");
		return 1;
	}

	public int checkMailId(String mailid, String gid) throws Exception {
		int i = 0;
		rs = st.executeQuery("SELECT * FROM UsersInfo WHERE UserId = '" + mailid + "' AND GrpId = '" + gid + "'");
		if (rs.next()) {
			System.out.println("value 1:::" + rs.getString(1));
			System.out.println("value 2:::" + rs.getString(2));
			System.out.println("value3 :::" + rs.getString(3));
			i = 1;
		}
		return i;
	}

	public boolean checkPermit(String id) throws Exception {
		rs = st.executeQuery("SELECT * FROM UsersInfo WHERE MailId = '" + id + "'");
		boolean flag = false;
		if (rs.next()) {
			flag = true;
		}
		return flag;
	}

	public boolean assignKeys(String mailid, String pass, String usradrs, String gid) {
		boolean flag = false;
		try {
			String query = "UPDATE UsersInfo SET UserIpAdrs = '" + usradrs + "' WHERE UserId='" + mailid + "' AND GrpId='" + gid + "'";
			st.executeUpdate(query);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean removeUser(String mailid, String gid) {
		boolean flag = false;
		try {
			String query = "DELETE FROM UsersInfo WHERE UserId='" + mailid + "' AND GrpId='" + gid + "'";
			st.executeUpdate(query);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String getKey(String user, String grpid) {
		System.out.println("Inside the getKey Method");
		String gid = "";
		if (grpid.equalsIgnoreCase("Group 1"))
			gid = "tree1";
		else if (grpid.equalsIgnoreCase("Group 2"))
			gid = "tree2";
		String key = "";
		try {
			rs = st.executeQuery("SELECT prikey FROM " + gid + " WHERE memname = '" + user + "'");
			if (rs.next()) {
				key = rs.getString(1);
				System.out.println("The Key Value = " + key);
			}
			if (key.length() > 8)
				key = key.substring(0, 8);
			else {
				int i = key.length();
				System.out.println("The Key Length = " + i);
				while (i < 8) {
					key = key + i;
					System.out.println("The Key = " + key);
					i++;
				}
			}

		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}
		return key;
	}
}