package com.metabuild.microservice.product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "product")
public class Product {

	@Id
	@Indexed(unique = true, sparse = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Field("name")
	@NotBlank
	private String name;

	@Field("price")
	@NotNull
	private int price;

	@Field("category")
	@NotBlank
	private String category;
}
