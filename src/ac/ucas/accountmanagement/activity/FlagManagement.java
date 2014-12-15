/**
 * Project Name:AccountManagementSystem
 * File Name:FlagManagement.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午5:42:53
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: FlagManagement
 * Function: TODO ADD FUNCTION.
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
import ac.ucas.accountmanagement.dao.FlagDAO;
import ac.ucas.accountmanagement.model.TableFlag;
import ac.ucas.accountmanagementsystem.R;

public class FlagManagement extends Activity {

	EditText txtFlag;					//创建EditText对象
	Button btnEdit, btnDel;				//创建两个Button对象
	String strid;						//创建字符串，表示便签的id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flagmanage);						//设置布局文件
		txtFlag = (EditText) findViewById(R.id.txtFlagManage);		//获取便签文本框
		btnEdit = (Button) findViewById(R.id.btnFlagManageEdit);	//获取修改按钮
		btnDel = (Button) findViewById(R.id.btnFlagManageDelete);	//获取删除按钮

		Intent intent = getIntent();								//创建Intent对象
		Bundle bundle = intent.getExtras();							//获取便签id
		strid = bundle.getString(ShowInfo.FLAG);					//将便签id转换为字符串
		final FlagDAO flagDAO = new FlagDAO(FlagManagement.this);	//创建FlagDAO对象
		txtFlag.setText(flagDAO.find(Integer.parseInt(strid)).getFlag());//根据便签id查找便签信息，并显示在文本框中

		//为修改按钮设置监听事件
		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				TableFlag tb_flag = new TableFlag();				//创建Tb_flag对象
				tb_flag.set_id(Integer.parseInt(strid));			//设置便签id
				tb_flag.setFlag(txtFlag.getText().toString());		//设置便签值
				flagDAO.update(tb_flag);							//修改便签信息
				//弹出信息提示
				Toast.makeText(FlagManagement.this, "〖便签数据〗修改成功！",
						Toast.LENGTH_SHORT).show();
			}
		});

		// 为删除按钮设置监听事件
		btnDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				flagDAO.detele(Integer.parseInt(strid));			//根据指定的id删除便签信息
				Toast.makeText(FlagManagement.this, "〖便签数据〗删除成功！",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}