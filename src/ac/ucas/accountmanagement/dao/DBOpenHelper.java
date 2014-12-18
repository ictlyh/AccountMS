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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ac.ucas.accountmanagement.model.TableFlag;
import ac.ucas.accountmanagement.model.TableInAccount;
import ac.ucas.accountmanagement.model.TableOutAccount;

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
	
		db.execSQL("create table if not exists tb_outaccount (userID varchar(20), _id integer, money decimal,"
				+ "time varchar(10), type varchar(10), address varchar(100), mark varchar(200),"
				+ "primary key(userID, _id))");// 创建支出信息表
		db.execSQL("create table  if not exists tb_inaccount (userID varchar(20), _id integer, money decimal,"
				+ "time varchar(10), type varchar(10),handler varchar(100),mark varchar(200),"
				+ "primary key(userID, _id))");// 创建收入信息表
		db.execSQL("create table  if not exists tb_pwd (_id varchar(20) primary key,password varchar(20))");// 创建密码表
		db.execSQL("create table  if not exists tb_flag (userID varchar(20), _id integer, flag varchar(200),"
				+ "primary key(userID, _id))");// 创建便签信息表
	}

	// 覆写基类的onUpgrade方法，以便数据库版本更新
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists tb_inaccount");
		db.execSQL("drop table if exists tb_outaccount");
		db.execSQL("drop table if exists tb_flag");
		onCreate(db);
	}
	
	//导出数据库到文件
	public void exportToFile(String userId, String filePath) {
		String line = null;
		String space = ",";
		//Log.d("DBOpenHelper exportToFile", "filePath = " + filePath);
		File file = new File(filePath);
		//文件不存在
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Log.d("DBOpenHelper exportToFile", "create file " + filePath + " success");
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		/*if(bw == null) {
			Log.e("DBOpenHelper exportToFile", "bw = null int line 88");
		}*/
		
		SQLiteDatabase db = getWritableDatabase();	// 初始化SQLiteDatabase对象
		
		// 导出收入信息
		List<TableInAccount> tb_inaccount = new ArrayList<TableInAccount>();// 创建集合对象
		// 获取所有收入信息
		Cursor cursor = db.rawQuery("select * from tb_inaccount where userID = ?  order by _id", new String[] { userId });
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
		// 记录数目
		line = String.valueOf(tb_inaccount.size());
		//Log.d("DBOpenHelper exportToFile", "line = " + line);
		try {
			bw.write(line, 0, line.length());
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<TableInAccount> inIte = tb_inaccount.iterator();
		// 写记录到文件
		while(inIte.hasNext()) {
			TableInAccount tmp = inIte.next();
			line = tmp.get_userID();
			line += space;
			line += tmp.get_id();
			line += space;
			line += tmp.getMoney();
			line += space;
			line += tmp.getTime();
			line += space;
			line += tmp.getType();
			line += space;
			line += tmp.getHandler();
			line += space;
			line += tmp.getMark();
			line += space;
			//Log.d("DBOpenHelper exportToFile", "line = " + line);
			try {
				bw.write(line, 0, line.length());
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 导出支出信息
		List<TableOutAccount> tb_outaccount = new ArrayList<TableOutAccount>();// 创建集合对象
		// 获取所有支出信息
		cursor = db.rawQuery("select * from tb_outaccount where userID = ?  order by _id", new String[] { userId });
		// 遍历所有的支出信息
		while (cursor.moveToNext()) {
			// 将遍历到的收入信息添加到集合中
			tb_outaccount.add(new TableOutAccount(
					cursor.getString(cursor.getColumnIndex("userID")),
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getString(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("address")),
					cursor.getString(cursor.getColumnIndex("mark"))));
		}
		// 记录数目
		line = String.valueOf(tb_outaccount.size());
		//Log.d("DBOpenHelper exportToFile", "line = " + line);
		try {
			bw.write(line, 0, line.length());
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<TableOutAccount> outIte = tb_outaccount.iterator();
		// 将记录写入文件
		while(outIte.hasNext()) {
			TableOutAccount tmp = outIte.next();
			line = tmp.get_userID();
			line += space;
			line += tmp.get_id();
			line += space;
			line += tmp.getMoney();
			line += space;
			line += tmp.getTime();
			line += space;
			line += tmp.getType();
			line += space;
			line += tmp.getAddress();
			line += space;
			line += tmp.getMark();
			line += space;
			//Log.d("DBOpenHelper exportToFile", "line = " + line);
			try {
				bw.write(line, 0, line.length());
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 导出便签信息
		List<TableFlag> lisTb_flags = new ArrayList<TableFlag>();//创建集合对象
		// 获取所有便签信息
		cursor = db.rawQuery("select * from tb_flag where userID = ? order by _id",new String[] { userId });
		// 遍历所有的便签信息
		while (cursor.moveToNext()) {
			//将遍历到的便签信息添加到集合中
			lisTb_flags.add(new TableFlag(
					cursor.getString(cursor.getColumnIndex("userID")),
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("flag"))));
		}
		// 便签数目
		line = String.valueOf(lisTb_flags.size());
		//Log.d("DBOpenHelper exportToFile", "line = " + line);
		try {
			bw.write(line, 0, line.length());
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<TableFlag> flagIte = lisTb_flags.iterator();
		// 将记录写入文件
		while(flagIte.hasNext()) {
			TableFlag tmp = flagIte.next();
			line = tmp.get_userID();
			line += space;
			line += tmp.get_id();
			line += space;
			line += tmp.getFlag();
			line += space;
			//Log.d("DBOpenHelper exportToFile", "line = " + line);
			try {
				bw.write(line, 0, line.length());
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 从文件中导入数据到数据库
	public void importFromFile(String userId, String filePath) {
		String space = ",";
		SQLiteDatabase db = getWritableDatabase();	// 初始化SQLiteDatabase对象
		//删除原有数据
		db.execSQL("delete from tb_inaccount where userID = ?", new String [] {userId });
		db.execSQL("delete from tb_outaccount where userID = ?", new String [] {userId });
		db.execSQL("delete from tb_flag where userID = ?", new String [] {userId });
		
		String userID = null, time = null, handler = null, address = null, type = null, mark = null, flag = null;
		int _id = 0, num = 0;
		double money = 0;
		
		File file = new File(filePath);
		if(!file.exists()){
			Log.d("DBOpenHelper importFromFile", "can not open file " + filePath);
			System.exit(0);
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String line = null;
		try {
			//读取收入信息数目
			line = br.readLine();
			//Log.d("DBOpenHelper importFromFile", "line = " + line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		num = Integer.valueOf(line).intValue();
		for(int i = 0; i < num; i++) {
			try {
				//按行读取收入信息
				int begin = 0, end;
				line = br.readLine();
				//Log.d("DBOpenHelper importFromFile", "line = " + line);
				end = line.indexOf(space);
				userID = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf(space, begin);
				_id = Integer.valueOf(line.substring(begin, end)).intValue();
				begin = end + 1;
				end = line.indexOf(space, begin);
				money = Double.valueOf(line.substring(begin, end)).doubleValue();
				begin = end + 1;
				end = line.indexOf(space, begin);
				time = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf(space, begin);
				type = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf(space, begin);
				handler = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf(space, begin);
				mark = line.substring(begin, end);
			} catch(Exception e) {
				e.printStackTrace();
			}
			//插入收入信息到数据库
			db.execSQL(
					"insert into tb_inaccount (userID,_id,money,time,type,handler,mark) values (?,?,?,?,?,?,?)",
					new Object[] { userID, _id, money, time, type, handler, mark });
		}
		
		try {
			//读取支出信息数目
			line = br.readLine();
			//Log.d("DBOpenHelper importFromFile", "line = " + line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		num = Integer.valueOf(line).intValue();
		for(int i = 0; i < num; i++) {
			try {
				//按行读取支出信息
				int begin = 0, end;
				line = br.readLine();
				//Log.d("DBOpenHelper importFromFile", "line = " + line);
				end = line.indexOf(space);
				userID = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf(space, begin);
				_id = Integer.valueOf(line.substring(begin, end)).intValue();
				begin = end + 1;
				end = line.indexOf(space, begin);
				money = Double.valueOf(line.substring(begin, end)).doubleValue();
				begin = end + 1;
				end = line.indexOf(space, begin);
				time = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf(space, begin);
				type = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf(space, begin);
				address = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf(space, begin);
				mark = line.substring(begin, end);
			} catch(Exception e) {
				e.printStackTrace();
			}
			//插入支出信息到数据库
			db.execSQL(
					"insert into tb_outaccount (userID,_id,money,time,type,address,mark) values (?,?,?,?,?,?,?)",
					new Object[] { userID, _id, money, time, type, address, mark });
		}
		
		try {
			//读取便签信息数目
			line = br.readLine();
			//Log.d("DBOpenHelper importFromFile", "line = " + line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		num = Integer.valueOf(line).intValue();
		for(int i = 0; i < num; i++) {
			try {
				//按行读取便签
				int begin = 0, end;
				line = br.readLine();
				//Log.d("DBOpenHelper importFromFile", "line = " + line);
				end = line.indexOf(space);
				userID = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf(space, begin);
				_id = Integer.valueOf(line.substring(begin, end)).intValue();
				begin = end + 1;
				end = line.indexOf(space, begin);
				flag = line.substring(begin, end);
			} catch(Exception e) {
				e.printStackTrace();
			}
			//插入便签到数据库
			db.execSQL(
					"insert into tb_flag (userID,_id,flag) values (?,?,?)",
					new Object[] { userID, _id, flag });
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}