/**
 * Project Name:AccountManagementSystem
 * File Name:InAccountDAO.java
 * Package Name:ac.ucas.accountmanagement.dao
 * Date:2014-12-15上午11:41:26
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: InAccountDAO
 * Function: 收入数据访问对象
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ac.ucas.accountmanagement.model.*;;

public class InAccountDAO {

	private DBOpenHelper helper;			//创建DBOpenHelper对象
	private SQLiteDatabase db;				//创建SQLiteDatabase对象

	//定义构造函数
	public InAccountDAO(Context context) {
		helper = new DBOpenHelper(context);	//初始化DBOpenHelper对象
	}
	
	/**
	 * 添加收入信息
	 * @param tb_inaccount
	 */
	public void add(TableInAccount tb_inaccount) {
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		// 执行添加收入信息操作
		db.execSQL(
				"insert into tb_inaccount (userID,_id,money,time,type,handler,mark) values (?,?,?,?,?,?,?)",
				new Object[] {
						tb_inaccount.get_userID(), tb_inaccount.get_id(),
						tb_inaccount.getMoney(), tb_inaccount.getTime(),
						tb_inaccount.getType(), tb_inaccount.getHandler(),
						tb_inaccount.getMark() });
		db.close();
	}

	/**
	 * 更新收入信息
	 * @param tb_inaccount
	 */
	public void update(TableInAccount tb_inaccount) {
		db = helper.getWritableDatabase();	// 初始化SQLiteDatabase对象
		// 执行修改收入信息操作
		db.execSQL(
				"update tb_inaccount set money = ?,time = ?,type = ?,handler = ?,mark = ? where userID = ? and _id = ?",
				new Object[] { tb_inaccount.getMoney(), tb_inaccount.getTime(),
						tb_inaccount.getType(), tb_inaccount.getHandler(),
						tb_inaccount.getMark(), tb_inaccount.get_userID(),
						tb_inaccount.get_id() });
		db.close();
	}

	/**
	 * 查找收入信息
	 * @param id
	 * @return
	 */
	public TableInAccount find(String userId, int id) {
		db = helper.getWritableDatabase();	// 初始化SQLiteDatabase对象
		Cursor cursor = db
				.rawQuery(
						"select userID,_id,money,time,type,handler,mark from tb_inaccount where userID = ? and _id = ?",
						new String[] { userId, String.valueOf(id) });// 根据编号查找收入信息，并存储到Cursor类中
		db.close();
		// 遍历查找到的收入信息
		if (cursor.moveToNext()) {
			// 将遍历到的收入信息存储到Tb_inaccount类中
			return new TableInAccount(
					cursor.getString(cursor.getColumnIndex("userID")),
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getString(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("handler")),
					cursor.getString(cursor.getColumnIndex("mark")));
		}
		return null;// 如果没有信息，则返回null
	}

	/**
	 * 刪除收入信息
	 * @param ids
	 */
	public void detele(String userId, Integer id) {
		db = helper.getWritableDatabase();		// 初始化SQLiteDatabase对象
		// 执行删除收入信息操作
		db.execSQL("delete from tb_inaccount where userID = ? and _id = ?",
				new String [] {userId, String.valueOf(id) });
		db.close();
	}

	/**
	 * 获取收入信息
	 * @param start 起始位置
	 * @param count 每页显示数量
	 * @return
	 */
	public List<TableInAccount> getScrollData(String userId, int start, int count) {
		List<TableInAccount> tb_inaccount = new ArrayList<TableInAccount>();// 创建集合对象
		db = helper.getWritableDatabase();	// 初始化SQLiteDatabase对象
		// 获取所有收入信息
		Cursor cursor = db.rawQuery("select * from tb_inaccount where userID = ?  order by _id  limit ?,?",
				new String[] { userId, String.valueOf(start), String.valueOf(count) });
		// 遍历所有的收入信息
		while (cursor.moveToNext()) {
			// 将遍历到的收入信息添加到集合中
			tb_inaccount.add(new TableInAccount(
					cursor.getString(cursor.getColumnIndex("userID")),
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getString(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("handler")),
					cursor.getString(cursor.getColumnIndex("mark"))));
		}
		db.close();
		return tb_inaccount;// 返回集合
	}

	/**
	 * 获取总记录数
	 * @return
	 */
	public long getCount(String userId) {
		db = helper.getWritableDatabase();	// 初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select count(_id) from tb_inaccount where userID = ?",
				new String [] { userId });// 获取收入信息的记录数
		db.close();
		// 判断Cursor中是否有数据
		if (cursor.moveToNext()) {
			return cursor.getLong(0);// 返回总记录数
		}
		return 0;// 如果没有数据，则返回0
	}

	/**
	 * 获取收入最大编号
	 * @return
	 */
	public int getMaxId(String userId) {
		db = helper.getWritableDatabase();	// 初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select max(_id) from tb_inaccount where userID = ?",
				new String [] {userId });// 获取收入信息表中的最大编号
		db.close();
		// 访问Cursor中的最后一条数据
		while (cursor.moveToLast()) {
			return cursor.getInt(0);// 获取访问到的数据，即最大编号
		}
		return 0;// 如果没有数据，则返回0
	}
}