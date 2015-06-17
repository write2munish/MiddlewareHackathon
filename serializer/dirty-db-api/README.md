# DirtyDB API

DirtyDB API relys on environment variables to connect to its database. So it requires the following 3 environment variables

    export jdbc_url=jdbc:mysql://dirtydb.cdxtfiwyq2na.eu-west-1.rds.amazonaws.com:3306/changelog
    export jdbc_user=dirty_user
    export jdbc_password=dirty_password
    mvn exec:java
