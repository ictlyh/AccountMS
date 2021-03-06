/**
 * Project Name:AccountManagementSystem
 * File Name:AddOutAccount.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午4:59:48
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: AddOutAccount
 * Function: 新增支出
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import java.util.Calendar;
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
import android.widget.Toast;
import ac.ucas.accountmanagement.dao.OutAccountDAO;
import ac.ucas.accountmanagement.model.TableOutAccount;
import ac.ucas.accountmanagementsystem.R;

public class AddOutAccount extends BaseActivity {
	
	protected static final int DATE_DIALOG_ID = 0;		//创建日期对话框常量
	EditText txtMoney, txtTime, txtAddress, txtMark;	//创建4个EditText对象
	Spinner spType;										//创建Spinner对象
	Button btnSaveButton;								//创建Button对象“保存”
	Button btnResetButton;								//创建Button对象“重置”
	Button btnCancelButton;								//创建Button对象“取消”
	String userID;										//保存参数userid
	private int mYear;									//年
	private int mMonth;									//月
	private int mDay;									//日

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addoutaccount);						//设置布局文件
		txtMoney = (EditText) findViewById(R.id.txtMoney);			//获取金额文本框
		txtTime = (EditText) findViewById(R.id.txtTime);			//获取时间文本框
		txtAddress = (EditText) findViewById(R.id.txtAddress);		//获取地点文本框
		txtMark = (EditText) findViewById(R.id.txtMark);			//获取备注文本框
		spType = (Spinner) findViewById(R.id.spType);				//获取类别下拉列表
		btnSaveButton = (Button) findViewById(R.id.btnOutSave);		//获取保存按钮
		btnResetButton = (Button) findViewById(R.id.btnOutReset);	//获取重置按钮
		btnCancelButton = (Button) findViewById(R.id.btnOutCancel);	//获取取消按钮
		userID = this.getIntent().getStringExtra("userID");			//获取userID

		//为时间文本框设置单击监听事件
		txtTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);						//显示日期选择对话框
			}
		});

		//为保存按钮设置监听事件
		btnSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String strMoney = txtMoney.getText().toString();//获取金额文本框的值
				//判断金额不为空
				if (!strMoney.isEmpty()) {
					//创建OutaccountDAO对象
					OutAccountDAO outaccountDAO = new OutAccountDAO(AddOutAccount.this);
					//创建TableOutAccount对象
					TableOutAccount tb_outaccount = new TableOutAccount(
							userID,
							outaccountDAO.getMaxId(userID) + 1,
							Double.parseDouble(strMoney),
							txtTime.getText().toString(),
							spType.getSelectedItem().toString(),
							txtAddress.getText().toString(),
							txtMark.getText().toString());
					outaccountDAO.add(tb_outaccount);	//添加支出信息
					//弹出信息提示
					Toast.makeText(AddOutAccount.this, "支出添加成功", Toast.LENGTH_SHORT).show();
					//调转到主页面
					Intent intent = new Intent(AddOutAccount.this, MainActivity.class);
					intent.putExtra("userID", userID);
					startActivity(intent);
				} else {
					Toast.makeText(AddOutAccount.this, "请输入金额", Toast.LENGTH_SHORT).show();
				}
			}
		});

		//为重置按钮设置监听事件
		btnResetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				txtMoney.setText("");				//设置金额文本框为空
				txtMoney.setHint("0.00");			//为金额文本框设置提示
				txtTime.setText("");				//设置时间文本框为空
				txtTime.setHint("2014-01-01");		//为时间文本框设置提示
				txtAddress.setText("");				//设置地点文本框为空
				txtMark.setText("");				//设置备注文本框为空
				spType.setSelection(0);				//设置类别下拉列表默认选择第一项
			}
		});

		//为取消按钮设置监听事件
		btnCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//返回主页面
				Intent intent = new Intent(AddOutAccount.this, MainActivity.class);
				intent.putExtra("userID", userID);
				startActivity(intent);
			}
		});
				
		final Calendar c = Calendar.getInstance();			//获取当前系统日期
		mYear = c.get(Calendar.YEAR);						//获取年份
		mMonth = c.get(Calendar.MONTH);						//获取月份
		mDay = c.get(Calendar.DAY_OF_MONTH);				//获取天数
		updateDisplay();									//显示当前系统时间
	}

	//重写onCreateDialog方法
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:								//弹出日期选择对话框
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;									//为年份赋值
			mMonth = monthOfYear;							//为月份赋值
			mDay = dayOfMonth;								//为天赋值
			updateDisplay();								//显示设置的日期
		}
	};

	//显示设置的时间
	private void updateDisplay() {
		txtTime.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
	}
}