package marina.managedBeans;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.sun.istack.internal.logging.Logger;



@ManagedBean
@SessionScoped
public class ProductController {
	

	private List<Product> products;
	private ProductDbUtil productDbUtil;
	private Logger logger = Logger.getLogger(getClass());  // check here bc i changed it 
	private String theSearchName;
	
	
	public String getTheSearchName() {
		return theSearchName;
	}

	public void setTheSearchName(String theSearchName) {
		this.theSearchName = theSearchName;
	}
	
	public  ProductController() throws Exception {
		products = new ArrayList<Product>();
		productDbUtil = ProductDbUtil.getInstance();
		
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public ProductDbUtil getProductDbUtil() {
		return productDbUtil;
	}

	public void setProductDbUtil(ProductDbUtil productDbUtil) {
		this.productDbUtil = productDbUtil;
	}
	
	public void loadProducts() {

		logger.info("Loading products");
		logger.info("theSearchName = " + theSearchName);

		try {

			// ------------------------------------------------------------------------------
			if (theSearchName != null && theSearchName.trim().length() > 0) {
				// Search for product by name
				products = productDbUtil.searchProducts(theSearchName);
			}
			// ------------------------------------------------------------------------------

			else {
				// Get all students from database
				products = productDbUtil.getProducts();
			}

		} catch (Exception exc) {
			// Send this to server logs
			logger.log(Level.SEVERE, "Error loading products", exc);

			// Add error message for JSF page
			addErrorMessage(exc);
		} finally {
			// Reset the search info
			theSearchName = null;
		}
	}
	
	public String addProduct(Product theProduct) {

		logger.info("\n\n-------------------- Adding product: " + theProduct);

		try {

			// add product to the database
			productDbUtil.addProduct(theProduct);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding products", exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "shopping?faces-redirect=true";
	}

	// UPDATE ---------------------------------------------------------------------------------------
	public String loadProduct(int product_id) {

		logger.info("\n\n-------------------- loading product: " + product_id);

		try {
			// 1- get product from database
			Product theProduct = productDbUtil.getProduct(product_id);

			// 2- Create an externalContext object ........................................
			/*
			 * ExternalContext can be consider as a memory space we use 
			 * to store data and have access to it
			 * 
			 * FacesContext contains all of the per-request state information 
			 * related to the processing of a single JavaServer Faces request, 
			 * and the rendering of the corresponding response.
			 * 
			 * A FacesContext instance is associated with a particular request 
			 * at the beginning of request processing, 
			 * by a call to the getFacesContext() method of the 
			 * FacesContextFactory instance associated with the 
			 * current web application. The instance remains active until 
			 * its release() method is called, after which no further 
			 * references to this instance are allowed. 
			 * While a FacesContext instance is active, 
			 * it must not be referenced from any thread other than 
			 * the one upon which the servlet container executing 
			 * this web application utilizes for the processing of 
			 * this request.
			*/
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			// 3- Return request object Map attribute from ExternalContext object
			Map<String, Object> requestMap = externalContext.getRequestMap();

			// 4- Put selected product to request object Map attributes
			requestMap.put("product", theProduct);
			//.............................................................................
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading product id:" + product_id, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		// 5- Go to the next page to update this selected product
		return "update-product-form.xhtml";
	}

	// This method is calling from update-product-form.xhtml
	public String updateProduct(Product theProduct) {

		logger.info("\n\n-------------------- updating product: " + theProduct);

		try {

			// 1- Update product in the database
			productDbUtil.updateProduct(theProduct);

		} catch (Exception exc) {
			// 2- Send this to server logs
			logger.log(Level.SEVERE, "Error updating product: " + theProduct, exc);

			// 3- Add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		// redirect: Browser URL will be updated. It is like sending an other GET request for a specific page
		// forward:  Browser URL will not be updated
		return "shopping?faces-redirect=true";
	}
	// -----------------------------------------------------------------------------------------------
	
	public String deleteProduct(int product_id) {

		logger.info("Deleting product id: " + product_id);

		try {

			// delete the product from the database
			productDbUtil.deleteProduct(product_id);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting product id: " + product_id, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "list-students";
	}

	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}


	
}
