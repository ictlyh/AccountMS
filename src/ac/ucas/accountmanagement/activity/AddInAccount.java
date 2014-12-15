/**
 * Project Name:AccountManagementSystem
 * File Name:AddInAccount.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午4:46:08
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: AddInAccount
 * Function: TODO ADD FUNCTION.
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import ac.ucas.accountmanagement.dao.InAccountDAO;
import ac.ucas.accountmanagement.model.TableInAccount;
import ac.ucas.accountmanagementsystem.R;

public class AddInAccount extends Activity {
	
	protected static final int DATE_DIALOG_ID = 0;				//创建日期对话框常量
	EditText txtInMoney, txtInTime, txtInHandler, txtInMark;	//创建4个EditText对象
	Spinner spInType;											//创建Spinner对象
	Button btnInSaveButton;										//创建Button对象“保存”
	Button btnInCancelButton;									//创建Button对象“取消”

	private int mYear;											//年
	private int mMonth;											//月
	private int mDay;											//日

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addinaccount);					//设置布局文件
		txtInMoney = (EditText) findViewById(R.id.txtInMoney);	//获取金额文本框
		txtInTime = (EditText) findViewById(R.id.txtInTime);	//获取时间文本框
		txtInHandler = (EditText) findViewById(R.id.txtInHandler);//获取付款方文本框
		txtInMark = (EditText) findViewById(R.id.txtInMark);	//获取备注文本框
		spInType = (Spinner) findViewById(R.id.spInType);		//获取类别下拉列表
		btnInSaveButton = (Button) findViewById(R.id.btnInSave);//获取保存按钮
		btnInCancelButton = (Button) findViewById(R.id.btnInCancel);//获取取消按钮

		txtInTime.setOnClickListener(new OnClickListener() {	//为时间文本框设置单击监听事件
					@Override
					public void onClick(View arg0) {
						showDialog(DATE_DIALOG_ID);				//显示日期选择对话框
					}
				});

		//为保存按钮设置监听事件
		btnInSaveButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String strInMoney = txtInMoney.getText().toString();// 获取金额文本框的值
						if (!strInMoney.isEmpty()) {			//判断金额不为空
							//创建InaccountDAO对象
							InAccountDAO inaccountDAO = new InAccountDAO(AddInAccount.this);
							//创建Tb_inaccount对象
							TableInAccount tb_inaccount = new TableInAccount(
									inaccountDAO.getMaxId() + 1,
									Double.parseDouble(strInMoney),
									txtInTime.getText().toString(),
									spInType.getSelectedItem().toString(),
									txtInHandler.getText().toString(),
									txtInMark.getText().toString());
							inaccountDAO.add(tb_inaccount);		//添加收入信息
							//弹出信息提示
							Toast.makeText(AddInAccount.this, "〖新增收入〗数据添加成功！",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(AddInAccount.this, "请输入收入金额！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		//为取消按钮设置监听事件
		btnInCancelButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						txtInMoney.setText("");					//设置金额文本框为空
						txtInMoney.setHint("0.00");				//为金额文本框设置提示
						txtInTime.setText("");					//设置时间文本框为空
						txtInTime.setHint("2014-01-01");		//为时间文本框设置提示
						txtInHandler.setText("");				//设置付款方文本框为空
						txtInMark.setText("");					//设置备注文本框为空
						spInType.setSelection(0);				//设置类别下拉列表默认选择第一项
					}
				});

		final Calendar c = Calendar.getInstance();				//获取当前系统日期
		mYear = c.get(Calendar.YEAR);							//获取年份
		mMonth = c.get(Calendar.MONTH);							//获取月份
		mDay = c.get(Calendar.DAY_OF_MONTH);					//获取天数
		updateDisplay();										//显示当前系统时间
	}

	// 重写onCreateDialog方法
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:									//弹出日期选择对话框
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;										//为年份赋值
			mMonth = monthOfYear;								//为月份赋值
			mDay = dayOfMonth;									//为天赋值
			updateDisplay();									//显示设置的日期
		}
	};

	//显示设置的时间
	private void updateDisplay() {
		txtInTime.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
	}
}