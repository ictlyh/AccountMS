/**
 * Project Name:AccountManagementSystem
 * File Name:BaseActivity.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-17上午10:43:10
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: BaseActivity
 * Function: 退出时关闭所有Activity
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import java.util.LinkedList;
import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	public static LinkedList<Activity> allActivitys = new LinkedList<Activity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		allActivitys.add(this);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		allActivitys.remove(this);
	}
	public static void finishAll(){
		for (Activity activity : allActivitys) {
			activity.finish();
		}
		allActivitys.clear();
		 //这个主要是用来关闭进程的, 光把所有activity finish的话，进程是不会关闭的
		System.exit(0);  
//		android.os.Process.killProcess(android.os.Process.myPid()); 
	}
}