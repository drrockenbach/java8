package br.com.drrockenbach.java8.livrocasacodigo.capitulo11;

import java.math.BigDecimal;
import java.nio.file.Path;

public class Product {

	private String name;
	private Path file;
	private BigDecimal price;

	public Product(String name, Path file, BigDecimal price) {
		super();
		this.name = name;
		this.file = file;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Path getFile() {
		return file;
	}

	public void setFile(Path file) {
		this.file = file;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", file=" + file + ", price=" + price + "]";
	}
	
}
