package ca.marina.finalexam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CustomerController {
	
	private List<Customer> customers;
	private CustomerDbUtil customerDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());

	// 1- Add theSearchName attribute -----------------------------------------------
	private String theSearchName;
	// ------------------------------------------------------------------------------

	// 2- theSearchName getter and setter -------------------------------------------
	public String getTheSearchName() {
		return theSearchName;
	}

	public void setTheSearchName(String theSearchName) {
		this.theSearchName = theSearchName;
	}
	// ------------------------------------------------------------------------------

	public List<Customer> getCustomers() {
		return customers;
	}

	public CustomerController() throws Exception {
		customers = new ArrayList<>();

		customerDbUtil = CustomerDbUtil.getInstance();
	}

	// 3- Add logic to loadStudents() method which will be called when page is reloaded
	// ------------------------------------------------------------------------------
	public void loadCustomers() {

		logger.info("Loading customers");
		logger.info("theSearchName = " + theSearchName);

		try {

			// ------------------------------------------------------------------------------
			if (theSearchName != null && theSearchName.trim().length() > 0) {
				// Search for customer by name
				customers = customerDbUtil.searchCustomers(theSearchName);
			}
			// ------------------------------------------------------------------------------

			else {
				// Get all customers from database
				customers = customerDbUtil.getCustomers();
			}

		} catch (Exception exc) {
			// Send this to server logs
			logger.log(Level.SEVERE, "Error loading customers", exc);

			// Add error message for JSF page
			addErrorMessage(exc);
		} finally {
			// Reset the search info
			theSearchName = null;
		}
	}
	// ------------------------------------------------------------------------------

	public String addCustomer(Customer theCustomer) {

		logger.info("Adding customer: " + theCustomer);

		try {

			// add customer to the database
			customerDbUtil.addCustomer(theCustomer);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding customers", exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "show-all?faces-redirect=true";
	}

	public String loadCustomer(String familyName) {

		logger.info("loading customer: " + familyName);

		try {
			// get customer from database
			Customer theCustomer = customerDbUtil.getCustomer(familyName);

			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("customer", theCustomer);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading customer:" + familyName, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "update-customer-form.xhtml";
	}

	public String updateCustomer(Customer theCustomer) {

		logger.info("updating customer: " + theCustomer);

		try {

			// update customer in the database
			customerDbUtil.updateCustomer(theCustomer);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating customer: " + theCustomer, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "show-all?faces-redirect=true";
	}

	public String deleteCustomer(String family) {

		logger.info("Deleting customer: " + family);

		try {

			// delete the customer from the database
			customerDbUtil.deleteCustomer(family);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting customer: " + family, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "show-all";
	}

	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
