package com.quarkdata.data.util.db;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.quarkdata.data.util.PropertiesUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class MongoUtils {

    private static MongoUtils mongoUtils ;
    public static Map<String, String> props = PropertiesUtils.prop;
//    private String address;
//    static {
//        String address = props.get("mongo.host");
//        Integer port = Integer.getInteger(props.get("mongo.port"));
//
//    }
    // mongoDB服务器
//    @Value("${mongo.host}")
//    private String address;
//
//    // mongoDB端口
//    @Value("${mongo.port}")
//    private int port;
//
//    // mongoDB库名称
//    @Value("${mongo.dbname}")
//    private String dbName;
//
//    // 连接池设置为20个连接,默认为100
//    @Value("${mongo.options.connections-per-host}")
//    private int connectionsPerHost;
//
//    // 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误
//    @Value("${mongo.options.threads-allowed-to-block-for-connection-multiplier}")
//    private int multiplier;
//
//    // 连接超时
//    @Value("${mongo.options.connect-timeout}")
//    private int connectTimeout;
//
//    @Value("${mongo.options.max-wait-time}")
//    private int maxWaitTime;
//
//    @Value("${mongo.options.socket-keep-alive}")
//    private int socketKeepAlive;
//
//    // 套接字超时时间，0无限制
//    @Value("${mongo.options.socket-timeout}")
//    private int socketTimeout;

    private String collection; // mongo表名


    MongoClient mongoClient ;
    MongoDatabase mongoDatabase ;
    MongoCollection<Document> mongoCol ;

    private void getMongoCon() {

        String address = props.get("mongo.host");
        Integer port = Integer.getInteger(props.get("mongo.port"));

//        MongoClientOptions.Builder options = new MongoClientOptions.Builder(); // mongoDB配置
//        options.connectionsPerHost(props.get("mongo.options.connections-per-host"));
//        options.connectTimeout(connectTimeout);
//        options.maxWaitTime(maxWaitTime);
//        options.socketTimeout(socketTimeout);
//        options.threadsAllowedToBlockForConnectionMultiplier(multiplier);
//        options.writeConcern(WriteConcern.SAFE);

        // 分布式mongo服务器 使用方式
//        List<ServerAddress> listHost = Arrays.asList(new ServerAddress("localhost", 27017),new ServerAddress("localhost", 27018));
//        mongoClient = new MongoClient(listHost,options.build());

        ServerAddress serverAddress = new ServerAddress(address,port); // 单节点mongoDB服务器
//        mongoClient = new MongoClient(serverAddress,options.build());
//        mongoDatabase = mongoClient.getDatabase(dbName);
    }

    //单利
    public static MongoUtils getInstance(){
        if (mongoUtils!=null) {
            return mongoUtils;
        }else {
            return new MongoUtils();
        }

    }

    /**
     * 根据地址，数据库及连接名称连接到数据库
     *
     */
    public MongoUtils() {
        //实例化
        getMongoCon();
    }


    // ------------------------------------共用方法---------------------------------------------------
    /**
     * 获取DB实例 - 指定DB
     *
     * @param dbName
     * @return
     */
    public MongoDatabase getDB(String dbName) {
        if (dbName != null && !"".equals(dbName)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            return database;
        }
        return null;
    }

    /**
     * 获取collection对象 - 指定Collection
     *
     * @param collName
     * @return
     */
    public MongoCollection<Document> getCollection(String dbName, String collName) {
        if (null == collName || "".equals(collName)) {
            return null;
        }
        if (null == dbName || "".equals(dbName)) {
            return null;
        }
        MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
        return collection;
    }

    /**
     * 查询DB下的所有表名
     */
    public List<String> getAllCollections(String dbName) {
        MongoIterable<String> colls = getDB(dbName).listCollectionNames();
        List<String> _list = new ArrayList<String>();
        for (String s : colls) {
            _list.add(s);
        }
        return _list;
    }

    /**
     * 获取所有数据库名称列表
     *
     * @return
     */
    public MongoIterable<String> getAllDBNames() {
        MongoIterable<String> s = mongoClient.listDatabaseNames();
        return s;
    }

    /**
     * 删除一个数据库
     */
    public void dropDB(String dbName) {
        getDB(dbName).drop();
    }

    /**
     * 查找对象 - 根据主键_id
     *
     * @param
     * @param id
     * @return
     */
    public Document findById(MongoCollection<Document> coll, String id) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
        Document myDoc = coll.find(Filters.eq("_id", _idobj)).first();
        return myDoc;
    }

    /** 统计数 */
    public int getCount(MongoCollection<Document> coll) {
        int count = (int) coll.count();
        return count;
    }

    /** 条件查询 */
    public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
        return coll.find(filter).iterator();
    }

    /** 分页查询 */
    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);
        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
    }

    /**
     * 通过ID删除
     *
     * @param coll
     * @param id
     * @return
     */
    public int deleteById(MongoCollection<Document> coll, String id) {
        int count = 0;
        ObjectId _id = null;
        try {
            _id = new ObjectId(id);
        } catch (Exception e) {
            return 0;
        }
        Bson filter = Filters.eq("_id", _id);
        DeleteResult deleteResult = coll.deleteOne(filter);
        count = (int) deleteResult.getDeletedCount();
        return count;
    }

    /**
     * FIXME
     *
     * @param coll
     * @param id
     * @param newdoc
     * @return
     */
    public Document updateById(MongoCollection<Document> coll, String id, Document newdoc) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
        Bson filter = Filters.eq("_id", _idobj);
        // coll.replaceOne(filter, newdoc); // 完全替代
        coll.updateOne(filter, new Document("$set", newdoc));
        return newdoc;
    }

    public void dropCollection(String dbName, String collName) {
        getDB(dbName).getCollection(collName).drop();
    }

    /**
     * 关闭Mongodb
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

     public static void main(String[] args) {
        MongoUtils mongoUtils = MongoUtils.getInstance();
        System.out.print(mongoUtils.getAllDBNames());
     }

}
