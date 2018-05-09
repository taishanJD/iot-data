package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;

public interface DataSetService {

    /**
     * 新建数据集，直接对应源数据库中的源表，不新建表
     * @param projectId 项目id
     * @param dataSourceId 数据源id
     * @param dataSetName  数据集名称
     * @param tableName  表名
     * @param isFloatToInt  是否浮点型转整型：0否1是
     * @param createUser  创建人id
     * @return
     */
    ResultCode addDataSet(Long projectId, Long dataSourceId, String dataSetName, String tableName, String isFloatToInt ,Long createUser);

    /**
     * 在工作流中创建某种数据处理时，新建数据集作为输出，不指定已有的table名，而是新建一个空table，表结构复制输入数据集的表结构，表名为project key加data_set名
     * @param projectId 项目id
     * @param dataSourceId 数据源id
     * @param workFlowId 工作流id
     * @param dataSetName  数据集名称
     * @param createUser  创建人id
     * @return
     */
    ResultCode addDataSetInWorkFlow(Long projectId, Long dataSourceId, Long workFlowId, String dataSetName ,Long createUser);
}
