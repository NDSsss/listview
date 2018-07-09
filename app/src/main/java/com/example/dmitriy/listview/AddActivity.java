package com.example.dmitriy.listview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    EditText header,desc,text;
    SQLiteDatabase db;
    DBHelper dbHelper;
    DbManager dbManager;
    ArticleDB articleDB;
    static final String ERROR_MSG="You have empty fields",BUTTON_TEXT_EDIT="Edit";
    int requestCode;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        header=(EditText)findViewById(R.id.editTextHeader);
        desc=(EditText)findViewById(R.id.editTextDescription);
        text=(EditText)findViewById(R.id.editTextText);
        button = (Button)findViewById(R.id.buttonConfirm);
        articleDB=MainActivity.articleDB;
        dbHelper = new DBHelper(this,articleDB);
        dbManager = new DbManager(dbHelper);
        header.setText(getIntent().getStringExtra(articleDB.firstRow));
        desc.setText(getIntent().getStringExtra(articleDB.secondRow));
        text.setText(getIntent().getStringExtra(articleDB.thirdRow));
        requestCode = getIntent().getIntExtra(MainActivity.REQUES_CODE, 0);
        if(requestCode==MainActivity.EDIT_REQUEST)
            button.setText(BUTTON_TEXT_EDIT);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonConfirm:
            String headerText = header.getText().toString();
            String descText = desc.getText().toString();
            String textText = text.getText().toString();
            if (headerText.equals("") || descText.equals("") || textText.equals("")) {
                Toast.makeText(getApplicationContext(), ERROR_MSG, Toast.LENGTH_LONG).show();
            } else {
                if (requestCode == MainActivity.ADD_REQUEST) {

                    dbManager.add(headerText, descText, textText, articleDB);
                } else if (requestCode == MainActivity.EDIT_REQUEST) {
                    dbManager.edit(headerText, descText, textText, articleDB, getIntent().getStringExtra(articleDB.thirdRow));
                }
                Intent intent = new Intent();
                intent.putExtra(articleDB.firstRow, header.getText().toString());
                intent.putExtra(articleDB.secondRow, desc.getText().toString());
                intent.putExtra(articleDB.thirdRow, text.getText().toString());
                intent.putExtra(MainActivity.EDITING_POINT, getIntent().getStringExtra(articleDB.thirdRow));
                setResult(RESULT_OK, intent);
                finish();

            }
                break;
            case R.id.buttonCancel:
                setResult(RESULT_CANCELED);
                finish();
        }

    }



}
