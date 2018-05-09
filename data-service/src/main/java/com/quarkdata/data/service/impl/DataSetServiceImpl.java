package com.quarkdata.data.service.impl;

import com.quarkdata.data.dal.dao.DatasetMapper;
import com.quarkdata.data.dal.dao.DatasourceMapper;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.Datasource;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DataSetServiceImpl implements DataSetService {

    @Autowired
    DatasetMapper datasetMapper;

    @Autowired
    DatasourceMapper datasourceMapper;

    /**
     * 新建数据集，直接对应源数据库中的源表，不新建表
     * @param projectId    项目id
     * @param dataSourceId 数据源id
     * @param dataSetName  数据集名称
     * @param tableName    表名
     * @param isFloatToInt 是否浮点型转整型：0否1是
     * @param createUser   创建人id
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResultCode addDataSet(Long projectId, Long dataSourceId, String dataSetName, String tableName, String isFloatToInt, Long createUser) {
        Dataset dataset = new Dataset();
        dataset.setCreateTime(new Date());
        dataset.setCreateUser(createUser);
        dataset.setDatasetName(dataSetName);
        dataset.setDatasourceId(dataSourceId);
        dataset.setIsFloatToInt(isFloatToInt);
        dataset.setProjectId(projectId);
        dataset.setTableName(tableName);

        //is_write字段：是否可写（0：否、1：是），根据对应的datasource的is_write字段值确定
        Datasource datasource = datasourceMapper.selectByPrimaryKey(dataSourceId);
        dataset.setIsWrite(datasource.getIsWrite());

        dataset.setUpdateTime(new Date());
        dataset.setUpdateUser(createUser);

        //以下非空字段在添加时都有默认值
        //is_sync字段：是否需要同步数据（0：否、1：是）默认0
        //sample_type字段：采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））默认0
        //sample_type_value字段：采样方式对应值（采样个数、采样比例）默认采样个数10000
        //is_sample_filter字段：是否采样过滤（0：否、1：是）默认0

        datasetMapper.insertSelective(dataset);
        return ResultUtil.success();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResultCode addDataSetInWorkFlow(Long projectId, Long dataSourceId, Long workFlowId, String dataSetName, Long createUser) {
        return null;
    }
}
