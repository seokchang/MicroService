package com.metabuild.microservice.message;

import org.springframework.validation.Errors;

import com.metabuild.microservice.product.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductUpdateMsg {
	Product product;
	Boolean isDelete;
	Errors errors;

	public ProductUpdateMsg(Product product, Boolean isDelete) {
		super();
		this.product = product;
		this.isDelete = isDelete;
	}

}
