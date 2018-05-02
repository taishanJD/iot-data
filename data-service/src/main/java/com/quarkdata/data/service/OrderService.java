package com.quarkdata.data.service;

import java.util.List;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.Datasource;
import org.springframework.data.domain.Page;

import com.mongodb.WriteResult;
import com.quarkdata.data.model.mongo.Order;

public interface OrderService {
	
	 public List<Order> queryAll() throws Exception;
	 
	 public Page<Order> queryAllByPage(int page,int rows) throws Exception;
	 
	 public List<Order> findByName(String name); 
	 
	 public List<Order> findByNameLike(String c);
	 
	 public List<Order> findByAddAndNumber(String c, String t);
	 
	 public Order findById(String id);
	 
	 public List<Order> findByIdNotNull(String id);
	 
	 public List<Order> findByAddNotNull();
	 
	 public List<Order> findChucksOrders(String t);
	 
	 public long count();
	 
	 public void update(String id,Order order);
	 
	 public WriteResult deleteByAdd(String add);


	 public ResultCode<?> testMysql2Mongo(Long dataSetId, int selectType, int selectValue, String filter);
}
