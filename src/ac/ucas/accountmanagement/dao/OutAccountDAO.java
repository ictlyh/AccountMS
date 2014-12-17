/**
 * Project Name:AccountManagementSystem
 * File Name:OutAccountDAO.java
 * Package Name:ac.ucas.accountmanagement.dao
 * Date:2014-12-15上午11:58:43
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: OutAccountDAO
 * Function: 支出数据访问对象
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

public class OutAccountDAO {
	
	private DBOpenHelper helper;	//创建DBOpenHelper对象
	private SQLiteDatabase db;		//创建SQLiteDatabase对象

	//定义构造函数
	public OutAccountDAO(Context context) {
		helper = new DBOpenHelper(context);	//初始化DBOpenHelper对象
	}
	
	/**
	 * 添加支出信息
	 * @param tb_outaccount
	 */
	public void add(TableOutAccount tb_outaccount) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		//执行添加支出信息操作
		db.execSQL("insert into tb_outaccount (userID,_id,money,time,type,address,mark) values (?,?,?,?,?,?,?)",
				new Object[] {
				tb_outaccount.get_userID(), tb_outaccount.get_id(),
				tb_outaccount.getMoney(), tb_outaccount.getTime(),
				tb_outaccount.getType(), tb_outaccount.getAddress(),
				tb_outaccount.getMark() });
		db.close();
	}

	/**
	 * 更新支出信息
	 * @param tb_outaccount
	 */
	public void update(TableOutAccount tb_outaccount) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		//执行修改支出信息操作
		db.execSQL(
				"update tb_outaccount set money = ?,time = ?,type = ?,address = ?,mark = ? where userID = ? and _id = ?",
				new Object[] {
						tb_outaccount.getMoney(),tb_outaccount.getTime(),
						tb_outaccount.getType(), tb_outaccount.getAddress(),
						tb_outaccount.getMark(), tb_outaccount.get_userID(),
						tb_outaccount.get_id() });
		db.close();
	}

	/**
	 * 查找支出信息
	 * @param id
	 * @return
	 */
	public TableOutAccount find(String userId, int id) {
		db = helper.getWritableDatabase();//初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select userID,_id,money,time,type,address,mark from tb_outaccount where userID = ? and _id = ?",
						new String[] { userId, String.valueOf(id) });//根据编号查找支出信息，并存储到Cursor类中
		db.close();
		//遍历查找到的支出信息
		if (cursor.moveToNext()) {
			//将遍历到的支出信息存储到Tb_outaccount类中
			return new TableOutAccount(
					cursor.getString(cursor.getColumnIndex("userID")),
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getString(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("address")),
					cursor.getString(cursor.getColumnIndex("mark")));
		}
		return null;//如果没有信息，则返回null
	}

	/**
	 * 刪除支出信息
	 * @param ids
	 */
	public void detele(String userId, Integer id) {
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//执行删除支出信息操作
		db.execSQL("delete from tb_outaccount where userID = ? and _id = ?",
				new String [] {userId, String.valueOf(id)} );
		db.close();
	}

	/**
	 * 获取支出信息
	 * @param start 起始位置
	 * @param count 每页显示数量
	 * @return
	 */
	public List<TableOutAccount> getScrollData(String userId, int start, int count) {
		List<TableOutAccount> tb_outaccount = new ArrayList<TableOutAccount>();//创建集合对象
		db = helper.getWritableDatabase();//初始化SQLiteDatabase对象
		//获取所有支出信息
		Cursor cursor = db.rawQuery("select * from tb_outaccount where userID = ?  order by _id  limit ?,?",
				new String[] { userId, String.valueOf(start), String.valueOf(count) });
		db.close();
		//遍历所有的支出信息
		while (cursor.moveToNext()) {
			//将遍历到的支出信息添加到集合中
			tb_outaccount.add(new TableOutAccount(
					cursor.getString(cursor.getColumnIndex("userID")),
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getString(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("address")),
					cursor.getString(cursor.getColumnIndex("mark"))));
		}
		return tb_outaccount;//返回集合
	}

	/**
	 * 获取总记录数
	 * @return
	 */
	public long getCount(String userId) {
		db = helper.getWritableDatabase();//初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select count(_id) from tb_outaccount where userID = ?",
				new String [] { userId });//获取支出信息的记录数
		db.close();
		//判断Cursor中是否有数据
		if (cursor.moveToNext()) {
			return cursor.getLong(0);//返回总记录数
		}
		return 0;//如果没有数据，则返回0
	}

	/**
	 * 获取支出最大编号
	 * @return
	 */
	public int getMaxId(String userId) {
		db = helper.getWritableDatabase();//初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select max(_id) from tb_outaccount where userID = ?",
				new String [] { userId });// 获取支出信息表中的最大编号
		db.close();
		while (cursor.moveToLast()) {//访问Cursor中的最后一条数据
			return cursor.getInt(0);//获取访问到的数据，即最大编号
		}
		return 0;//如果没有数据，则返回0
	}
}