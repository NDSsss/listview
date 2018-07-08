package com.example.dmitriy.listview;

public class ArticleDB {

    public String firstRow,secondRow,thirdRow,name,dbName;
    int version;

    public ArticleDB(String name, String firstRow, String secondRow, String thirdRow,String dbName, int version){
        this.name = name;
        this.firstRow=firstRow;
        this.secondRow = secondRow;
        this.thirdRow = thirdRow;
        this.version =version;
        this.dbName = dbName;

    }
}
