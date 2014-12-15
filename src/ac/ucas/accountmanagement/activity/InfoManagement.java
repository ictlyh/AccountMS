/**
 * Project Name:AccountManagementSystem
 * File Name:InfoManagement.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午5:55:56
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: InfoManagement
 * Function: TODO ADD FUNCTION.
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ac.ucas.accountmanagement.dao.InAccountDAO;
import ac.ucas.accountmanagement.dao.OutAccountDAO;
import ac.ucas.accountmanagement.model.TableInAccount;
import ac.ucas.accountmanagement.model.TableOutAccount;
import ac.ucas.accountmanagementsystem.R;

public class InfoManagement extends Activity {
	
	protected static final int DATE_DIALOG_ID = 0;		//创建日期对话框常量
	TextView tvtitle, textView;							//创建两个TextView对象
	EditText txtMoney, txtTime, txtHA, txtMark;			//创建4个EditText对象
	Spinner spType;										//创建Spinner对象
	Button btnEdit, btnDel;								//创建两个Button对象
	String[] strInfos;									//定义字符串数组
	String strid, strType;								//定义两个字符串变量，分别用来记录信息编号和管理类型

	private int mYear;									//年
	private int mMonth;									//月
	private int mDay;									//日

	OutAccountDAO outaccountDAO = new OutAccountDAO(InfoManagement.this);	//创建OutaccountDAO对象
	InAccountDAO inaccountDAO = new InAccountDAO(InfoManagement.this);		//创建InaccountDAO对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infomanage);					//设置布局文件
		tvtitle = (TextView) findViewById(R.id.inouttitle);		//获取标题标签对象
		textView = (TextView) findViewById(R.id.tvInOut);		//获取地点/付款方标签对象
		txtMoney = (EditText) findViewById(R.id.txtInOutMoney);	//获取金额文本框
		txtTime = (EditText) findViewById(R.id.txtInOutTime);	//获取时间文本框
		spType = (Spinner) findViewById(R.id.spInOutType);		//获取类别下拉列表
		txtHA = (EditText) findViewById(R.id.txtInOut);			//获取地点/付款方文本框
		txtMark = (EditText) findViewById(R.id.txtInOutMark);	//获取备注文本框
		btnEdit = (Button) findViewById(R.id.btnInOutEdit);		//获取修改按钮
		btnDel = (Button) findViewById(R.id.btnInOutDelete);	//获取删除按钮

		Intent intent = getIntent();							//创建Intent对象
		Bundle bundle = intent.getExtras();						//获取传入的数据，并使用Bundle记录
		strInfos = bundle.getStringArray(ShowInfo.FLAG);		//获取Bundle中记录的信息
		strid = strInfos[0];									//记录id
		strType = strInfos[1];									//记录类型
		//如果类型是btnoutinfo
		if (strType.equals("btnoutinfo")) {
			tvtitle.setText("支出管理");							//设置标题为“支出管理”
			textView.setText("地  点：");							//设置“地点/付款方”标签文本为“地 点：”
			//根据编号查找支出信息，并存储到Tb_outaccount对象中
			TableOutAccount tb_outaccount = outaccountDAO.find(Integer.parseInt(strid));
			txtMoney.setText(String.valueOf(tb_outaccount.getMoney()));//显示金额
			txtTime.setText(tb_outaccount.getTime());			//显示时间
			spType.setPrompt(tb_outaccount.getType());			//显示类别
			txtHA.setText(tb_outaccount.getAddress());			//显示地点
			txtMark.setText(tb_outaccount.getMark());			//显示备注
		} 
		//如果类型是btnininfo
		else if (strType.equals("btnininfo")) {
			tvtitle.setText("收入管理");							//设置标题为“收入管理”
			textView.setText("付款方：");						//设置“地点/付款方”标签文本为“付款方：”
			//根据编号查找收入信息，并存储到Tb_inaccount对象中
			TableInAccount tb_inaccount = inaccountDAO.find(Integer.parseInt(strid));
			txtMoney.setText(String.valueOf(tb_inaccount.getMoney()));// 显示金额
			txtTime.setText(tb_inaccount.getTime());			//显示时间
			spType.setPrompt(tb_inaccount.getType());			//显示类别
			txtHA.setText(tb_inaccount.getHandler());			//显示付款方
			txtMark.setText(tb_inaccount.getMark());			//显示备注
		}

		//为时间文本框设置单击监听事件
		txtTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);						//显示日期选择对话框
			}
		});

		//为修改按钮设置监听事件
		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//判断类型如果是btnoutinfo
				if (strType.equals("btnoutinfo")) {
					TableOutAccount tb_outaccount = new TableOutAccount();	//创建Tb_outaccount对象
					tb_outaccount.set_id(Integer.parseInt(strid));			//设置编号
					tb_outaccount.setMoney(Double.parseDouble(txtMoney.getText().toString()));//设置金额
					tb_outaccount.setTime(txtTime.getText().toString());	//设置时间
					tb_outaccount.setType(spType.getSelectedItem().toString());//设置类别
					tb_outaccount.setAddress(txtHA.getText().toString());	//设置地点
					tb_outaccount.setMark(txtMark.getText().toString());	//设置备注
					outaccountDAO.update(tb_outaccount);					//更新支出信息
				}
				//判断类型如果是btnininfo
				else if (strType.equals("btnininfo")) {
					TableInAccount tb_inaccount = new TableInAccount();		//创建Tb_inaccount对象
					tb_inaccount.set_id(Integer.parseInt(strid));			//设置编号
					tb_inaccount.setMoney(Double.parseDouble(txtMoney.getText().toString()));//设置金额
					tb_inaccount.setTime(txtTime.getText().toString());		//设置时间
					tb_inaccount.setType(spType.getSelectedItem().toString());//设置类别
					tb_inaccount.setHandler(txtHA.getText().toString());	//设置付款方
					tb_inaccount.setMark(txtMark.getText().toString());		//设置备注
					inaccountDAO.update(tb_inaccount);						//更新收入信息
				}
				//弹出信息提示
				Toast.makeText(InfoManagement.this, "〖数据〗修改成功！", Toast.LENGTH_SHORT).show();
			}
		});

		//为删除按钮设置监听事件
		btnDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//判断类型如果是btnoutinfo
				if (strType.equals("btnoutinfo")) {
					outaccountDAO.detele(Integer.parseInt(strid));	//根据编号删除支出信息
				}
				//判断类型如果是btnininfo
				else if (strType.equals("btnininfo")) {
					inaccountDAO.detele(Integer.parseInt(strid));	//根据编号删除收入信息
				}
				Toast.makeText(InfoManagement.this, "〖数据〗删除成功！", Toast.LENGTH_SHORT).show();
			}
		});

		final Calendar c = Calendar.getInstance();		//获取当前系统日期
		mYear = c.get(Calendar.YEAR);					//获取年份
		mMonth = c.get(Calendar.MONTH);					//获取月份
		mDay = c.get(Calendar.DAY_OF_MONTH);			//获取天数
		updateDisplay();								//显示当前系统时间
	}

	//重写onCreateDialog方法
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:					//弹出日期选择对话框
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;						//为年份赋值
			mMonth = monthOfYear;				//为月份赋值
			mDay = dayOfMonth;					//为天赋值
			updateDisplay();					//显示设置的日期
		}
	};

	// 显示设置的时间
	private void updateDisplay() {
		txtTime.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
	}
}