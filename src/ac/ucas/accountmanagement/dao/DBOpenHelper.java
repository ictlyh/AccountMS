/**
 * Project Name:AccountManagementSystem
 * File Name:DBOpenHelper.java
 * Package Name:ac.ucas.accountmanagement.dao
 * Date:2014-12-15上午11:01:42
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: DBOpenHelper
 * Function: 用来实现创建数据库，数据表等功能
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	
	private static final int VERSION = 1;				//定义数据库版本号
	private static final String DBNAME = "account.db";	//定义数据库名称
	
	//定义构造函数
	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}
	
	// 创建数据库
	@Override
	public void onCreate(SQLiteDatabase db){
	
		db.execSQL("create table tb_outaccount (userID varchar(20), _id integer, money decimal, time varchar(10),"
				+ "type varchar(10), address varchar(100), mark varchar(200),"
				+ "primary key(userID, _id))");// 创建支出信息表
		db.execSQL("create table tb_inaccount (userID varchar(20), _id integer, money decimal, time varchar(10),"
				+ "type varchar(10),handler varchar(100),mark varchar(200),"
				+ "primary key(userID, _id))");// 创建收入信息表
		db.execSQL("create table tb_pwd (_id varchar(20) primary key,password varchar(20))");// 创建密码表
		db.execSQL("create table tb_flag (userID varchar(20), _id integer, flag varchar(200),"
				+ "primary key(userID, _id))");// 创建便签信息表
	}

	// 覆写基类的onUpgrade方法，以便数据库版本更新
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}
}