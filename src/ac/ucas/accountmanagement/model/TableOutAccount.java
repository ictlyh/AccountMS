/**
 * Project Name:AccountManagementSystem
 * File Name:TableOutAccount.java
 * Package Name:ac.ucas.accountmanagement.model
 * Date:2014-12-15上午10:47:14
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: TableOutAccount
 * Function: 支出信息实体类
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.model;

public class TableOutAccount {
	
	private String userID;
	private int _id;		//存储支出编号
	private double money;	//存储支出金额
	private String time;	//存储支出时间
	private String type;	//存储支出类别
	private String address;	//存储支出地点
	private String mark;	//存储支出备注

	//默认构造函数
	public TableOutAccount() {
		super();
	}

	//定义有参构造函数，用来初始化支出信息实体类中的各个字段
	public TableOutAccount(String userID, int _id, double money, String time, String type,
			String address, String mark) {
		super();
		this.userID = userID;
		this._id = _id;		//为支出编号赋值
		this.money = money;	//为支出金额赋值
		this.time = time;	//为支出时间赋值
		this.type = type;	//为支出类型赋值
		this.address = address;//为支出地址赋值
		this.mark = mark;	//为支出备注赋值
	}

	public String get_userID() {
		return userID;
	}
	
	public void set_userID(String userID) {
		this.userID = userID;
	}
	
	//获取支出ID
	public int get_id() {
		return _id;
	}

	//设置支出ID
	public void set_id(int _id) {
		this._id = _id;
	}

	//获取支出金额
	public double getMoney() {
		return money;
	}

	//设置支出金额
	public void setMoney(double money) {
		this.money = money;
	}

	//获取支出时间
	public String getTime() {
		return time;
	}

	//设置支出时间
	public void setTime(String time) {
		this.time = time;
	}

	//获取支出类型
	public String getType() {
		return type;
	}

	//设置支出类型
	public void setType(String type) {
		this.type = type;
	}

	//获取支出地址
	public String getAddress() {
		return address;
	}

	//设置支出地址
	public void setAddress(String address) {
		this.address = address;
	}

	//获取支出备注
	public String getMark() {
		return mark;
	}

	//设置支出备注
	public void setMark(String mark) {
		this.mark = mark;
	}
}