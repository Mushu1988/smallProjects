package marina.managedBeans;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Product {
	
	private int product_id;
	private String category_name;
	private String product_name;
	private String product_descrip;
	
	public Product() {
		super();
	}


	public Product(int product_id, String category_name, String product_name, String product_descrip) {
		super();
		this.product_id = product_id;
		this.category_name = category_name;
		this.product_name = product_name;
		this.product_descrip = product_descrip;
	}


	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	public String getCategory_name() {
		return category_name;
	}


	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}


	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_descrip() {
		return product_descrip;
	}

	public void setProduct_descrip(String product_descrip) {
		this.product_descrip = product_descrip;
	}
	
	@Override
	public String toString() {
		return "Product [product_id=" + product_id + ", category_name=" + category_name + ", product_name=" + product_name + ", product_descrip=" + product_descrip + "]";
	}

}
