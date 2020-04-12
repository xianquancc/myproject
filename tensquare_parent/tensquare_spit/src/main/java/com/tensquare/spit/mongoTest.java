package com.tensquare.spit;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class mongoTest {
    public static void main(String[] args) {
//        MongoClient client=new MongoClient("192.168.164.129:27917");
//        //创建连接
//        MongoDatabase spitdb = client.getDatabase("app");
//        //打开数据库
//        MongoCollection<Document> spit = spitdb.getCollection("test");
//        // 获取集合
//        FindIterable<Document> documents = spit.find();
//        //查询记录获取文档集 合
//        for(Document document:documents){
//            System.out.println(document);
//        }
//        client.close();
//        //关闭连接




        List<ServerAddress> adds = new ArrayList<>();
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("192.168.164.129", 27917);
        adds.add(serverAddress);

        List<MongoCredential> credentials = new ArrayList<>();
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("xianquan", "app", "root".toCharArray());
        credentials.add(mongoCredential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(adds, credentials);

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("app");

        MongoCollection<Document> spit = mongoDatabase.getCollection("test");
        // 获取集合
        FindIterable<Document> documents = spit.find();
        //查询记录获取文档集 合
        for(Document document:documents){
            System.out.println(document);
        }
        mongoClient.close();

        }
}
