package com.example.tensquare_user.Util;
import java.sql.*;
import java.util.*;

public class jdbcMethod {
    public static void main(String[] args) {
        jdbcMethod method=new jdbcMethod();
        method.readMysql();
    }
    private void readMysql(){
        try {
            Connection conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fsshunde?characterEncoding=UTF8", "root", "root");
            String sql="select * from foshan_data where foshan_fold_id=20275;";
            PreparedStatement pre=conn.prepareStatement(sql);
            ResultSet res=pre.executeQuery();
            List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
            HashMap<String,Object> map=null;
            int j=1;
//            while (res.next()){
//                System.out.println(j);
//                j++;
//                System.out.println(res.getString("title"));
//            }
        //    System.out.println(list);

            while (res.next()) {
                j++;
                map=new HashMap<String, Object>();
                ResultSetMetaData rsMeta=res.getMetaData();
               // System.out.println(rsMeta);
                int columnCount=rsMeta.getColumnCount();
               // System.out.println(columnCount);
                for (int i=1; i<=columnCount; i++) {
                    map.put(rsMeta.getColumnLabel(i),res.getObject(i));
                }
                list.add(map);
            }

            for (Map<String,Object> map2:list){

                for (String key:map2.keySet()){
                    System.out.println(key+"--"+map2.get(key));
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

        }
    }
}
