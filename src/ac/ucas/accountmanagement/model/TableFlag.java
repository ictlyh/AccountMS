/**
 * Project Name:AccountManagementSystem
 * File Name:TableFlag.java
 * Package Name:ac.ucas.accountmanagement.model
 * Date:2014-12-15上午10:43:54
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: TableFlag
 * Function: 便签信息实体类
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.model;

public class TableFlag {
	
	private int _id;		// 存储便签编号
	private String flag;	// 存储便签信息
	
	//默认构造函数
	public TableFlag() {
		super();
	}

	//定义有参构造函数，用来初始化便签信息实体类中的各个字段
	public TableFlag(int _id, String flag) {
		super();
		this._id = _id;		// 为便签号赋值
		this.flag = flag;	// 为便签信息赋值
	}

	//获取便签ID
	public int get_id() {
		return _id;
	}

	//设置便签ID
	public void set_id(int _id) {
		this._id = _id;
	}

	//获取便签内容
	public String getFlag() {
		return flag;
	}

	//设置便签内容
	public void setFlag(String flag) {
		this.flag = flag;
	}
}