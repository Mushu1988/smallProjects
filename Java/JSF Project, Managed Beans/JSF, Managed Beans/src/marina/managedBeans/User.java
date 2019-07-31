package marina.managedBeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
public class User {
	
	private String id;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	private String email;
	private String userName;
	private String password;
	private String[] subscription;
	private String profileSetting;
	
	public User() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public String getProfileSetting() {
		return profileSetting;
	}


	public void setProfileSetting(String profileSetting) {
		this.profileSetting = profileSetting;
	}



	public String[] getSubscription() {
		return subscription;
	}


	public void setSubscription(String[] subscription) {
		this.subscription = subscription;
	}


			//Custom Validation method
			public void validateTheUserName (FacesContext context, UIComponent component, Object value) throws ValidatorException{
				
				if(value==null) return;
				
				String data = value.toString();
				
				//Course code shall start from IPD17
				if(!data.startsWith("IPD17")) {
					FacesMessage message = new FacesMessage("User name must start with IPD17");
					throw new ValidatorException(message);
				}
			}

	
	
	
}

