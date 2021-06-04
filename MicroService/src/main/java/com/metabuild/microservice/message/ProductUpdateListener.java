package com.metabuild.microservice.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.metabuild.microservice.product.Product;
import com.metabuild.microservice.service.ProductController;

@Component
public class ProductUpdateListener {

	@Autowired
	ProductController productController;

	private Logger logger = LoggerFactory.getLogger(ProductUpdateListener.class);

	@JmsListener(destination = "${jms.ProductTopic}", subscription = "productSearchListener")
	public void receiveMessage(ProductUpdateMsg updateMsg) {
		Product product = updateMsg.getProduct();
		Boolean isDelete = updateMsg.getIsDelete();

		if (isDelete) {
			productController.deleteProduct(product.getId());
			logger.info("Delete : " + product.getId());
		} else {
			productController.updateProduct(product.getId(), product, updateMsg.getErrors());
			logger.info("Update : " + product.getId());
		}
	}

}
