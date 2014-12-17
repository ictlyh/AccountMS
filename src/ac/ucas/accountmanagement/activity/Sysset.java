/**
 * Project Name:AccountManagementSystem
 * File Name:Sysset.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午9:18:41
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: Sysset
 * Function: 用户验证
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ac.ucas.accountmanagement.dao.PwdDAO;
import ac.ucas.accountmanagementsystem.R;

public class Sysset extends BaseActivity {
	
	EditText txtuserid;					//创建EditText对象
	EditText txtpwd;					//创建EditText对象
	Button betconf, btnsetCancle, btnsetReset;//创建两个Button对象
	String userID;						//保存参数userid

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sysset);						//设置布局文件
		txtuserid = (EditText) findViewById(R.id.txtUserID);	//获取用户名文本框
		txtpwd = (EditText) findViewById(R.id.txtPwd);			//获取密码文本框
		betconf = (Button) findViewById(R.id.btnConf);			//获取确定按钮
		btnsetReset = (Button) findViewById(R.id.btnsetReset);	//获取重置按钮
		btnsetCancle = (Button) findViewById(R.id.btnsetCancle);//获取取消按钮
		userID = this.getIntent().getStringExtra("userID");		//获取userID

		//为确定按钮添加监听事件
		betconf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				PwdDAO pwdDAO = new PwdDAO(Sysset.this);// 创建PwdDAO对象
				//判断输入的用户名是否为当前用户名
				if( !userID.equals(txtuserid.getText().toString())) {
					Toast.makeText(Sysset.this, "请输入当前用户名", Toast.LENGTH_SHORT).show();
				}
				//判断用户名和密码是否正确
				else if(pwdDAO.find(userID) != null && 
						pwdDAO.find(userID).getPassword().equals(txtpwd.getText().toString())) {
					Intent intent = new Intent(Sysset.this, ChangePwd.class);// 使用ChangePwd窗口初始化Intent
					intent.putExtra("userID", userID);
					startActivity(intent);// 打开ChangePwd
				}
				else {
					Toast.makeText(Sysset.this, "请输入正确的用户名和密码！", Toast.LENGTH_SHORT).show();
				}
				txtuserid.setText("");	//清空用户名文本框
				txtpwd.setText("");		//清空密码文本框
			}
		});

		//为重置按钮添加监听事件
		btnsetReset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				txtuserid.setText("");				//清空用户名文本框
				txtuserid.setHint("请输入用户名");	//为用户名文本框设置提示
				txtpwd.setText("");					//清空密码文本框
				txtpwd.setHint("请输入密码");		//为密码文本框设置提示
			}
		});
		
		//为取消按钮添加监听事件
		btnsetCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(new Intent(Sysset.this, MainActivity.class));	//创建Intent对象
				intent.putExtra("userID", userID);
				startActivity(intent);
			}
		});
	}
}