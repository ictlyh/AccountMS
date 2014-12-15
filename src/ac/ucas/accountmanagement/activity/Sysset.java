/**
 * Project Name:AccountManagementSystem
 * File Name:Sysset.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午9:18:41
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: Sysset
 * Function: TODO ADD FUNCTION.
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ac.ucas.accountmanagement.dao.PwdDAO;
import ac.ucas.accountmanagement.model.TablePassword;
import ac.ucas.accountmanagementsystem.R;

public class Sysset extends Activity {
	
	EditText txtuserid;					//创建EditText对象
	EditText txtpwd;					//创建EditText对象
	Button btnSet, btnsetCancel;		//创建两个Button对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sysset);					//设置布局文件
		txtuserid = (EditText) findViewById(R.id.txtUserID);//获取用户名文本框
		txtpwd = (EditText) findViewById(R.id.txtPwd);		//获取密码文本框
		btnSet = (Button) findViewById(R.id.btnSet);		//获取设置按钮
		btnsetCancel = (Button) findViewById(R.id.btnsetCancel);//获取取消按钮

		//为设置按钮添加监听事件
		btnSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				PwdDAO pwdDAO = new PwdDAO(Sysset.this);// 创建PwdDAO对象
				//根据输入的用户名和密码创建TablePassword对象
				TablePassword tb_pwd = new TablePassword(txtuserid.getText().toString(), txtpwd.getText().toString());
				//判断数据库中是否已经设置了密码
				if (pwdDAO.getCount() == 0) {
					pwdDAO.add(tb_pwd);//添加用户密码
				} else {
					pwdDAO.update(tb_pwd);//修改用户密码
				}
				// 弹出信息提示
				Toast.makeText(Sysset.this, "〖密码〗设置成功！", Toast.LENGTH_SHORT).show();
			}
		});

		btnsetCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				txtuserid.setText("");				//清空用户名文本框
				txtuserid.setHint("请输入用户名");	//为用户名文本框设置提示
				txtpwd.setText("");					//清空密码文本框
				txtpwd.setHint("请输入密码");		//为密码文本框设置提示
			}
		});
	}
}