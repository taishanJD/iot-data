package com.quarkdata.data.dal.mongoRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mongodb.WriteResult;
import com.quarkdata.data.model.mongo.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	
	public List<Order> findByName(String name); 
	
	public List<Order> findByNameLike(String c);
	
	public List<Order> findByAddAndNumber(String c, String t);
	
	@Query(value="{_id:?0}")
	public Order findById(String id);
	
	@Query(value="{'_id':{'$ne':null}}")
	public List<Order> findByIdNotNull(String id);
	
	public List<Order> findByAddNotNull();
	
	@Query(value ="{'add':'test','onumber':?0}",fields="{'cname':0}")
    public List<Order> findCsers(String t);
	
	@CountQuery(value="{'add':'test'}")
	public long count();
	
	@DeleteQuery(value="{'add':'test'}")
	public WriteResult deleteQuery();
	
//	public void update(String id);
}
