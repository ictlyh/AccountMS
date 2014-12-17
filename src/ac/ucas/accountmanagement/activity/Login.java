/**
 * Project Name:AccountManagementSystem
 * File Name:Login.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午8:42:26
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: Login
 * Function: 登录系统
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ac.ucas.accountmanagement.dao.PwdDAO;
import ac.ucas.accountmanagement.model.TablePassword;
import ac.ucas.accountmanagementsystem.R;

public class Login extends Activity {
	
	EditText txtloginname, txtloginpwd;			//创建两个EditText对象
	Button btnlogin, btnregist, btnclose;		//创建两个Button对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);								//设置布局文件

		txtloginname = (EditText) findViewById(R.id.txtLoginName);	//获取用户名文本框
		txtloginpwd = (EditText) findViewById(R.id.txtLoginPwd);	//获取密码文本框
		btnlogin = (Button) findViewById(R.id.btnLogin);			//获取登录按钮
		btnregist = (Button) findViewById(R.id.btnRegist);			//获取注册按钮
		btnclose = (Button) findViewById(R.id.btnClose);			//获取取消按钮

		//为登录按钮设置监听事件
		btnlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Login.this, MainActivity.class);	//创建Intent对象
				PwdDAO pwdDAO = new PwdDAO(Login.this);						//创建PwdDAO对象
				
				//判断用户名和密码是否为空
				if(txtloginpwd.getText().toString().length() == 0 ||
						txtloginname.getText().toString().length() == 0) {
					Toast.makeText(Login.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
				}
				//判断是否有此用户名
				else if(pwdDAO.find(txtloginname.getText().toString()) == null) {
					Toast.makeText(Login.this, "用户名不存在", Toast.LENGTH_SHORT).show();
				}
				//判断密码是否正确
				else if(pwdDAO.find(txtloginname.getText().toString()).getPassword().equals(txtloginpwd.getText().toString())) {
					startActivity(intent);
				}
				else {
					Toast.makeText(Login.this, "密码错误", Toast.LENGTH_SHORT).show();
				}
				txtloginname.setText("");	//清空用户名文本框
				txtloginpwd.setText("");	//清空密码文本框
			}
		});

		//为注册按钮设置监听事件
		btnregist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Login.this, MainActivity.class);	//创建Intent对象
				PwdDAO pwdDAO = new PwdDAO(Login.this);						//创建PwdDAO对象
				
				//判断是否有此用户名
				if(pwdDAO.find(txtloginname.getText().toString()) != null) {
					Toast.makeText(Login.this, "用户名已存在！", Toast.LENGTH_SHORT).show();
				}
				//插入用户名和密码到数据库
				else {
					pwdDAO.add(new TablePassword(txtloginname.getText().toString(),
							txtloginpwd.getText().toString()));
					startActivity(intent);
				}
				txtloginname.setText("");	//清空用户名文本框
				txtloginpwd.setText("");	//清空密码文本框
			}
		});
		
		//为取消按钮设置监听事件
		btnclose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();//退出当前程序
			}
		});
	}
}