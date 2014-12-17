/**
 * Project Name:AccountManagementSystem
 * File Name:MainActivity.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午8:47:22
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: MainActivity
 * Function: 系统主页
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import ac.ucas.accountmanagementsystem.R;

public class MainActivity extends BaseActivity {

	GridView gvInfo;//创建GridView对象
	//定义字符串数组，存储系统功能
	String[] titles = new String[] { "新增支出", "新增收入", "我的支出",
			"我的收入", "数据管理","更改密码", "新增便签", "退出" };
	//定义int数组，存储功能对应的图标
	int[] images = new int[] { R.drawable.addoutaccount, R.drawable.addinaccount,
			R.drawable.outaccountinfo, R.drawable.inaccountinfo,
			R.drawable.showinfo, R.drawable.sysset,
			R.drawable.accountflag, R.drawable.exit };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		gvInfo = (GridView) findViewById(R.id.gvInfo);						//获取布局文件中的gvInfo组件
		pictureAdapter adapter = new pictureAdapter(titles, images, this);	//创建pictureAdapter对象
		gvInfo.setAdapter(adapter);											//为GridView设置数据源
		//为GridView设置项单击事件
		gvInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = null;//创建Intent对象
				switch (arg2) {
				case 0:
					//使用AddOutAccount窗口初始化Intent
					intent = new Intent(MainActivity.this, AddOutAccount.class);
					startActivity(intent);//打开AddOutAccount
					break;
				case 1:
					//使用AddInAccount窗口初始化Intent
					intent = new Intent(MainActivity.this, AddInAccount.class);
					startActivity(intent);//打开AddInAccount
					break;
				case 2:
					//使用OutAccountInfo窗口初始化Intent
					intent = new Intent(MainActivity.this, OutAccountInfo.class);
					startActivity(intent);//打开OutAccountInfo
					break;
				case 3:
					//使用InAccountInfo窗口初始化Intent
					intent = new Intent(MainActivity.this, InAccountInfo.class);
					startActivity(intent);//打开InAccountInfo
					break;
				case 4:
					//使用ShowInfo窗口初始化Intent
					intent = new Intent(MainActivity.this, ShowInfo.class);
					startActivity(intent);//打开ShowInfo
					break;
				case 5:
					//使用Sysset窗口初始化Intent
					intent = new Intent(MainActivity.this, Sysset.class);
					startActivity(intent);//打开Sysset
					break;
				case 6:
					//使用AccountFlag窗口初始化Intent
					intent = new Intent(MainActivity.this, AccountFlag.class);
					startActivity(intent);//打开AccountFlag
					break;
				case 7:
					finishAll();//关闭所有Activity
				}
			}
		});
	}
}

//创建基于BaseAdapter的子类
class pictureAdapter extends BaseAdapter {
	private LayoutInflater inflater;	//创建LayoutInflater对象
	private List<Picture> pictures;		//创建List泛型集合

	//为类创建构造函数
	public pictureAdapter(String[] titles, int[] images, Context context) {
		super();
		pictures = new ArrayList<Picture>();		//初始化泛型集合对象
		inflater = LayoutInflater.from(context);	//初始化LayoutInflater对象
		//遍历图像数组
		for (int i = 0; i < images.length; i++) {
			Picture picture = new Picture(titles[i], images[i]);//使用标题和图像生成Picture对象
			pictures.add(picture);								//将Picture对象添加到泛型集合中
		}
	}

	// 获取泛型集合的长度
	@Override
	public int getCount() {
		//如果泛型集合不为空
		if (null != pictures) {
			return pictures.size();	//返回泛型长度
		} else {
			return 0;				//返回0
		}
	}

	//获取泛型集合指定索引处的项
	@Override
	public Object getItem(int arg0) {
		return pictures.get(arg0);
	}

	//返回泛型集合的索引
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder;	//创建ViewHolder对象
		//判断图像标识是否为空
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.gvitem, null);	//设置图像标识
			viewHolder = new ViewHolder();					//初始化ViewHolder对象
			viewHolder.title = (TextView) arg1.findViewById(R.id.ItemTitle);//设置图像标题
			viewHolder.image = (ImageView) arg1.findViewById(R.id.ItemImage);//设置图像的二进制值
			arg1.setTag(viewHolder);//设置提示
		} else {
			viewHolder = (ViewHolder) arg1.getTag();//设置提示
		}
		viewHolder.title.setText(pictures.get(arg0).getTitle());//设置图像标题
		viewHolder.image.setImageResource(pictures.get(arg0).getImageId());//设置图像的二进制值
		return arg1;//返回图像标识
	}
}

//创建ViewHolder类
class ViewHolder {
	public TextView title;	//创建TextView对象
	public ImageView image;	//创建ImageView对象
}

//创建Picture类
class Picture {
	private String title;	//定义字符串，表示图像标题
	private int imageId;	//定义int变量，表示图像的二进制值

	// 默认构造函数
	public Picture() {
		super();
	}

	//定义有参构造函数
	public Picture(String title, int imageId) {
		super();
		this.title = title;			//图像标题
		this.imageId = imageId;		//图像ID
	}

	//获取图像标题
	public String getTitle() {
		return title;
	}

	//设置图像标题
	public void setTitle(String title) {
		this.title = title;
	}

	//获取图像ID
	public int getImageId() {
		return imageId;
	}

	//设置图像ID
	public void setimageId(int imageId) {
		this.imageId = imageId;
	}
}