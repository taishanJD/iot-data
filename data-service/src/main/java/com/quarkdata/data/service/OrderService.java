package com.quarkdata.data.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quarkdata.data.model.mongo.Order;

public interface OrderService {
	
	 public List<Order> queryAll() throws Exception;
	 
	 public Page<Order> queryAllByPage(int page,int rows) throws Exception;
	 
	 public List<Order> findByName(String name); 
	 
	 public List<Order> findByNameLike(String c);
	 
	 public List<Order> findByAddAndNumber(String c, String t);
	 
	 public List<Order> findByIdNotNull(String id);
	 
	 public List<Order> findByAddNotNull();
	 
	 public List<Order> findChucksOrders(String t);
	 
	 public long count();
}
