/**
 * Project Name:AccountManagementSystem
 * File Name:ChangePwd.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-16下午6:56:07
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: ChangePwd
 * Function: 更改密码
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

public class ChangePwd extends Activity {

	EditText txtpwd;					//创建EditText对象
	EditText txtpwdconf;				//创建EditText对象
	Button btnchpwd, btncancle;			//创建两个Button对象
	
	String userID = this.getIntent().getStringExtra("userID");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepwd);					//设置布局文件
		txtpwd = (EditText) findViewById(R.id.txtChPwd);	//获取密码文本框
		txtpwdconf = (EditText) findViewById(R.id.txtChPwdConf);//获取确认密码
		btnchpwd = (Button) findViewById(R.id.btnConf);		//获取确定按钮
		btncancle = (Button) findViewById(R.id.btnsetCancel);//获取取消按钮

		//为确定按钮添加监听事件
		btnchpwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//密码是否一致
				if(txtpwd.getText().toString().equals(txtpwdconf.getText().toString())) {
					PwdDAO pwdDAO = new PwdDAO(ChangePwd.this);// 创建PwdDAO对象
					//根据输入的用户名和密码创建TablePassword对象
					TablePassword tb_pwd = new TablePassword(userID, txtpwd.getText().toString());
					pwdDAO.update(tb_pwd);
					Toast.makeText(ChangePwd.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(ChangePwd.this, MainActivity.class);	//创建Intent对象
					startActivity(intent);
				}
				else {
					Toast.makeText(ChangePwd.this, "密码不一致！", Toast.LENGTH_SHORT).show();
				}
				txtpwd.setText("");		//清空密码文本框
				txtpwdconf.setText("");	//清空密码确认文本框
			}
		});

		btncancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				txtpwd.setText("");				//清空用户名文本框
				txtpwd.setHint("请输入密码");	//为用户名文本框设置提示
				txtpwdconf.setText("");					//清空密码文本框
				txtpwdconf.setHint("请确认密码");		//为密码文本框设置提示
			}
		});
	}
}
