package com.quarkdata.data.web.api.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.UserService;

/**
 * 不需要登录可直接访问
 * @author huliang
 *
 */
@Controller
public class UserController2 {
	
	@Autowired
	UserService userService;
	
    @RequestMapping(value = "/update_status",method=RequestMethod.POST)
    public @ResponseBody ResultCode updateUserStatus(
    		@RequestParam(value = "key", required = true) String key,
    		@RequestParam(value = "password") String password) {
        return userService.updateUserStatus(key, password);
    }
}
