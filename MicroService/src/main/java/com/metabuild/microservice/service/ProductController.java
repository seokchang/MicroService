package com.metabuild.microservice.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metabuild.microservice.common.ErrorsResource;
import com.metabuild.microservice.dao.ProductRepository;
import com.metabuild.microservice.message.ProductMsgProducer;
import com.metabuild.microservice.product.Product;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	ProductMsgProducer msgProducer;

	// Product 1개 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getProduct(@PathVariable String id) {
		Optional<Product> optionalProduct = this.productRepository.findById(id);

		if (!optionalProduct.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optionalProduct);
	}

	// Product 전체 조회
	@GetMapping
	public ResponseEntity<?> getProductsAll(Pageable pageable, PagedResourcesAssembler<Product> assembler) {
		Page<Product> page = this.productRepository.findAll(pageable);

		return ResponseEntity.ok(page);
	}

	// Product Category 별로 검색
	@GetMapping("/category/{category}")
	public List<Product> getProductsForCategory(@PathVariable String category) {
		return this.productRepository.findByCategory(category);
	}

	// Product 등록
	@PostMapping
	public ResponseEntity<?> insertProduct(@RequestBody @Valid Product product, Errors errors) {
		// Validation API Annotation을 사용해서 입력검증 체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		Product saveProduct = this.productRepository.save(product);

		return ResponseEntity.ok(saveProduct);
	}

	// Product 수정
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product,
			Errors errors) {
		Optional<Product> optionalProduct = this.productRepository.findById(id);

		if (!optionalProduct.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		Product existingProduct = optionalProduct.get();

		existingProduct.setName(product.getName());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setCategory(product.getCategory());

		Product saveProduct = productRepository.save(existingProduct);

		return ResponseEntity.ok(saveProduct);
	}

	// Product 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id) {
		Optional<Product> optionalProduct = this.productRepository.findById(id);

		if (!optionalProduct.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Product existingProduct = optionalProduct.get();

		productRepository.delete(existingProduct);
		msgProducer.sendUpdate(existingProduct, true);

		return ResponseEntity.ok(existingProduct);
	}

	private ResponseEntity<?> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	}
}
