package ca.marina.finalexam;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean
public class Customer {

	private String name;
	private String family;
	private String phone;
	private String address;
	private String email;
	private String userName;
	private String password;
	private String carType;
	private String brand;
	private String startYear;
	private String startMonth;
	private String startDay;
	private String numberOfDays;
	private int rate;
	private int total;
	
	public Customer() {
	}

	public Customer(String name, String family, String phone, String address, String email, String userName,
			String password, String carType, String brand, String startYear, String startMonth, String startDay,
			String numberOfDays, int rate, int total) {
		this.name = name;
		this.family = family;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.carType = carType;
		this.brand = brand;
		this.startYear = startYear;
		this.startMonth = startMonth;
		this.startDay = startDay;
		this.numberOfDays = numberOfDays;
		this.rate = rate;
		this.total = total;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
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

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
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
