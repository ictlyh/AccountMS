/**
 * Project Name:AccountManagementSystem
 * File Name:TablePassword.java
 * Package Name:ac.ucas.accountmanagement.model
 * Date:2014-12-15上午10:52:19
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: TablePassword
 * Function: 密码数据表实体类
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.model;

public class TablePassword {
	
	private String userID;		//定义字符串，表示用户ID，唯一
	private String password;	//定义字符串，表示用户密码

	//默认构造函数
	public TablePassword() {
		super();
	}

	//定义有参构造函数
	public TablePassword(String userID, String password) {
		super();
		this.userID = userID;	//为用户ID赋值
		this.password = password;//为用户密码赋值
	}

	//获取用户ID
	public String getUserID() {
		return userID;
	}

	//设置用户ID
	public void setUserID(String userID) {
		this.userID = userID;
	}

	//获取用户密码
	public String getPassword() {
		return password;
	}

	//设置用户密码
	public void setPassword(String password) {
		this.password = password;
	}
}