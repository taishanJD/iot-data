package com.quarkdata.data.service.impl;

import java.util.List;
import java.util.Map;

import com.quarkdata.data.dal.dao.DatasetMapper;
import com.quarkdata.data.dal.dao.DatasourceMapper;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.Datasource;
import com.quarkdata.data.util.db.DB2MongoUtil;
import com.quarkdata.data.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;
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

	@Autowired
	private DatasetMapper datasetMapper;

	@Autowired
	private DatasourceMapper datasourceMapper;
	
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

	@Override
	public void update(String id,Order order) {
//		Order order1 = orderRepository.findById(id);
////		order1.setAdd(order.getAdd());
//		order1.setAdd("123434ADD");
////		order1.setDate(order.getDate());
////		order1.setItem(order.getItem());
////		order1.setName(order.getName());
////		order1.setNumber(order.getNumber());
//		orderRepository.save(order1);
		
		//根据条件查询出来后 再去修改 
		Criteria criteria = Criteria.where("id").is(id);  
		Query query = new Query(criteria); 
		Update update = Update.update("add","1211ASDF").set("cname", "xiaoli");  
		mongoOperations.findAndModify(query, update, Order.class);//查询出来后修改
	}

	@Override
	public Order findById(String id) {
		return orderRepository.findById(id);
	}

	@Override
	public WriteResult deleteByAdd(String add) {
//		 return orderRepository.deleteQuery();
		Criteria criteria = Criteria.where("add").is(add);//add等于
		Query query2 = new Query(criteria);  
		return mongoOperations.remove(query2, Order.class);
	}

	@Override
	public ResultCode<?> testMysql2Mongo(Long dataSetId, int selectType, int selectValue, String filter) {
		Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
		Long dataSourceId = dataset.getDatasourceId();
		Datasource datasource = datasourceMapper.selectByPrimaryKey(dataSourceId);
		List<Map<String,Object>> list = DB2MongoUtil.mysql2Mongo(dataset,datasource,1,20,"test");
		return ResultUtil.success(list);
	}

}
