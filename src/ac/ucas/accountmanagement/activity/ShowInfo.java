/**
 * Project Name:AccountManagementSystem
 * File Name:ShowInfo.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午9:05:11
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: ShowInfo
 * Function: 收入，支出，便签信息显示
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import ac.ucas.accountmanagement.dao.FlagDAO;
import ac.ucas.accountmanagement.dao.InAccountDAO;
import ac.ucas.accountmanagement.dao.OutAccountDAO;
import ac.ucas.accountmanagement.model.TableFlag;
import ac.ucas.accountmanagement.model.TableInAccount;
import ac.ucas.accountmanagement.model.TableOutAccount;
import ac.ucas.accountmanagementsystem.R;

public class ShowInfo extends BaseActivity {
	
	public static final String FLAG = "id";		//定义一个常量，用来作为请求码
	Button btnoutinfo, btnininfo, btnflaginfo;	//创建3个Button对象
	Button btnreturn;							//关联返回按钮
	ListView lvinfo;							//创建ListView对象
	String strType = "";						//创建字符串，记录管理类型
	String userID;								//保存参数userid

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showinfo);		//设置布局文件

		lvinfo = (ListView) findViewById(R.id.lvinfo);			//获取布局文件中的ListView组件
		btnoutinfo = (Button) findViewById(R.id.btnoutinfo);	//获取布局文件中的支出信息按钮
		btnininfo = (Button) findViewById(R.id.btnininfo);		//获取布局文件中的收入信息按钮
		btnflaginfo = (Button) findViewById(R.id.btnflaginfo);	//获取布局文件中的便签信息按钮
		btnreturn = (Button) findViewById(R.id.btnreturn);		//获取布局文件中的返回按钮
		userID = this.getIntent().getStringExtra("userID");		//获取userID

		Showinfo(R.id.btnoutinfo);								//默认显示支出信息

		//为支出信息按钮设置监听事件
		btnoutinfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Showinfo(R.id.btnoutinfo);				//显示支出信息
			}
		});

		//为收入信息按钮设置监听事件
		btnininfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Showinfo(R.id.btnininfo);				//显示收入信息
			}
		});
		
		//为便签信息按钮设置监听事件
		btnflaginfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Showinfo(R.id.btnflaginfo);				//显示便签信息
			}
		});

		//为ListView添加项单击事件
		lvinfo.setOnItemClickListener(new OnItemClickListener() {
			//覆写onItemClick方法
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());	//记录单击的项信息
				String strid = strInfo.substring(0, strInfo.indexOf('|'));		//从项信息中截取编号
				Intent intent = null;											//创建Intent对象
				//判断如果是支出或者收入信息
				if (strType == "btnoutinfo" || strType == "btnininfo") {
					intent = new Intent(ShowInfo.this, InfoManagement.class);	//使用InfoManagement窗口初始化Intent对象
					intent.putExtra(FLAG, new String[] { strid, strType });		//设置要传递的数据
				}
				// 判断如果是便签信息
				else if (strType == "btnflaginfo") {
					intent = new Intent(ShowInfo.this, FlagManagement.class);	//使用FlagManagement窗口初始化Intent对象
					intent.putExtra(FLAG, strid);								//设置要传递的数据
				}
				intent.putExtra("userID", userID);
				startActivityForResult(intent, 0);								//执行Intent，打开相应的Activity
			}
		});
		
		//为返回按钮设置监听事件
		btnreturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ShowInfo.this, MainActivity.class);
				intent.putExtra("userID", userID);
				startActivity(intent);
			}
		});
	}

	//用来根据传入的管理类型，显示相应的信息
	private void Showinfo(int intType) {
		String[] strInfos = null;						// 定义字符串数组，用来存储收入信息
		ArrayAdapter<String> arrayAdapter = null;		// 创建ArrayAdapter对象
		//以intType为条件进行判断
		switch (intType) {
		
		case R.id.btnoutinfo:							//如果是btnoutinfo按钮
			strType = "btnoutinfo";						//为strType变量赋值
			OutAccountDAO outaccountinfo = new OutAccountDAO(ShowInfo.this);//创建OutAccountDAO对象
			//获取所有支出信息，并存储到List泛型集合中
			List<TableOutAccount> listoutinfos = outaccountinfo.getScrollData(userID, 0, (int) outaccountinfo.getCount(userID));
			strInfos = new String[listoutinfos.size()];	//设置字符串数组的长度
			int i = 0;									//定义一个开始标识
			//遍历List泛型集合
			for (TableOutAccount tb_outaccount : listoutinfos) {
				//将支出相关信息组合成一个字符串，存储到字符串数组的相应位置
				strInfos[i] = tb_outaccount.get_id() + "|" + tb_outaccount.getType() + " "
						+ String.valueOf(tb_outaccount.getMoney()) + "元     " + tb_outaccount.getTime();
				i++;//标识加1
			}
			break;
			
		case R.id.btnininfo:							//如果是btnininfo按钮
			strType = "btnininfo";						//为strType变量赋值
			InAccountDAO inaccountinfo = new InAccountDAO(ShowInfo.this);// 创建InAccountDAO对象
			//获取所有收入信息，并存储到List泛型集合中
			List<TableInAccount> listinfos = inaccountinfo.getScrollData(userID, 0, (int) inaccountinfo.getCount(userID));
			strInfos = new String[listinfos.size()];	//设置字符串数组的长度
			int m = 0;									//定义一个开始标识
			//遍历List泛型集合
			for (TableInAccount tb_inaccount : listinfos) {
				//将收入相关信息组合成一个字符串，存储到字符串数组的相应位置
				strInfos[m] = tb_inaccount.get_id() + "|" + tb_inaccount.getType() + " "
						+ String.valueOf(tb_inaccount.getMoney()) + "元     " + tb_inaccount.getTime();
				m++;//标识加1
			}
			break;
			
		case R.id.btnflaginfo:							//如果是btnflaginfo按钮
			strType = "btnflaginfo";					//为strType变量赋值
			FlagDAO flaginfo = new FlagDAO(ShowInfo.this);// 创建FlagDAO对象
			//获取所有便签信息，并存储到List泛型集合中
			List<TableFlag> listFlags = flaginfo.getScrollData(userID, 0, (int) flaginfo.getCount(userID));
			strInfos = new String[listFlags.size()];	//设置字符串数组的长度
			int n = 0;									//定义一个开始标识
			//遍历List泛型集合
			for (TableFlag tb_flag : listFlags) {
				//将便签相关信息组合成一个字符串，存储到字符串数组的相应位置
				strInfos[n] = tb_flag.get_id() + "|" + tb_flag.getFlag();
				//判断便签信息的长度是否大于15
				if (strInfos[n].length() > 15) {
					//将位置大于15之后的字符串用……代替
					strInfos[n] = strInfos[n].substring(0, 15) + "……";
				}
				n++;//标识加1
			}
			break;
		}
		//使用字符串数组初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);//为ListView列表设置数据源
	}

	@Override
	protected void onRestart() {
		super.onRestart();			//实现基类中的方法
		Showinfo(R.id.btnoutinfo);	//显示支出信息
	}
}