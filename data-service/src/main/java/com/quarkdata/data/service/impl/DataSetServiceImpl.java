package com.quarkdata.data.service.impl;

import com.quarkdata.data.dal.dao.DatasetMapper;
import com.quarkdata.data.dal.dao.DatasourceMapper;
import com.quarkdata.data.dal.dao.ProjectMapper;
import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.Datasource;
import com.quarkdata.data.model.dataobj.Project;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.util.db.MySqlUtils;
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

    @Autowired
    ProjectMapper projectMapper;

    /**
     * 新建数据集，直接对应源数据库中的源表，不新建表
     *
     * @param projectId    项目id
     * @param dataSourceId 数据源id
     * @param dataSetName  数据集名称
     * @param tableName    对应数据源表名（mysql table、MongoDB collection等）
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

        //以下非空字段在添加时使用默认值
        //is_sync字段：是否需要同步数据（0：否、1：是）默认0
        //sample_type字段：采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））默认0
        //sample_type_value字段：采样方式对应值（采样个数、采样比例）默认采样个数10000
        //is_sample_filter字段：是否采样过滤（0：否、1：是）默认0

        datasetMapper.insertSelective(dataset);
        return ResultUtil.success();
    }

    /**
     * 在工作流中创建某种数据处理时，新建数据集作为输出，
     * 不指定已有的table名，而是新建一个空table，表结构复制输入数据集的表结构，表名为project key加data_set名
     *
     * @param projectId      项目id
     * @param dataSourceId   数据源id
     * @param workFlowId     工作流id
     * @param inputDataSetId 输入的数据集id
     * @param dataSetName    数据集名称
     * @param createUser     创建人id
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResultCode addDataSetInWorkFlow(Long projectId, Long dataSourceId, Long workFlowId, Long inputDataSetId, String dataSetName, Long createUser) {

        //table_name : 对应数据源表名（mysql table、MongoDB collection等）,project key+"_"+dataSetName
        Project project = projectMapper.selectByPrimaryKey(projectId);
        String tableName = project.getProjectKey().trim() + Constants.TABLE_NAME_DELIMITER + dataSetName;

        //输入数据集
        Dataset inputDataSet = datasetMapper.selectByPrimaryKey(inputDataSetId);
        String inputTableName = inputDataSet.getTableName();//输入数据集对应的数据表
        Long inputDataSourceId = inputDataSet.getDatasourceId();
        Datasource inputDataSource = datasourceMapper.selectByPrimaryKey(inputDataSourceId);
        String inputDataType = inputDataSource.getDataType(); //输入数据源的数据类型
        String inputHost = inputDataSource.getHost(); //输入数据源的域
        Integer inputPort = inputDataSource.getPort();//输入数据源的端口
        String inputDbName = inputDataSource.getDb();//输入数据源的库名
        String inputUserName = inputDataSource.getUsername();//输入数据源的用户名
        String inputPassword = inputDataSource.getPassword();//输入数据集的密码

        //输出数据集
        Datasource outputDatasource = datasourceMapper.selectByPrimaryKey(dataSourceId); // 输出的数据源
        String outputDataType = outputDatasource.getDataType();// 输出的数据类型（0：MySQL、1：Oracle、2：PostgreSQL、3：MS SQL Server、4：HANA、5：MongoDB、6：Cassandra、7：ElasticSearch、8：Hive、9：Hbase、10：HDFS、11：HTTP、12：FTP、13：SFTP）
        String outputHost = outputDatasource.getHost();//输出数据源的域
        Integer outputPort = outputDatasource.getPort();//输出数据源的端口
        String outputDbName = outputDatasource.getDb();//输出数据源的库名
        String outputUserName = outputDatasource.getUsername();//输出数据源的用户名
        String outputPassword = outputDatasource.getPassword();//输出数据源的密码


        switch (inputDataType) {

            /********输入源为mysql的情况开始*********************************************************/
            case "0":
                switch (outputDataType) {
                    case "0"://输出源为mysql的情况开始
                        if (inputHost.equals(outputHost) && inputPort.equals(outputPort)) {

                            // 在同一个mysql服务器下复制表结构，可能同一个库下复制，也可能不同库
                            MySqlUtils mySqlUtils = MySqlUtils.getInstance(outputHost, outputPort.toString(), outputDbName, outputUserName, outputPassword);
                            mySqlUtils.getConnection();
                            try {
                                boolean result = false;
                                result = mySqlUtils.createTableFromExist(outputDbName, tableName, inputDbName, inputTableName);

                                if (result) { // 建表成功，dataset插入数据
                                    Dataset dataset = new Dataset();
                                    dataset.setCreateTime(new Date());
                                    dataset.setCreateUser(createUser);
                                    dataset.setDatasetName(dataSetName);
                                    dataset.setDatasourceId(dataSourceId);
                                    dataset.setProjectId(projectId);
                                    dataset.setWorkflowId(workFlowId);
                                    dataset.setUpdateUser(createUser);
                                    dataset.setUpdateTime(new Date());
                                    dataset.setTableName(tableName);

                                    //is_write : 新建的数据集，直接可写，不再根据对应数据源的is_write字段确定
                                    dataset.setIsWrite("1");

                                    //以下非空字段在添加时使用默认值
                                    //is_sync：是否需要同步数据（0：否、1：是）默认0
                                    //is_float_to_int : 是否浮点型转整型（0：否、1：是），默认0
                                    //sample_type字段：采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））默认0
                                    //sample_type_value字段：采样方式对应值（采样个数、采样比例）默认采样个数10000
                                    //is_sample_filter字段：是否采样过滤（0：否、1：是）默认0
                                    datasetMapper.insertSelective(dataset);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                mySqlUtils.closeDB();
                                return ResultUtil.success();
                            }
                        } else {
                            // 跨mysql服务器复制表结构
                        }
                        break; // 输出源为mysql的情况结束
                    case "6": //TODO 输出源为Cassandra的情况开始
                        break;
                    case "8": //TODO 输出源为Hive的情况开始
                        break;
                    default:
                        break;
                }
                break;
            /********输入源为mysql的情况结束***************************************************************/


            case "6": //Cassandra
                break;
            case "8": //Hive
                break;
            default:
                break;
        }

        return null;
    }
}
