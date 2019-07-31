package marina.managedBeans;

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

public class ProductDbUtil {
	
	private DataSource dataSource;
	private String jndName= "java:comp/env/jdbc/shop";
	
	
	private static ProductDbUtil instance;
	
	private ProductDbUtil() throws Exception{
		dataSource = getDataSource();
	}

	public static ProductDbUtil getInstance() throws Exception{
		
		if(instance == null)
			instance =  new ProductDbUtil();
		return instance;
	}
	private DataSource getDataSource() throws NamingException{
		Context context= new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndName);
		return theDataSource;
	}
	
	private Connection getConnection() throws Exception{
		Connection theConn = dataSource.getConnection();
		return theConn;
	}
	private void close(Connection theConn,Statement theStmt) {
		close(theConn,theStmt,null);
	}
	private void close(Connection theConn,Statement theStmt,ResultSet theRs) {
		
		try {
				if(theRs != null)
					theRs.close();
				if(theStmt != null)
					theStmt.close();
				if(theConn !=null)
					theConn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Product> getProducts() throws Exception{
		
		List<Product> products = new ArrayList<>();
		Connection  myConn= null;
		Statement  myStmt= null;
		ResultSet   myRs= null;
		
		try {
			
			myConn = getConnection();
			String sql = "select * from products";
			myStmt = myConn.createStatement();
			myRs =myStmt.executeQuery(sql);
			while(myRs.next())	{
				int product_id = myRs.getInt("product_id");
				String category_name = myRs.getString("category_name");
				String product_name = myRs.getString("product_name");
				String product_descrip = myRs.getString("product_descrip");
				Product tempProduct = new Product(product_id,category_name,product_name,product_descrip);
				products.add(tempProduct);
				
			}
			return products;
		}finally {
			close(myConn,myStmt,myRs);
		}
			
			
	}
	
	public Product getProduct(int productId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from products where product_id=?";

			myStmt = myConn.prepareStatement(sql);

			// Set params
			myStmt.setInt(1, productId);

			myRs = myStmt.executeQuery();

			Product theProduct = null;

			// Retrieve data from result set row
			if (myRs.next()) {
				int product_id = myRs.getInt("product_id");
				String category_name = myRs.getString("category_name");
				String product_name = myRs.getString("product_name");
				String product_descrip = myRs.getString("product_descrip");

				theProduct = new Product(product_id, category_name, product_name, product_descrip);
			} else {
				throw new Exception("Could not find product id: " + productId);
			}

			return theProduct;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
//..............................................................

public void addProduct(Product theProduct) throws Exception {

	Connection myConn = null;
	PreparedStatement myStmt = null;

	try {
		myConn = getConnection();

		String sql = "insert into products (category_name, product_name, product_descrip) values (?, ?, ?)";

		myStmt = myConn.prepareStatement(sql);

		// set params
		myStmt.setString(1, theProduct.getCategory_name());
		myStmt.setString(2, theProduct.getProduct_name());
		myStmt.setString(3, theProduct.getProduct_descrip());

		myStmt.execute();
	} finally {
		close(myConn, myStmt);
	}

}

//..............................................................
public void updateProduct(Product theProduct) throws Exception {

	Connection myConn = null;
	PreparedStatement myStmt = null;

	try {
		myConn = getConnection();

		String sql = "update products " + " set category_name=?, product_name=?, product_descrip=?" + " where product_id=?";

		myStmt = myConn.prepareStatement(sql);

		// set params
		myStmt.setString(1, theProduct.getCategory_name());
		myStmt.setString(2, theProduct.getProduct_name());
		myStmt.setString(3, theProduct.getProduct_descrip());
		myStmt.setInt(4, theProduct.getProduct_id());

		myStmt.execute();
	} finally {
		close(myConn, myStmt);
	}

}

public void deleteProduct(int product_id) throws Exception {

	Connection myConn = null;
	PreparedStatement myStmt = null;

	try {
		myConn = getConnection();

		String sql = "delete from products where product_id=?";

		myStmt = myConn.prepareStatement(sql);

		// set params
		myStmt.setInt(1, product_id);

		myStmt.execute();
	} finally {
		close(myConn, myStmt);
	}
}

// Search method ----------------------------------------------------------------
public List<Product> searchProducts(String theSearchName) throws Exception {

	// 1- Result list
	List<Product> products = new ArrayList<>();

	// 2- Clean attributes
	Connection myConn = null;
	PreparedStatement myStmt = null;
	ResultSet myRs = null;
//	int product_id;

	try {

		// 3- Get connection to database
		myConn = dataSource.getConnection();

		// 4- Only search by name if theSearchName is not empty
		if (theSearchName != null && theSearchName.trim().length() > 0) {

			// 5- Create sql to search for products by name
			String sql = "select * from products where lower(product_name) like ? or lower(category_name) like ?";

			// 6- Create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// 7- Set params
			String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
			myStmt.setString(1, theSearchNameLike);
			myStmt.setString(2, theSearchNameLike);

		} else {
			// 8- Create sql to get all students
			String sql = "select * from products";

			// 9- Create prepared statement
			myStmt = myConn.prepareStatement(sql);
		}

		// 10- Execute statement
		myRs = myStmt.executeQuery();

		// 11- Retrieve data from result set row
		while (myRs.next()) {

			// 12- Retrieve data from result set row
			int product_id = myRs.getInt("product_id");
			String category_name = myRs.getString("category_name");
			String product_name = myRs.getString("product_name");
			String product_descrip = myRs.getString("product_descrip");

			// 13- Create new student object
			Product tempProduct = new Product(product_id, category_name, product_name, product_descrip);

			// 14- Add it to the list of students
			products.add(tempProduct);
		}

		return products;
	} finally {

		// 15- Clean up JDBC objects
		close(myConn, myStmt, myRs);
	}
}
	
	
}
