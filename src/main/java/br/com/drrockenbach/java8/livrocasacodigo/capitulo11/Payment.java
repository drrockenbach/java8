package br.com.drrockenbach.java8.livrocasacodigo.capitulo11;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Payment {

	private List<Product> products;
	private LocalDateTime date;
	private Customer customer;

	public Payment(List<Product> products, LocalDateTime date, Customer customer) {
		super();
		this.products = Collections.unmodifiableList(products);
		this.date = date;
		this.customer = customer;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getTotal() {
		return products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public String toString() {
		return "[Payment: "+date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +" "+customer + " " + products + "]";
	}
	
}
