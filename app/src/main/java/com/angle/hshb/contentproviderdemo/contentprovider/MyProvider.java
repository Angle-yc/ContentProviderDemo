package com.angle.hshb.contentproviderdemo.contentprovider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 自己的内容提供者
 */

public class MyProvider extends ContentProvider {

    public static final int TABLE1_DIR = 0;

    public static final int TABLE1_ITEM = 1;

    public static final int TABLE2_DIR = 2;

    public static final int TABLE2_ITEM = 3;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.angle.hshb.contentproviderdemo","table1",TABLE1_DIR);
        uriMatcher.addURI("com.angle.hshb.contentproviderdemo","table1/#",TABLE1_ITEM);
        uriMatcher.addURI("com.angle.hshb.contentproviderdemo","table2",TABLE2_DIR);
        uriMatcher.addURI("com.angle.hshb.contentproviderdemo","table2/#",TABLE2_ITEM);
    }

    /**
     * 初始化内容提供器的时候调用
     * 通常在这里完成对数据库的创建和升级操作
     * 返回True表示内容提供者初始化成功
     * False表示失败
     * 注：只有当存在ContentResolver尝试访问我们程序中的数据时，内容提供器才会被初始化
     * @return
     */
    @Override
    public boolean onCreate() {
        return false;
    }

    /**
     * 从内容提供器中查询数据
     * @param uri  确定查询哪张表
     * @param projection  确定查询哪些列
     * @param selection   用于约束查询哪些行
     * @param selectionArgs  用于约束查询哪些行
     * @param sortOrder  对结果进行排序
     * @return  Cursor对象
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                //查询table1表中所有数据
                break;
            case TABLE1_ITEM:
                //查询table1表中单条数据
                break;
            case TABLE2_DIR:
                //查询table2表中所有数据
                break;
            case TABLE2_ITEM:
                //查询table2表中单条数据
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * 根据传入的URI来返回相应的MIME类型
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.angle.hshb.contentproviderdemo.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.angle.hshb.contentproviderdemo.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.angle.hshb.contentproviderdemo.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.item/vnd.com.angle.hshb.contentproviderdemo.table2";
            default:
                break;
        }
        return null;
    }

    /**
     * 向内容提供器中添加数据
     * @param uri  确定添加到的表
     * @param values  待添加的数据保存在values中
     * @return  返回一个用于表示这条新纪录的URL
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    /**
     * 从内容提供器中删除数据
     * @param uri  确定删除哪一张表中的数据
     * @param selection   约束删除哪些行
     * @param selectionArgs   约束删除哪些行
     * @return  返回被删除的行数
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * 更新内容提供者中已有的数据
     * @param uri  确定更新哪张表的数据
     * @param values  新数据保存在Values
     * @param selection   约束更新哪些行
     * @param selectionArgs  约束更新哪些行
     * @return   返回受影响的行数
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
