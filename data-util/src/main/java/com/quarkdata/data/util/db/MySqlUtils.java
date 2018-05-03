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
    private  String USERNAME = "root";
    // 定义数据库的密码
    private  String PASSWORD = "root";
    // 定义数据库的驱动信息
    private  String DRIVER = "com.mysql.jdbc.Driver";
    // 定义访问数据库的地址
    private  String URL = "jdbc:mysql://localhost:3306/db";

    private static MySqlUtils per = null;
    // 定义数据库的链接
    private Connection con = null;
    // 定义sql语句的执行对象
    private PreparedStatement pstmt = null;
    // 定义查询返回的结果集合
    private ResultSet resultSet = null;

    private MySqlUtils(String host,String port,String dbName,String userName,String password) {
        this.USERNAME = userName;
        this.PASSWORD = password;
        this.URL = "jdbc:mysql://"+host+":"+port+"/"+dbName+"?useUnicode=true&characterEncoding=utf-8";
    }

    /**
     * 单例模式，获得工具类的一个对象
     *
     * @return
     */
    public static MySqlUtils getInstance(String host,String port,String dbName,String userName,String password) {
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
        logger.info("连接mysql数据库成功! url == "+URL);
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
     * @param sql sql语句
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // 使用示例
    public static void main(String[] args) {

        MySqlUtils db = MySqlUtils.getInstance("localhost","3306","oneiot","root","root");
        db.getConnection();

        String sql = "SELECT * FROM product WHERE id IN(SELECT id from product where tenant_id = ?)";

        List<Map<String,Object>> reslist = new ArrayList<>(); //结果集

        List<Object> list = new ArrayList<Object>();//参数集合
        list.add(1);

        try {
            reslist = db.executeQuery(sql,list);
            for(Map<String,Object> ac:reslist){
                System.out.println(ac);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
    }

}
