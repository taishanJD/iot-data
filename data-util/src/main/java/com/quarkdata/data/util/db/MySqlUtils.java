package com.quarkdata.data.util.db;


import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlUtils {

    private static Logger logger = Logger.getLogger(MySqlUtils.class);

    // 表示定义数据库的用户名
    private String USERNAME;
    // 定义数据库的密码
    private String PASSWORD;
    // 定义数据库的驱动信息
    private String DRIVER = "com.mysql.jdbc.Driver";
    // 定义访问数据库的地址
    private String URL;

    private static MySqlUtils per = null;
    // 定义数据库的链接
    private Connection con = null;
    // 定义sql语句的执行对象
    private PreparedStatement pstmt = null;
    // 定义查询返回的结果集合
    private ResultSet resultSet = null;

    private MySqlUtils(String host, String port, String dbName, String userName, String password) {
        this.USERNAME = userName;
        this.PASSWORD = password;
        this.URL = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8";
    }

    /**
     * 单例模式，获得工具类的一个对象
     *
     * @return
     */
    public static MySqlUtils getInstance(String host, String port, String dbName, String userName, String password) {
        if (per == null) {
            per = new MySqlUtils(host, port, dbName, userName, password);
            per.registeredDriver();
        }
        return per;
    }

    private void registeredDriver() {
        try {
            Class.forName(DRIVER);
            logger.info("注册mysql驱动成功！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得数据库的连接
     *
     * @return
     */
    public Connection getConnection() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("连接mysql数据库成功! url == " + URL);
        return con;
    }

    /**
     * 完成对数据库的表的添加删除和修改的操作
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public boolean executeUpdate(String sql, List<Object> params)
            throws SQLException {

        boolean flag = false;

        int result = -1;  // 表示当用户执行添加删除和修改的时候所影响数据库的行数

        pstmt = con.prepareStatement(sql);

        if (params != null && !params.isEmpty()) {
            int index = 1;
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, i);
            }
        }

        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;

        return flag;
    }

    /**
     * 从数据库中查询数据
     *
     * @param sql    sql语句
     * @param params sql语句中查询参数列表(?)
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> executeQuery(String sql,
                                                  List<Object> params) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = con.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;

    }

    /**
     * jdbc的封装可以用反射机制来封装,把从数据库中获取的数据封装到一个类的对象里
     *
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public <T> List<T> executeQueryByRef(String sql, List<Object> params,
                                         Class<T> cls) throws Exception {
        List<T> list = new ArrayList<T>();
        int index = 1;
        pstmt = con.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            T resultObject = cls.newInstance();  // 通过反射机制创建实例
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); // 打开javabean的访问private权限
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        return list;

    }


    /**
     * 获取表结构信息
     *
     * @return
     */
//    public String getTableStructure(String tableName) {
//        try {
//            StringBuffer sql = new StringBuffer();
//            sql.append("select * from ");
//            sql.append(tableName);
//            pstmt = con.prepareStatement(sql.toString());
//            resultSet = pstmt.executeQuery();
//
//            ResultSetMetaData data = resultSet.getMetaData();
////            while (resultSet.next()) {
//                for (int i = 1; i <= data.getColumnCount(); i++) {
////获得所有列的数目及实际列数
//                    int columnCount = data.getColumnCount();
////获得指定列的列名
//                    String columnName = data.getColumnName(i);
////获得指定列的列值
////                    String columnValue = resultSet.getString(i);
////获得指定列的数据类型
//                    int columnType = data.getColumnType(i);
////获得指定列的数据类型名
//                    String columnTypeName = data.getColumnTypeName(i);
////所在的Catalog名字
//                    String catalogName = data.getCatalogName(i);
////对应数据类型的类
//                    String columnClassName = data.getColumnClassName(i);
////在数据库中类型的最大字符个数
//                    int columnDisplaySize = data.getColumnDisplaySize(i);
////默认的列的标题
//                    String columnLabel = data.getColumnLabel(i);
////获得列的模式
//                    String schemaName = data.getSchemaName(i);
////某列类型的精确度(类型的长度)
//                    int precision = data.getPrecision(i);
////小数点后的位数
//                    int scale = data.getScale(i);
////获取某列对应的表名
//                    String tableNamea = data.getTableName(i);
//// 是否自动递增
//                    boolean isAutoInctement = data.isAutoIncrement(i);
////在数据库中是否为货币型
//                    boolean isCurrency = data.isCurrency(i);
////是否为空
//                    int isNullable = data.isNullable(i);
////是否为只读
//                    boolean isReadOnly = data.isReadOnly(i);
////能否出现在where中
//                    boolean isSearchable = data.isSearchable(i);
//                    System.out.println(columnCount);
//                    System.out.println("获得列" + i + "的字段名称:" + columnName);
////                    System.out.println("获得列" + i + "的字段值:" + columnValue);
//                    System.out.println("获得列" + i + "的类型,返回SqlType中的编号:" + columnType);
//                    System.out.println("获得列" + i + "的数据类型名:" + columnTypeName);
//                    System.out.println("获得列" + i + "所在的Catalog名字:" + catalogName);
//                    System.out.println("获得列" + i + "对应数据类型的类:" + columnClassName);
//                    System.out.println("获得列" + i + "在数据库中类型的最大字符个数:" + columnDisplaySize);
//                    System.out.println("获得列" + i + "的默认的列的标题:" + columnLabel);
//                    System.out.println("获得列" + i + "的模式:" + schemaName);
//                    System.out.println("获得列" + i + "类型的精确度(类型的长度):" + precision);
//                    System.out.println("获得列" + i + "小数点后的位数:" + scale);
//                    System.out.println("获得列" + i + "对应的表名:" + tableNamea);
//                    System.out.println("获得列" + i + "是否自动递增:" + isAutoInctement);
//                    System.out.println("获得列" + i + "在数据库中是否为货币型:" + isCurrency);
//                    System.out.println("获得列" + i + "是否为空:" + isNullable);
//                    System.out.println("获得列" + i + "是否为只读:" + isReadOnly);
//                    System.out.println("获得列" + i + "能否出现在where中:" + isSearchable);
////                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        retu

    /**
     * 同一个mysql服务器下，复制一个存在的表的表结构到一个新表，不包括表数据,
     * CREATE TABLE newDBName.newTableName LIKE existDBName.existTableName
     *
     * @param existDBName 存在的表所在的库名
     * @param existTableName 存在的表表名
     * @param newDBName 待新建的表所在库名
     * @param newTableName   待新表表名
     */
    public boolean createTableFromExist(String newDBName, String newTableName,String existDBName, String existTableName) throws SQLException {
        logger.info("开始复制表 [" + existDBName+"."+existTableName + "] 表结构到新表 [" + newDBName+"."+newTableName + "] ");
        boolean result = false;
        StringBuffer sql = new StringBuffer();
        sql.append("create table ");
        sql.append(newDBName);
        sql.append(".");
        sql.append(newTableName);
        sql.append(" like ");
        sql.append(existDBName);
        sql.append(".");
        sql.append(existTableName);
        logger.info("执行sql :: " + sql.toString());
        pstmt = con.prepareStatement(sql.toString());
        result = pstmt.executeUpdate() > 0;
        if (result) {
            logger.info("复制完成！");
        }
        return result;
    }

    public void closeDB() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
                logger.info("mysql连接已释放");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // 使用示例
    public static void main(String[] args) {

        MySqlUtils db = MySqlUtils.getInstance("localhost", "3306", "oneiot", "root", "root");
        db.getConnection();

//        String sql = "SELECT * FROM product WHERE id IN(SELECT id from product where tenant_id = ?)";
//
//        List<Map<String, Object>> reslist = new ArrayList<>(); //结果集
//
//        List<Object> list = new ArrayList<Object>();//参数集合
//        list.add(1);

        try {
//            reslist = db.executeQuery(sql, list);
//            for (Map<String, Object> ac : reslist) {
//                System.out.println(ac);
//            }
//            db.getTableStructure("data_point");
            db.createTableFromExist("iot_guolu","zz", "oneiot","data_point");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
    }

}
