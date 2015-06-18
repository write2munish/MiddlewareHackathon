package com.philips;

import static spark.Spark.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.google.gson.Gson;

public class ChangeLog {

  static Gson gson = new Gson();
  
  static Sql2o sql2o;
  public static void main(String[] args) throws Exception {
    Arrays.asList("port", "jdbc_url", "jdbc_user", "jdbc_password").forEach( s -> {
     if ( System.getenv(s) == null ) {
       System.err.println("Require " + s + " to be set from the environment");
       System.exit(1);
     }
    });
    port(Integer.parseInt(System.getenv("port")));
    sql2o = new Sql2o(System.getenv("jdbc_url"),
        System.getenv("jdbc_user"), System.getenv("jdbc_password"));
    
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
    
    get("/logchange", (req, res) -> {
      res.status(400);
      return "Sorry only post supported";
    });
    
    post("/logchange", "application/json", (req, res) -> {
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
