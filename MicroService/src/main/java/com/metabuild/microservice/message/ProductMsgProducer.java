package com.metabuild.microservice.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.metabuild.microservice.product.Product;

@Component
public class ProductMsgProducer {

	@Autowired
	JmsTemplate jmsTemplate;

	@Value("${jms.ProductTopic}")
	private String productTopic;

	public void sendUpdate(Product product, Boolean isDelete) {
		ProductUpdateMsg updateMsg = new ProductUpdateMsg(product, isDelete);

		jmsTemplate.convertAndSend(productTopic, updateMsg);
	}
}
