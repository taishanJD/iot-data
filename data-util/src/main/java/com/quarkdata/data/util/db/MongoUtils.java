package com.quarkdata.data.util.db;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.quarkdata.data.util.PropertiesUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class MongoUtils {

    private static Logger logger = Logger.getLogger(MongoUtils.class);
    private static MongoUtils mongoUtils ;
    public static Map<String, String> props = PropertiesUtils.prop;

    @Autowired
    private MongoOperations mongoOperations;

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
        logger.info("开始连接mongodb");
        String address = props.get("mongo.host");
        Integer port = Integer.parseInt(props.get("mongo.port"));

        MongoClientOptions.Builder options = new MongoClientOptions.Builder(); // mongoDB配置
        options.connectionsPerHost(Integer.parseInt(props.get("mongo.options.connections-per-host")));
        options.connectTimeout(Integer.parseInt(props.get("mongo.options.connect-timeout")));
        options.maxWaitTime(Integer.parseInt(props.get("mongo.options.max-wait-time")));
        options.socketTimeout(Integer.parseInt(props.get("mongo.options.socket-timeout")));
        options.threadsAllowedToBlockForConnectionMultiplier(Integer.parseInt(props.get("mongo.options.threads-allowed-to-block-for-connection-multiplier")));
        options.writeConcern(WriteConcern.SAFE);

        // 分布式mongo服务器 使用方式
//        List<ServerAddress> listHost = Arrays.asList(new ServerAddress("localhost", 27017),new ServerAddress("localhost", 27018));
//        mongoClient = new MongoClient(listHost,options.build());
//        logger.info("获取mongodb连接成功！url == "+mongoClient.getAllAddress());

        ServerAddress serverAddress = new ServerAddress(address,port); // 单节点mongoDB服务器
        mongoClient = new MongoClient(serverAddress,options.build());
        logger.info("获取mongodb连接成功！url == "+mongoClient.getAddress());
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
     * 插入数据
     * @param dbName 指定数据库名
     * @param collectionName 指定collection名
     * @param datas 待插入的数据
     * @return
     */
    public void insertData(String dbName,String collectionName,List<Map<String,Object>> datas){

        List<Document> list = new ArrayList<>();

        //使用工具类，获取到指定数据库的MongoCollection对象
        MongoCollection mongoCollection = getCollection(dbName,collectionName);

        for (Map<String,Object> data:datas
             ) {
            Document document = new Document(data);
            list.add(document);
        }
        mongoCollection.insertMany(list);
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

    /** 分页查询,单一条件 */
    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);
        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
    }

    /**
     * 分页查询,多条件
     */
    public MongoCursor<Document> aggregateByPage(MongoCollection<Document> coll, List<Bson> filters, int pageNo, int pageSize) {
//        Bson orderBy = new BasicDBObject("_id", 1);
//        Bson sort = new BasicDBObject("$sort",orderBy);
//        Bson skip = new BasicDBObject("$skip",pageNo);
//        Bson limit = new BasicDBObject("$limit",pageSize);

//        filters.add(sort);
//        filters.add(skip);
//        filters.add(limit);

//        FindIterable<Document> iterable = (FindIterable<Document>) coll.aggregate(filters);
//        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
        //match
//        Bson[] array = {
//                new BasicDBObject("age", new BasicDBObject("$lt",30)),
//                new BasicDBObject("firstName", "/J/")
//        };

        Pattern pattern = Pattern.compile("^J", CASE_INSENSITIVE);
        BasicDBObject cond = new BasicDBObject();
        cond.put("age", new BasicDBObject("$lt",30));
        cond.put("firstName", pattern);
        Bson match = new BasicDBObject("$match", cond);

        //group
//        DBObject groupFields = new BasicDBObject( "_id", "$lastEvent");
//        groupFields.put("count", new BasicDBObject( "$sum", 1));
//        DBObject group = new BasicDBObject("$group", groupFields);

        //sort
        Bson sort = new BasicDBObject("$sort", new BasicDBObject("_id", 1));
        //skip
        Bson skip = new BasicDBObject("$skip", pageNo);
        //limit
        Bson limit = new BasicDBObject("$limit", pageSize);

        filters.add(match);
        filters.add(sort);
        filters.add(skip);
        filters.add(limit);

        return coll.aggregate(filters).iterator();
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
     *
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
//        MongoIterable<String> cols = mongoUtils.getAllDBNames();
//        for (String s : cols) {
////            _list.add(s);
//            System.out.println(s);
//        }

//        List<String> collections = mongoUtils.getAllCollections("School");
//        for (String s : collections) {
////            _list.add(s);
//            System.out.println(s);
//        }
//         Bson filter1 = Filters.lt("age",30); //过滤age小于30的记录
//         Bson filter2 = Filters.regex("firstName","/^J/");
         List<Bson> filters = new ArrayList<>();
//         filters.add(filter1);
//         filters.add(filter2);
//         MongoCursor<Document> cursor = mongoUtils.findByPage(mongoUtils.getCollection("School","user"),filters,1,10);
//         while (cursor.hasNext()){
//             Document object = cursor.next();
//             logger.info(object.toJson());
//             //do something.
//         }

         MongoCursor<Document> cursor = mongoUtils.aggregateByPage(mongoUtils.getCollection("School","user"),filters,1,10);
         while (cursor.hasNext()){
             Document object = cursor.next();
             logger.info(object.toJson());
             //do something.
         }

     }

}
