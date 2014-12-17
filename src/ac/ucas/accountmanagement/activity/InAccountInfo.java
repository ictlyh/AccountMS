/**
 * Project Name:AccountManagementSystem
 * File Name:InAccountInfo.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午5:48:49
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: InAccountInfo
 * Function: 收入信息
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
import ac.ucas.accountmanagement.dao.InAccountDAO;
import ac.ucas.accountmanagement.model.TableInAccount;
import ac.ucas.accountmanagementsystem.R;

public class InAccountInfo extends BaseActivity {
	
	public static final String FLAG = "id";			//定义一个常量，用来作为请求码
	ListView lvinfo;								//创建ListView对象
	String strType = "";							//创建字符串，记录管理类型
	Button btnreturn;								//关联返回按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inaccountinfo);						//设置布局文件
		lvinfo = (ListView) findViewById(R.id.lvinaccountinfo);		//获取布局文件中的ListView组件
		btnreturn = (Button) findViewById(R.id.btninreturn);		//获取布局文件中的返回按钮
		ShowInfo(R.id.btnininfo);									//调用自定义方法显示收入信息
		lvinfo.setOnItemClickListener(new OnItemClickListener()		//为ListView添加项单击事件
		{
			// 覆写onItemClick方法
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());			//记录收入信息
				String strid = strInfo.substring(0, strInfo.indexOf('|'));				//从收入信息中截取收入编号
				Intent intent = new Intent(InAccountInfo.this, InfoManagement.class);	//创建Intent对象
				intent.putExtra(FLAG, new String[] { strid, strType });					//设置传递数据
				startActivity(intent);													//执行Intent操作
			}
		});
		
		//为返回按钮设置监听事件
		btnreturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(InAccountInfo.this, MainActivity.class));
			}
		});
	}

	//用来根据传入的管理类型，显示相应的信息
	private void ShowInfo(int intType) {
		String[] strInfos = null;						//定义字符串数组，用来存储收入信息
		ArrayAdapter<String> arrayAdapter = null;		//创建ArrayAdapter对象
		strType = "btnininfo";							//为strType变量赋值
		InAccountDAO inaccountinfo = new InAccountDAO(InAccountInfo.this);//创建InAccountDAO对象
		//获取所有收入信息，并存储到List泛型集合中
		List<TableInAccount> listinfos = inaccountinfo.getScrollData(0,
				(int) inaccountinfo.getCount());
		strInfos = new String[listinfos.size()];		//设置字符串数组的长度
		int m = 0;										//定义一个开始标识
		//遍历List泛型集合
		for (TableInAccount tb_inaccount : listinfos) {	
			//将收入相关信息组合成一个字符串，存储到字符串数组的相应位置
			strInfos[m] = tb_inaccount.get_id() + "|" + tb_inaccount.getType()
					+ " " + String.valueOf(tb_inaccount.getMoney()) + "元     "
					+ tb_inaccount.getTime();
			m++;										//标识加1
		}
		//使用字符串数组初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);				//为ListView列表设置数据源
	}

	@Override
	protected void onRestart() {
		super.onRestart();								//实现基类中的方法
		ShowInfo(R.id.btnininfo);						//显示收入信息
	}
}