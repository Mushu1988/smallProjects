package ca.marina.finalexam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CustomerDbUtil {
	
	private static CustomerDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/customer_db";

	public static CustomerDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new CustomerDbUtil();
		}

		return instance;
	}

	private CustomerDbUtil() throws Exception {
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}

	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();

		return theConn;
	}

	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}

	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public List<Customer> getCustomers() throws Exception {

		List<Customer> customers = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from customer order by family";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				String name = myRs.getString("name");
				String family = myRs.getString("family");
				String phone = myRs.getString("phone");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String userName = myRs.getString("userName");
				String password = myRs.getString("password");
				String carType = myRs.getString("carType");
				String brand = myRs.getString("brand");
				String startYear = myRs.getString("startYear");
				String startMonth = myRs.getString("startMonth");
				String startDay = myRs.getString("startDay");
				String numberOfDays = myRs.getString("numberOfDays");
				int rate = myRs.getInt("rate");
				int total = myRs.getInt("total");

				// create new customer object
				Customer tempCustomer = new Customer(name, family, phone, address, email, userName, password, carType, brand, startYear, startMonth, startDay, numberOfDays, rate, total);

				// add it to the list of customers
				customers.add(tempCustomer);
			}

			return customers;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public Customer getCustomer(String familyName) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select name, family, phone, address, email, userName, password, carType, brand, startYear, startMonth, startDay, numberOfDays, rate, total from customer where family=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, familyName);

			myRs = myStmt.executeQuery();

			Customer theCustomer = null;

			// retrieve data from result set row
			if (myRs.next()) {
				String name = myRs.getString("name");
				String family = myRs.getString("family");
				String phone = myRs.getString("phone");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String userName = myRs.getString("userName");
				String password = myRs.getString("password");
				String carType = myRs.getString("carType");
				String brand = myRs.getString("brand");
				String startYear = myRs.getString("startYear");
				String startMonth = myRs.getString("startMonth");
				String startDay = myRs.getString("startDay");
				String numberOfDays = myRs.getString("numberOfDays");
				int rate = myRs.getInt("rate");
				int total = myRs.getInt("total");

				theCustomer = new Customer(name, family, phone, address, email, userName, password, carType, brand, startYear, startMonth, startDay, numberOfDays, rate, total);
			} else {
				throw new Exception("Could not find customer" + familyName);
			}

			return theCustomer;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void addCustomer(Customer theCustomer) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into customer (name, family, phone, address, email, userName, password, carType, brand, startYear, startMonth, startDay, numberOfDays, rate, total) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theCustomer.getName());
			myStmt.setString(2, theCustomer.getFamily());
			myStmt.setString(3, theCustomer.getPhone());
			myStmt.setString(4, theCustomer.getAddress());
			myStmt.setString(5, theCustomer.getEmail());
			myStmt.setString(6, theCustomer.getUserName());
			myStmt.setString(7, theCustomer.getPassword());
			myStmt.setString(8, theCustomer.getCarType());
			myStmt.setString(9, theCustomer.getBrand());
			myStmt.setString(10, theCustomer.getStartYear());
			myStmt.setString(11, theCustomer.getStartMonth());
			myStmt.setString(12, theCustomer.getStartDay());
			myStmt.setString(13, theCustomer.getNumberOfDays());
			myStmt.setInt(14, theCustomer.getRate());
			myStmt.setInt(15, theCustomer.getTotal());

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}

	}

	public void updateCustomer(Customer theCustomer) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update customer " + " set name=?, phone=?, address=?, email=?, userName=?, password=?, carType=?, brand=?, startYear=?, startMonth=?, startDay=?, numberOfDays=?, rate=?, total=?" + " where family=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theCustomer.getName());
			myStmt.setString(2, theCustomer.getPhone());
			myStmt.setString(3, theCustomer.getAddress());
			myStmt.setString(4, theCustomer.getEmail());
			myStmt.setString(5, theCustomer.getUserName());
			myStmt.setString(6, theCustomer.getPassword());
			myStmt.setString(7, theCustomer.getCarType());
			myStmt.setString(8, theCustomer.getBrand());
			myStmt.setString(9, theCustomer.getStartYear());
			myStmt.setString(10, theCustomer.getStartMonth());
			myStmt.setString(11, theCustomer.getStartDay());
			myStmt.setString(12, theCustomer.getNumberOfDays());
			myStmt.setInt(13, theCustomer.getRate());
			myStmt.setInt(14, theCustomer.getTotal());
			myStmt.setString(15, theCustomer.getFamily());

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}

	}

	public void deleteCustomer(String family) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from customer where family=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, family);

			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}
	}

	// Search method ----------------------------------------------------------------
	public List<Customer> searchCustomers(String theSearchName) throws Exception {

		// 1- Result list
		List<Customer> customers = new ArrayList<>();

		// 2- Clean attributes
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			// 3- Get connection to database
			myConn = dataSource.getConnection();

			// 4- Only search by name if theSearchName is not empty
			if (theSearchName != null && theSearchName.trim().length() > 0) {

				// 5- Create sql to search for students by name
				String sql = "select * from customer where lower(family) like ? or lower(carType) like ? or lower(numberOfDays) like ?";

				// 6- Create prepared statement
				myStmt = myConn.prepareStatement(sql);

				// 7- Set params
				String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
				myStmt.setString(1, theSearchNameLike);
				myStmt.setString(2, theSearchNameLike);
				myStmt.setString(3, theSearchNameLike);

			} else {
				// 8- Create sql to get all customers
				String sql = "select * from customer order by family";

				// 9- Create prepared statement
				myStmt = myConn.prepareStatement(sql);
			}

			// 10- Execute statement
			myRs = myStmt.executeQuery();

			// 11- Retrieve data from result set row
			while (myRs.next()) {

				// 12- Retrieve data from result set row
				String name = myRs.getString("name");
				String family = myRs.getString("family");
				String phone = myRs.getString("phone");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String userName = myRs.getString("userName");
				String password = myRs.getString("password");
				String carType = myRs.getString("carType");
				String brand = myRs.getString("brand");
				String startYear = myRs.getString("startYear");
				String startMonth = myRs.getString("startMonth");
				String startDay = myRs.getString("startDay");
				String numberOfDays = myRs.getString("numberOfDays");
				int rate = myRs.getInt("rate");
				int total = myRs.getInt("total");

				// 13- Create new customer object
				Customer tempCustomer = new Customer(name, family, phone, address, email, userName, password, carType, brand, startYear, startMonth, startDay, numberOfDays, rate, total);

				// 14- Add it to the list of customers
				customers.add(tempCustomer);
			}

			return customers;
		} finally {

			// 15- Clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	// ------------------------------------------------------------------------------

}


