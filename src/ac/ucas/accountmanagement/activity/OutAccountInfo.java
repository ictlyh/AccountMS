/**
 * Project Name:AccountManagementSystem
 * File Name:OutAccountInfo.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午8:58:58
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: OutAccountInfo
 * Function: 支出管理
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
import ac.ucas.accountmanagement.dao.OutAccountDAO;
import ac.ucas.accountmanagement.model.TableOutAccount;
import ac.ucas.accountmanagementsystem.R;

public class OutAccountInfo extends BaseActivity {

	public static final String FLAG = "id";		//定义一个常量，用来作为请求码
	ListView lvinfo;							//创建ListView对象
	String strType = "";						//创建字符串，记录管理类型
	Button btnreturn;							//关联返回按钮
	String userID;								//保存参数userid

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.outaccountinfo);//设置布局文件
		lvinfo = (ListView) findViewById(R.id.lvoutaccountinfo);//获取布局文件中的ListView组件
		btnreturn = (Button) findViewById(R.id.btnoutreturn);	//获取布局文件中的返回按钮
		userID = this.getIntent().getStringExtra("userID");		//获取userID

		ShowInfo(R.id.btnoutinfo);//调用自定义方法显示支出信息

		//为ListView添加项单击事件
		lvinfo.setOnItemClickListener(new OnItemClickListener() {
			//覆写onItemClick方法
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());	//记录支出信息
				String strid = strInfo.substring(0, strInfo.indexOf('|'));		//从支出信息中截取支出编号
				Intent intent = new Intent(OutAccountInfo.this,InfoManagement.class);//创建Intent对象
				intent.putExtra(FLAG, new String[] { strid, strType });			//设置传递数据
				intent.putExtra("userID", userID);
				startActivity(intent);											//执行Intent操作
			}
		});
		
		//为返回按钮设置监听事件
		btnreturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(OutAccountInfo.this, MainActivity.class);
				intent.putExtra("userID", userID);
				startActivity(intent);
			}
		});
	}

	//用来根据传入的管理类型，显示相应的信息
	private void ShowInfo(int intType) {
		String[] strInfos = null;					//定义字符串数组，用来存储支出信息
		ArrayAdapter<String> arrayAdapter = null;	//创建ArrayAdapter对象
		strType = "btnoutinfo";						//为strType变量赋值
		OutAccountDAO outaccountinfo = new OutAccountDAO(OutAccountInfo.this);// 创建OutAccountDAO对象
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
		//使用字符串数组初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);//为ListView列表设置数据源
	}

	@Override
	protected void onRestart() {
		super.onRestart();			//实现基类中的方法
		ShowInfo(R.id.btnoutinfo);	//显示收入信息
	}
}