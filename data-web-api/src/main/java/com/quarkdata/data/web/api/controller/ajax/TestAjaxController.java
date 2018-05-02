package com.quarkdata.data.web.api.controller.ajax;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.WriteResult;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.mongo.Order;
import com.quarkdata.data.web.api.controller.RouteKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quarkdata.data.service.OrderService;
import com.quarkdata.data.service.TestService;

/**
 * 
 *
 * Created by xu on 2017-01-16.
 */
@Controller
@RequestMapping(RouteKey.PREFIX_AJAX+"/"+RouteKey.TEST)
public class TestAjaxController {

    @Autowired
    private TestService testService;
    
    @Autowired
    private OrderService orderService;
    
    static Logger logger = LoggerFactory.getLogger(TestAjaxController.class);

    /**
     * test
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/test1")
    public ResultCode<?> getTest(@RequestParam(value = "dataSetId", required = true) Long dataSetId,
                                 @RequestParam(value = "selectType", required = true) Integer selectType,
                                 @RequestParam(value = "selectValue", required = true) Integer selectValue,
                                 @RequestParam(value = "filter", required = false) String filter){
        ResultCode<?> result = new ResultCode<>();
        try{
        	result = orderService.testMysql2Mongo(dataSetId,selectType,selectValue,filter);
        } catch (Exception e){
            logger.error("get pay channels error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }
    
    /**
     * test
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/queryAll")
    public ResultCode<List<Order>> queryAll(HttpServletRequest request, HttpServletResponse response){
        ResultCode<List<Order>> result = new ResultCode<List<Order>>();
        try{
        	List<Order> orders = orderService.queryAll();
        	result.setData(orders);
        } catch (Exception e){
            logger.error("quary all Orders error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/find_by_name")
    public ResultCode<List<Order>> findByName(
    		@RequestParam(value = "name", required = true) String name){
        ResultCode<List<Order>> result = new ResultCode<List<Order>>();
        try{
        	List<Order> order = orderService.findByName(name);
        	result.setData(order);
        } catch (Exception e){
            logger.error("quary by name error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/find_by_add_nn")
    public ResultCode<List<Order>> findByAddNotNull(){
        ResultCode<List<Order>> result = new ResultCode<List<Order>>();
        try{
        	List<Order> order = orderService.findByAddNotNull();
        	result.setData(order);
        } catch (Exception e){
            logger.error("quary by name error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/mongo_test")
    public ResultCode<List<Order>> mongoTest(){
        ResultCode<List<Order>> result = new ResultCode<List<Order>>();
        try{
        	List<Order> order = orderService.findByAddAndNumber("test", "004");
//        	List<Order> order = orderService.findByAddNotNull();
//        	List<Order> order = orderService.findByNameLike(""); //查询全部
//        	List<Order> order = orderService.findByNameLike("o"); // 模糊查询
//        	List<Order> order = orderService.findChucksOrders("004"); // 注解查询 @Query(value ="{'add':'test','onumber':?0}",fields="{'cname':0}
        	
        	result.setData(order);
        } catch (Exception e){
            logger.error("quary by name error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/page")
    public ResultCode<Page<Order>> orderPage(){
        ResultCode<Page<Order>> result = new ResultCode<Page<Order>>();
        try{
        	Page<Order> order = orderService.queryAllByPage(4, 3); // 分页查询
        	
        	result.setData(order);
        } catch (Exception e){
            logger.error("quary by name error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/count")
    public ResultCode<Long> orderCount(){
        ResultCode<Long> result = new ResultCode<Long>();
        try{
        	Long order = orderService.count(); // 查询数量 @CountQuery("{'add':'test'}")
        	
        	result.setData(order);
        } catch (Exception e){
            logger.error("quary by name error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/update")
    public ResultCode<Long> update(){
        ResultCode<Long> result = new ResultCode<Long>();
        try{
        	Order order = new Order();
        	order.setAdd("12345ADD");
        	orderService.update("qwee",order); // 查询数量 @CountQuery("{'add':'test'}")
        	
        	result.setData(null);
        } catch (Exception e){
            logger.error("quary by name error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete")
    public ResultCode<WriteResult> delete(){
        ResultCode<WriteResult> result = new ResultCode<WriteResult>();
        try{
//        	Order order = new Order();
//        	order.setAdd("12345ADD");
        	orderService.deleteByAdd("test"); // 查询数量 @DeleteQuery("{'add':'test'}")
        	
        	result.setData(orderService.deleteByAdd("test"));
        } catch (Exception e){
            logger.error("quary by name error", e);
            result.setCode(Messages.API_ERROR_CODE);
            result.setMsg(Messages.API_ERROR_MSG);
            return result;
        }
        return result;
    }
}
