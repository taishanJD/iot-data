package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.web.api.controller.RouteKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(RouteKey.PREFIX_AJAX+ RouteKey.DATASET)
public class DataSetController {

    static Logger logger = Logger.getLogger(DataSetController.class);

    @Autowired
    DataSetService dataSetService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultCode addDataSet(@RequestParam(value = "projectId") Long projectId,
                                 @RequestParam(value = "dataSourceId") Long dataSourceId,
                                 @RequestParam(value = "dataSetName") String dataSetName,
                                 @RequestParam(value = "tableName") String tableName,
                                 @RequestParam(value = "isFloatToInt") String isFloatToInt) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
        Long createUser = 1l;
        ResultCode resultCode;
        try {
            resultCode = dataSetService.addDataSet(projectId, dataSourceId, dataSetName, tableName, isFloatToInt,createUser);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("创建data_set失败",e);
            resultCode = ResultUtil.error(Messages.ADD_DATASET_FAILED_CODE,Messages.ADD_DATASET_FAILED_MSG);
        }
        return resultCode;
    }


    @ResponseBody
    @RequestMapping(value = "/add_in_workflow", method = RequestMethod.POST)
    public ResultCode addDataSetInWorkflow(@RequestParam(value = "projectId") Long projectId,
                                 @RequestParam(value = "dataSourceId") Long dataSourceId,
                                           @RequestParam(value = "workFlowId") Long workFlowId,
                                           @RequestParam(value = "inputDataSetId") Long inputDataSetId,
                                 @RequestParam(value = "dataSetName") String dataSetName) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
        Long createUser = 1l;
        ResultCode resultCode;
        try {
            resultCode = dataSetService.addDataSetInWorkFlow(projectId, dataSourceId,workFlowId,inputDataSetId,dataSetName,createUser);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("创建data_set失败",e);
            resultCode = ResultUtil.error(Messages.ADD_DATASET_FAILED_CODE,Messages.ADD_DATASET_FAILED_MSG);
        }
        return resultCode;
    }
}
