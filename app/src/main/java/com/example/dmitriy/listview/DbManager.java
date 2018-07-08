package com.example.dmitriy.listview;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DbManager {
    //DBHelper dbHelper;
    SQLiteDatabase db;
    public DbManager(DBHelper dbHelper){
        db=dbHelper.getWritableDatabase();
    }

    public void add(String header,String desc,String text,ArticleDB articleDB){
        ContentValues cv = new ContentValues();
        cv.put(articleDB.firstRow, header);
        cv.put(articleDB.secondRow, desc);
        cv.put(articleDB.thirdRow, text);

        db.insert(articleDB.name, null, cv);
    }
    public void edit(String header,String desc,String text,ArticleDB articleDB,String preChangeText){

        db.execSQL("UPDATE "+articleDB.name+" SET "+articleDB.firstRow
                +"='"+header+"'"
                +" WHERE "+articleDB.thirdRow +" ='"
                +preChangeText+"';");
        db.execSQL("UPDATE "+articleDB.name+" SET "+articleDB.secondRow
                +"='"+desc+"'"
                +" WHERE "+articleDB.thirdRow +" ='"
                +preChangeText+"';");
        db.execSQL("UPDATE "+articleDB.name+" SET "+articleDB.thirdRow
                +"='"+text+"'"
                +" WHERE "+articleDB.thirdRow +" ='"
                +preChangeText+"';");
    }
    public ArrayList<Article> load(ArticleDB articleDB){
        ArrayList<Article> articles2 = new ArrayList<Article>();
        Cursor cursor =db.query(articleDB.name, null, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            int headerColIndex = cursor.getColumnIndex(articleDB.firstRow);
            int descColIndex = cursor.getColumnIndex(articleDB.secondRow);
            int textColIndex = cursor.getColumnIndex(articleDB.thirdRow);
            do {
                articles2.add(new Article(cursor.getString(headerColIndex), cursor.getString(descColIndex), cursor.getString(textColIndex)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return articles2;
    }
}
