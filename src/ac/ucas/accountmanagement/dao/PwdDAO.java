/**
 * Project Name:AccountManagementSystem
 * File Name:PwdDAO.java
 * Package Name:ac.ucas.accountmanagement.dao
 * Date:2014-12-15下午12:17:03
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: PwdDAO
 * Function: 密码数据访问对象
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ac.ucas.accountmanagement.model.*;

public class PwdDAO {

	private DBOpenHelper helper;	//创建DBOpenHelper对象
	private SQLiteDatabase db;		//创建SQLiteDatabase对象

	//定义构造函数
	public PwdDAO(Context context) {
		helper = new DBOpenHelper(context);//初始化DBOpenHelper对象
	}

	/**
	 * 添加密码信息
	 * @param tb_pwd
	 */
	public void add(TablePassword tb_pwd) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		// 执行添加密码操作
		db.execSQL("insert into tb_pwd (_id,password) values (?,?)",
				new Object[] { tb_pwd.getUserID(), tb_pwd.getPassword() });
	}

	/**
	 * 设置密码信息
	 * @param tb_pwd
	 */
	public void update(TablePassword tb_pwd) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		//执行修改密码操作
		db.execSQL("update tb_pwd set password = ? where _id = ?",
				new Object[] { tb_pwd.getPassword(), tb_pwd.getUserID() });
	}

	/**
	 * 查找密码信息
	 * @return
	 */
	public TablePassword find(String userID) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		//查找密码并存储到Cursor类中
		Cursor cursor = db.rawQuery("select _id,password from tb_pwd where _id = ?", new String[] { userID });
		//遍历查找到的密码信息
		if (cursor.moveToNext()) {
			// 将密码存储到TablePassword类中
			return new TablePassword(
					cursor.getColumnName(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("password")));
		}
		return null;//如果没有信息，则返回null
	}

	/**
	 * 获取总记录数
	 * @return
	 */
	public long getCount() {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select count(password) from tb_pwd", null);//获取密码信息的记录数
		//判断Cursor中是否有数据
		if (cursor.moveToNext()) {
			return cursor.getLong(0);//返回总记录数
		}
		return 0;//如果没有数据，则返回0
	}
}