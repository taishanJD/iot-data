package com.quarkdata.data.util;


import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.Datasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import java.io.IOException;
import java.util.Map;

/**
 * MySQL,Cassandra,Hive数据导入到mongo中
 * @author jiadao
 */
public class DB2MongoUtil {

    @Autowired
	private MongoOperations mongoOperations; //方法2


    public static Map<String, String> props = PropertiesUtils.prop;
    public static void main(String[] args) {

        String mongodb = props.get("mongo.dbname");
        System.out.print(mongodb);
    }


    public void mysql2Mongo(){

    }

    /**
     * mysql导出数据到csv中的命令行语句
     * 命令示例：
     * " select id,product_id,model_id,name,description,unit_name,unit_sign,frequency,type,length,create_by,create_date,update_by,update_date,type_value
     * from data_point
     * into outfile '/var/lib/mysql-files/data_point_prev.csv'
     * fields terminated by ',' optionally enclosed by "" escaped by "" lines terminated by '\r\n' ";
     *
     *
     */

    private static String getMysqlExportCommand(Datasource mysqlDS, String tableName) {

        StringBuffer command = new StringBuffer();
//
//        String username = mysqlDS.getUsername();//用户名
//        String password = mysqlDS.getPassword();//用户密码
//        String exportDatabaseName = mysqlDS.getDb();//需要导出的数据库名
//        String host = mysqlDS.getHost();//从哪个主机导出数据库，如果没有指定这个值，则默认取localhost
//        Integer port = mysqlDS.getPort();//使用的端口号
//        String exportPath = "\'/var/lib/mysql-files/"+tableName+"_prev_1"+".csv\'";//导出路径
////        String MysqlPath = properties.getProperty("MysqlPath"); //  路径是mysql中 bin 文件 的位置
//
//        // dataset --> datasetschema --> 列名 --> 查
//        String selectSql = "select id,product_id,model_id,name,description,unit_name,unit_sign,frequency,type,length,create_by,create_date,update_by,update_date,type_value from data_point";
//        String outfileCmd = "into outfile ";
//
//        //注意哪些地方要空格，哪些不要空格
//        command.append(MysqlPath).append("mysqldump -u").append(username).append(" -p").append(password)//密码是用的小p，而端口是用的大P。
//                .append(" -h").append(host).append(" -P").append(port).append(" ").append(exportDatabaseName).append(" -r ").append(exportPath);

        return command.toString();
    }
}
