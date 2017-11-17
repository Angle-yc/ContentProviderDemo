package com.angle.hshb.contentproviderdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int CALL_CODE = 1;
    public static final int CONTACTS_CODE = 2;
    ArrayAdapter<String> adapter;
    List<String> contactsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView contactsView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contactsList);
        contactsView.setAdapter(adapter);
        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toCall(contactsList.get(position).split("\\n")[1]);
            }
        });
        findViewById(R.id.iv_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
    }

    /**
     * 获取读取手机联系人权限
     * @param view
     */
    public void readContacts(View view) {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},CONTACTS_CODE);
        }else {
            readContacts();
        }
    }
    /**
     * 读取联系人
     */
    private void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if (cursor != null){
                while (cursor.moveToNext()){
                    //获取联系人姓名
                    String disPlayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取联系人手机号
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(disPlayName+"\n"+number);
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
        }
    }

    /**
     * 获取拨打电话的权限
     * @param number
     */
    private void toCall(String number) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},CALL_CODE);
        }else {
            call(number);
        }
    }

    /**
     * 拨打电话
     * @param number
     */
    private void call(String number) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number)));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CALL_CODE:
                if (grantResults.length >0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "权限获取成功,现在可以拨打电话了", Toast.LENGTH_SHORT).show();;
                }else {
                    Toast.makeText(this, "You denid the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case CONTACTS_CODE:
                if (grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else {
                    Toast.makeText(this, "You denid the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

}
