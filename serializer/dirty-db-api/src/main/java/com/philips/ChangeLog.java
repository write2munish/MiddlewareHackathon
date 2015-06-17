package com.wipro;

import static spark.Spark.get;
import static spark.Spark.post;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.google.gson.Gson;

public class ChangeLog {

  static Gson gson = new Gson();
  
  static Sql2o sql2o;
  public static void main(String[] args) throws Exception {
    System.out.println(gson.toJson(Arrays.asList(
        new ChangeSet("PRD-HC781342","product","AA","en",new Timestamp(1434457386),12L),
        new ChangeSet("PRD-HC781342","product","DE","de",new Timestamp(1434457350),1L),
        new ChangeSet("FEA-100221","feature","AA","en",new Timestamp(1434457386),10L),
        new ChangeSet("unece.unit.ppm","unit","AA","en",new Timestamp(1434457344),20L)
        )));
    System.out.println(gson.toJson(new ChangeSet("PRD-HC781342","product","DE","de",new Timestamp(1434457350),1L)));
    Properties props = new Properties();
    props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
    sql2o = new Sql2o(props.getProperty("jdbc.url"),
        props.getProperty("jdbc.user"), props.getProperty("jdbc.password"));
    
    get("/list", "application/json", (req, res) -> {
      try (Connection conn = sql2o.open()) {
        List<ChangeSet> changesets = conn.createQuery("select * from dirty_list")
            .executeAndFetch(ChangeSet.class);
        return changesets;
      }
    }, new JsonTransformer());
    
    get("/list/:timestamp", "application/json", (req, res) -> {
      Timestamp ts = new Timestamp(Long.parseLong(req.params("timestamp")));
      try (Connection conn = sql2o.open()) {
        List<ChangeSet> changesets = conn.createQuery("select * from dirty_list where lastmodified > :timestamp")
            .addParameter("timestamp", ts)
            .executeAndFetch(ChangeSet.class);
        return changesets;
      }
    }, new JsonTransformer());
    
    post("/changelog", "application/json", (req, res) -> {
      ChangeSet object = gson.fromJson(req.body(), ChangeSet.class);
      try (Connection conn = sql2o.beginTransaction()) {
        conn.createQuery("insert into dirty_list(id, type, country, language, lastmodified, sequencenumber) VALUES (:id, :type, :country, :language, :lastmodified, :sequencenumber)")
        .addParameter("id", object.id)
        .addParameter("type", object.type)
        .addParameter("country", object.country)
        .addParameter("language", object.language)
        .addParameter("lastmodified", object.lastmodified)
        .addParameter("sequencenumber", object.sequencenumber)
        .executeUpdate();
        conn.commit();
        return object;
      }
    }, new JsonTransformer());
  }

}
