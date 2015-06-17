# DirtyDB API

DirtyDB API relys on environment variables to connect to its database. So it requires the following 3 environment variables

    export jdbc_url=jdbc:mysql://dirtydb.cdxtfiwyq2na.eu-west-1.rds.amazonaws.com:3306/changelog
    export jdbc_user=dirty_user
    export jdbc_password=dirty_password
    mvn exec:java

#Docker Commands

    docker build -t dirtydb .

    docker run -e jdbc_url="jdbc:mysql://dirtydb.cdxtfiwyq2na.eu-west-1.rds.amazonaws.com:3306/changelog" -e jdbc_user="dirty_user" -e jdbc_password="dirty_password" -p 4567:9999 dirtydb
