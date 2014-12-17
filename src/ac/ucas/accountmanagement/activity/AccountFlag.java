/**
 * Project Name:AccountManagementSystem
 * File Name:AccountFlag.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午4:40:07
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: AccountFlag
 * Function: 新增便签
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
import ac.ucas.accountmanagement.dao.FlagDAO;
import ac.ucas.accountmanagement.model.TableFlag;
import ac.ucas.accountmanagementsystem.R;

public class AccountFlag extends BaseActivity {
	
	EditText txtFlag;			//创建EditText组件对象
	Button btnflagSaveButton;	//创建Button组件对象
	Button btnflagResetButton;	//创建Button组件对象
	Button btnflagCancleButton;	//创建Button组件对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountflag);

		txtFlag = (EditText) findViewById(R.id.txtFlag);					//获取便签文本框
		btnflagSaveButton = (Button) findViewById(R.id.btnflagSave);		//获取保存按钮
		btnflagResetButton = (Button) findViewById(R.id.btnflagReset);		//获取重置按钮
		btnflagCancleButton = (Button) findViewById(R.id.btnflagCancle);	//获取取消按钮
		
		//为保存按钮设置监听事件
		btnflagSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String strFlag = txtFlag.getText().toString();		//获取便签文本框的值
				if (!strFlag.isEmpty()) {							//判断获取的值不为空
					FlagDAO flagDAO = new FlagDAO(AccountFlag.this);//创建FlagDAO对象
					TableFlag tb_flag = new TableFlag(flagDAO.getMaxId() + 1, strFlag);//创建TableFlag对象
					flagDAO.add(tb_flag);							//添加便签信息
					// 弹出信息提示
					Toast.makeText(AccountFlag.this, "便签添加成功", Toast.LENGTH_SHORT).show();
					//跳转到主页面
					startActivity(new Intent(AccountFlag.this, MainActivity.class));
				} else {
					Toast.makeText(AccountFlag.this, "请输入便签内容", Toast.LENGTH_SHORT).show();
				}
			}
		});

		//为重置按钮设置监听事件
		btnflagResetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//清空便签文本框
				txtFlag.setText("");
			}
		});
		
		//为取消按钮设置监听事件
		btnflagCancleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//返回主页面
				startActivity(new Intent(AccountFlag.this, MainActivity.class));
			}
		});
	}
}