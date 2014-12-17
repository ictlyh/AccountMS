/**
 * Project Name:AccountManagementSystem
 * File Name:TableInAccount.java
 * Package Name:ac.ucas.accountmanagement.model
 * Date:2014-12-15上午10:30:35
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: TableInAccount
 * Function: 输入信息实体类
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.model;

public class TableInAccount {
	
	private String userID;
	private int _id;		// 存储收入编号
	private double money;	// 存储收入金额
	private String time;	// 存储收入时间
	private String type;	// 存储收入类别
	private String handler;	// 存储收入付款方
	private String mark;	// 存储收入备注
	
	//默认构造函数
	public TableInAccount() {
		super();
	}

	//定义有参构造函数，用来初始化收入信息实体类中的各个字段
	public TableInAccount(String userID, int _id, double money, String time, String type,
			String handler, String mark) {
		super();
		this.userID = userID;
		this._id = _id;		//为收入编号赋值
		this.money = money;	//为收入金额赋值
		this.time = time;	//为收入时间赋值
		this.type = type;	//为收入类别赋值
		this.handler = handler;//为收入付款方赋值
		this.mark = mark;	//为收入备注赋值
	}

	public String get_userID() {
		return userID;
	}
	
	public void set_userID(String userID) {
		this.userID = userID;
	}
	//获取输入编号
	public int get_id() {
		return _id;
	}

	//设置输入编号
	public void set_id(int _id) {
		this._id = _id;
	}

	//获取输入金额
	public double getMoney() {
		return money;
	}

	//设置输入金额
	public void setMoney(double money) {
		this.money = money;
	}

	//获取输入时间
	public String getTime() {
		return time;
	}

	//设置输入时间
	public void setTime(String time) {
		this.time = time;
	}

	//获取输入类型
	public String getType() {
		return type;
	}

	//设置输入类型
	public void setType(String type) {
		this.type = type;
	}

	//获取付款方
	public String getHandler() {
		return handler;
	}

	//设置付款方
	public void setHandler(String handler) {
		this.handler = handler;
	}

	//获取输入备注
	public String getMark() {
		return mark;
	}

	//设置输入备注
	public void setMark(String mark) {
		this.mark = mark;
	}
}