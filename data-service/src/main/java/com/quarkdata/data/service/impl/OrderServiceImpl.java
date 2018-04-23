package com.quarkdata.data.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.quarkdata.data.dal.mongoRepository.OrderRepository;
import com.quarkdata.data.model.mongo.Order;
import com.quarkdata.data.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OrderRepository orderRepository; //  方法一
	
	@Autowired
	private MongoOperations mongoOperations; //方法2
	
	@Override
	public List<Order> queryAll() throws Exception {
//		return orderRepository.findAll();  // 
		return mongoOperations.findAll(Order.class);
	}
	
	@Override
	public Page<Order> queryAllByPage(int page,int rows) throws Exception {
//	        PageRequest pageRequest = new PageRequest(page-1,rows);
//	        return orderRepository.findAll(pageRequest);
		
		
		List<Order> orders = mongoOperations.find(new Query().skip(1).limit(3), Order.class);
		logger.info(orders);
		return null;
	 }

	@Override
	public List<Order> findByName(String name) {
//		return orderRepository.findByName(name);
		
		 Criteria criteria = Criteria.where("cname").is(name);
		 List<Order> orders = mongoOperations.find(new Query(criteria),Order.class);
		 return orders;
	}

	@Override
	public List<Order> findByNameLike(String c) {
//		return orderRepository.findByNameLike(c);
		
		Criteria criteria = Criteria.where("cname").regex("/"+c+"/");
		List<Order> orders = mongoOperations.find(new Query(criteria),Order.class);
		return orders;
	}
	
	@Override
	public List<Order> findByIdNotNull(String id) {
		return orderRepository.findByIdNotNull(id);
	}

	@Override
	public List<Order> findByAddNotNull() {
//		return orderRepository.findByAddNotNull();
		
		Criteria criteria = Criteria.where("cname").exists(true);
		List<Order> orders = mongoOperations.find(new Query(criteria),Order.class);
		return orders;
	}

	@Override
	public List<Order> findByAddAndNumber(String add, String num) {
//		return orderRepository.findByAddAndNumber(add, num);
		
		Criteria criteria = Criteria.where("add").exists(true).and("onumber").exists(true);
		List<Order> orders = mongoOperations.find(new Query(criteria),Order.class);
		return orders;
	}

	@Override
	public List<Order> findChucksOrders(String t) {
//		return orderRepository.findCsers(t);
		
		Criteria criteria = Criteria.where("add").is("test").and("onumber").is(t);
		List<Order> orders = mongoOperations.find(new Query(criteria),Order.class);
		return orders;
	}

	@Override
	public long count() {
//		return orderRepository.count();
		
		return mongoOperations.getCollection("orders").count();
	}

}
