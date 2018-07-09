package com.example.dmitriy.listview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener,View.OnClickListener{


    public static final int ADD_REQUEST=1,EDIT_REQUEST=2;
    public static final String REQUES_CODE = "requestCode",EDITING_POINT="editingPoint";
    ListView list;
    ArticleAdapter adapter;
    public static final ArticleDB articleDB= new ArticleDB("articlesTable", "dbHeader",
            "dbDescription", "dbText","articlesDB",2);
    DBHelper dbHelper;
    DbManager dbManager;
    SQLiteDatabase db;
    ArrayList<Article> articles2;
    int editingPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.listView);
        dbHelper= new DBHelper(this,articleDB);
        dbManager= new DbManager(dbHelper);
        db = dbHelper.getWritableDatabase();
        articles2=dbManager.load(articleDB);
        /////
        adapter = new ArticleAdapter(this, R.layout.article_layout, articles2, getLayoutInflater());
        list.setAdapter(adapter);
        /////
        list.setOnItemClickListener(this);
        Log.d("myTag",db.getPath()+" Version:"+ String.valueOf(db.getVersion()));
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        showPopupMenu(view,i);

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra(REQUES_CODE,ADD_REQUEST);
        startActivityForResult(intent,ADD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case ADD_REQUEST:
                        String frow = data.getStringExtra(articleDB.firstRow);
                        String srow = data.getStringExtra(articleDB.secondRow);
                        String trow = data.getStringExtra(articleDB.thirdRow);
                        articles2.add(new Article(frow,srow,trow));
                        adapter.notifyDataSetChanged();
                        break;
                    case EDIT_REQUEST:
                        articles2.set(editingPosition, new Article(
                                data.getStringExtra(articleDB.firstRow),
                                data.getStringExtra(articleDB.secondRow),
                                data.getStringExtra(articleDB.thirdRow)

                        ));
                        adapter.notifyDataSetChanged();
                        break;

                }
            }
        }
    }
    void updateList(ArrayList<Article> atricle)
    {
        adapter = new ArticleAdapter(this, R.layout.article_layout, atricle, getLayoutInflater());
        list.setAdapter(adapter);

    }

    private void showPopupMenu(View v, final int irrr) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.pop_out_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.menuDel:
                                db.execSQL("DELETE FROM "+articleDB.name+" WHERE "+articleDB.firstRow+"='"+ articles2.get(irrr).headder+"';");
                                articles2.remove(irrr);
                                adapter.notifyDataSetChanged();
                                //updateList(articles2);
                                return true;
                            case R.id.menuEdit:
                                editingPosition=irrr;
                                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                                intent.putExtra(articleDB.firstRow, articles2.get(irrr).headder);
                                intent.putExtra(articleDB.secondRow, articles2.get(irrr).descriprion);
                                intent.putExtra(articleDB.thirdRow, articles2.get(irrr).text);
                                intent.putExtra(EDITING_POINT,articles2.get(irrr).text);
                                intent.putExtra(REQUES_CODE,EDIT_REQUEST);
                                startActivityForResult(intent,EDIT_REQUEST);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.show();
    }
}
