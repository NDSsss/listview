package com.example.dmitriy.listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    ArticleDB articleDB;
    final static String TEMPORARY_TABLE="temporaryTable";
    ContentValues cv;
    //SQLiteDatabase db;

    Article[] articles = {
            new Article("1head","1description","1text"),
            new Article("2head","2description","2text"),
            new Article("3head","3description","3text"),
            new Article("4head","4description","4text"),
            new Article("5head","5description","5text")
    };


    public DBHelper(Context context,ArticleDB articleDB) {
        // конструктор суперкласса
        super(context, articleDB.dbName, null, articleDB.version);
        this.articleDB=articleDB;
       // db = getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table "+articleDB.name+" ("
                + "id integer primary key autoincrement,"
                + articleDB.firstRow+" text,"
                + articleDB.secondRow+" text,"
                + articleDB.thirdRow+" text" + ");");
        addVoid(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("create table "+TEMPORARY_TABLE+" ("
                + "id integer primary key autoincrement,"
                + articleDB.firstRow+" text,"
                + articleDB.secondRow+" text,"
                + articleDB.thirdRow+" text);");

        Cursor cursor =db.query(articleDB.name, null, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            int headerColIndex = cursor.getColumnIndex(articleDB.firstRow);
            int descColIndex = cursor.getColumnIndex(articleDB.secondRow);
            int textColIndex = cursor.getColumnIndex(articleDB.thirdRow);
            ContentValues cv = new ContentValues();
            do {
                //articles2.add(new Article(cursor.getString(headerColIndex), cursor.getString(descColIndex), cursor.getString(textColIndex)));
                cv.put(articleDB.firstRow, cursor.getString(headerColIndex));
                cv.put(articleDB.secondRow, cursor.getString(descColIndex));
                cv.put(articleDB.thirdRow, cursor.getString(textColIndex));
                db.insert(TEMPORARY_TABLE, null, cv);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.execSQL("DROP TABLE "+articleDB.name+";");
        //ALTER TABLE temporaryTable TO articlesTable;
        //ALTER TABLE table2 RENAME TO table02;
        db.execSQL("ALTER TABLE "+TEMPORARY_TABLE+" RENAME TO "+articleDB.name+";");

    }

    public void addVoid(SQLiteDatabase db){
        for(int i =0;i<articles.length;i++)
        {
            ContentValues cv = new ContentValues();
            cv.put(articleDB.firstRow,articles[i].headder);
            cv.put(articleDB.secondRow,articles[i].descriprion);
            cv.put(articleDB.thirdRow,articles[i].text);
            db.insert(articleDB.name,null,cv);
        }
    }

}
