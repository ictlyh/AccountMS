/**
 * Project Name:AccountManagementSystem
 * File Name:FlagDAO.java
 * Package Name:ac.ucas.accountmanagement.dao
 * Date:2014-12-15下午12:09:38
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: FlagDAO
 * Function: 便签数据访问对象
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ac.ucas.accountmanagement.model.*;

public class FlagDAO {
	
	private DBOpenHelper helper;	//创建DBOpenHelper对象
	private SQLiteDatabase db;		//创建SQLiteDatabase对象

	//定义构造函数
	public FlagDAO(Context context) {
		helper = new DBOpenHelper(context);//初始化DBOpenHelper对象
	}

	/**
	 * 添加便签信息
	 * @param tb_flag
	 */
	public void add(TableFlag tb_flag) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		db.execSQL("insert into tb_flag (userID,_id,flag) values (?,?,?)",
				new Object[] { tb_flag.get_userID(), tb_flag.get_id(), tb_flag.getFlag() });//执行添加便签信息操作
	}

	/**
	 * 更新便签信息
	 * @param tb_flag
	 */
	public void update(TableFlag tb_flag) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		db.execSQL("update tb_flag set flag = ? where userID = ? and _id = ?",
				new Object[] { tb_flag.getFlag(), tb_flag.get_userID(), tb_flag.get_id() });//执行修改便签信息操作
	}

	/**
	 * 查找便签信息
	 * @param id
	 * @return
	 */
	public TableFlag find(String userId, int id) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select userID,_id,flag from tb_flag where userID = ? and_id = ?",
				new String[] { userId, String.valueOf(id) });//根据编号查找便签信息，并存储到Cursor类中
		//遍历查找到的便签信息
		if (cursor.moveToNext()) {
			//将遍历到的便签信息存储到Tb_flag类中
			return new TableFlag(
					cursor.getString(cursor.getColumnIndex("userID")),
					cursor.getColumnIndex("_id"),
					cursor.getString(cursor.getColumnIndex("flag")));
		}
		return null;//如果没有信息，则返回null
	}

	/**
	 * 刪除便签信息
	 * @param ids
	 */
	public void detele(String userId, Integer id) {
		db = helper.getWritableDatabase();		//创建SQLiteDatabase对象
		//执行删除便签信息操作
		db.execSQL("delete from tb_flag where userID = ? and _id = ?",
				new String [] {userId, String.valueOf(id) });
	}

	/**
	 * 获取便签信息
	 * @param start 起始位置
	 * @param count 每页显示数量
	 * @return
	 */
	public List<TableFlag> getScrollData(String userId, int start, int count) {
		List<TableFlag> lisTb_flags = new ArrayList<TableFlag>();//创建集合对象
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		//获取所有便签信息
		Cursor cursor = db.rawQuery("select * from tb_flag where userID = ? and limit ?,?",
				new String[] { userId, String.valueOf(start), String.valueOf(count) });
		//遍历所有的便签信息
		while (cursor.moveToNext()) {
			//将遍历到的便签信息添加到集合中
			lisTb_flags.add(new TableFlag(
					cursor.getString(cursor.getColumnIndex("userID")),
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("flag"))));
		}
		return lisTb_flags;//返回集合
	}

	/**
	 * 获取总记录数
	 * @return
	 */
	public long getCount(String userId) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select count(_id) from tb_flag where userID = ?",
				new String [] { userId });//获取便签信息的记录数
		//判断Cursor中是否有数据
		if (cursor.moveToNext()) {
			return cursor.getLong(0);//返回总记录数
		}
		return 0;//如果没有数据，则返回0
	}

	/**
	 * 获取便签最大编号
	 * @return
	 */
	public int getMaxId(String userId) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select max(_id) from tb_flag where userID = ?",
				new String [] { userId });//获取便签信息表中的最大编号
		//访问Cursor中的最后一条数据
		while (cursor.moveToLast()) {
			return cursor.getInt(0);//获取访问到的数据，即最大编号
		}
		return 0;//如果没有数据，则返回0
	}
}