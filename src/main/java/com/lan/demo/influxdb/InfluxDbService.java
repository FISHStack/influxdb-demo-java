package com.lan.demo.influxdb;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BoundParameterQuery.QueryBuilder;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Series;
import org.influxdb.impl.InfluxDBResultMapper;

/**
 * @author yunhorn lyp
 * @date 2019/9/13下午6:25
 */
public class InfluxDbService {

  public static void main(String[] args) {
    InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.1.11:8086", "root", "root");
    influxDB.query(new Query("CREATE DATABASE mydb2"));
    influxDB.setDatabase("mydb2");
    Point point = Point
        .measurement("cpu")
        .tag("atag", "test")
        .addField("idle", 90L)
        .addField("usertime", 9L)
        .addField("system", 1L)
        .build();
    influxDB.write(point);

    Query query = QueryBuilder.newQuery("SELECT * FROM cpu WHERE atag = $atag")
        .bind("atag", "test")
        .create();
    QueryResult result = influxDB.query(query);
    System.out.println(result);
//    Series series = result.getResults().get(0).getSeries().get(0);
//    System.out.println(series);

//    influxDB.query(new Query("DROP DATABASE mydb2"));
  }
}
