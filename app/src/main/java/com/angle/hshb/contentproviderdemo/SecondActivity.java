package com.angle.hshb.contentproviderdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * 自己的应用的内容提供者
 */
public class SecondActivity extends AppCompatActivity {
    public static final String AUTHORITY = "com.angle.hshb.sqliteopenhelperdemo.provider";
    private String newId ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    /**
     * 添加数据
     * @param view
     */
    public void addData(View view) {
        Uri uri = Uri.parse("content://"+AUTHORITY+"/book");
        ContentValues values = new ContentValues();
        values.put("name","A Clash of Kings");
        values.put("author","George Martin");
        values.put("pages",1040);
        values.put("price",22.85);
        Uri newUri = getContentResolver().insert(uri,values);
        newId = newUri.getPathSegments().get(1);
    }

    /**
     * 查询数据
     * @param view
     */
    public void queryData(View view) {
        Uri uri = Uri.parse("content://"+AUTHORITY+"/book");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int page = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.i("SecondActivity","book name is :"+name+"\n"+
                "book author is :"+author+"\n"+
                "book pages is :"+page+"\n"+
                "book price is :"+price);
            }
            cursor.close();
        }
    }

    /**
     * 更新数据
     * @param view
     */
    public void updateData(View view) {
        Uri uri = Uri.parse("content://"+AUTHORITY+"/book/"+newId);
        ContentValues values = new ContentValues();
        values.put("name","A Storm of Swords");
        values.put("pages",1216);
        values.put("price",24.05);
        getContentResolver().update(uri,values,null,null);
    }

    /**
     * 删除数据
     * @param view
     */
    public void deleteData(View view) {
        Uri uri = Uri.parse("content://"+AUTHORITY+"/book/"+newId);
        getContentResolver().delete(uri,null,null);
    }
}
