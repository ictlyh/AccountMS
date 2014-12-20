/**
 * Project Name:AccountManagementSystem
 * File Name:Login.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-15下午8:42:26
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: Login
 * Function: 登录系统
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ac.ucas.accountmanagement.dao.PwdDAO;
import ac.ucas.accountmanagement.model.TablePassword;
import ac.ucas.accountmanagementsystem.R;

public class Login extends BaseActivity {
	
	EditText txtloginname, txtloginpwd;			//创建两个EditText对象
	Button btnlogin, btnregist, btnclose;		//创建两个Button对象
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);								//设置布局文件

		txtloginname = (EditText) findViewById(R.id.txtLoginName);	//获取用户名文本框
		txtloginpwd = (EditText) findViewById(R.id.txtLoginPwd);	//获取密码文本框
		btnlogin = (Button) findViewById(R.id.btnLogin);			//获取登录按钮
		btnregist = (Button) findViewById(R.id.btnRegist);			//获取注册按钮
		btnclose = (Button) findViewById(R.id.btnClose);			//获取退出按钮

		//为登录按钮设置监听事件
		btnlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Login.this, MainActivity.class);	//创建Intent对象
				PwdDAO pwdDAO = new PwdDAO(Login.this);						//创建PwdDAO对象
				
				//判断用户名和密码是否为空
				if(txtloginpwd.getText().toString().length() == 0 ||
						txtloginname.getText().toString().length() == 0) {
					Toast.makeText(Login.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
				}
				//判断是否有此用户名
				else if(pwdDAO.find(txtloginname.getText().toString()) == null) {
					Toast.makeText(Login.this, "用户名不存在", Toast.LENGTH_SHORT).show();
				}
				//判断密码是否正确
				else if(pwdDAO.find(txtloginname.getText().toString()).getPassword().equals(txtloginpwd.getText().toString())) {
					// 创建一个新线程，用于向服务器注册用户
					new Thread(new Runnable() {
						public void run() {
							register(txtloginname.getText().toString(), txtloginpwd.getText().toString());
							Message m = handler.obtainMessage(); // 获取一个Message
							handler.sendMessage(m); // 发送消息
						}
					}).start(); // 开启线程
					intent.putExtra("userID", txtloginname.getText().toString());
					startActivity(intent);
				}
				else {
					Toast.makeText(Login.this, "密码错误", Toast.LENGTH_SHORT).show();
				}
				txtloginname.setText("");	//清空用户名文本框
				txtloginpwd.setText("");	//清空密码文本框
			}
		});

		//为注册按钮设置监听事件
		btnregist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Login.this, MainActivity.class);	//创建Intent对象
				PwdDAO pwdDAO = new PwdDAO(Login.this);						//创建PwdDAO对象
				
				//判断用户名和密码是否为空
				if(txtloginpwd.getText().toString().length() == 0 ||
						txtloginname.getText().toString().length() == 0) {
					Toast.makeText(Login.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
				}
				//判断是否有此用户名
				else if(pwdDAO.find(txtloginname.getText().toString()) != null) {
					Toast.makeText(Login.this, "用户名已存在", Toast.LENGTH_SHORT).show();
				}
				//插入用户名和密码到数据库
				else {
					pwdDAO.add(new TablePassword(txtloginname.getText().toString(), txtloginpwd.getText().toString()));
					// 创建一个新线程，用于向服务器注册用户
					new Thread(new Runnable() {
						public void run() {
							register(txtloginname.getText().toString(), txtloginpwd.getText().toString());
							Message m = handler.obtainMessage(); // 获取一个Message
							handler.sendMessage(m); // 发送消息
						}
					}).start(); // 开启线程
					Toast.makeText(Login.this, "注册成功", Toast.LENGTH_SHORT).show();
					intent.putExtra("userID", txtloginname.getText().toString());
					startActivity(intent);
				}
				txtloginname.setText("");	//清空用户名文本框
				txtloginpwd.setText("");	//清空密码文本框
			}
		});
		
		//为退出按钮设置监听事件
		btnclose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finishAll();//退出当前程序
			}
		});
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
			}
		};
	}
	
	private void register(String id, String pwd) {
		String target = "http://124.16.78.167:8080/accountms/handler.php";
		URL url;
		try{
			url = new URL(target);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); 	// 创建一个HTTP连接
			urlConn.setRequestMethod("POST"); 										// 指定使用POST请求方式
			urlConn.setDoInput(true); 												// 向连接中写入数据
			urlConn.setDoOutput(true); 												// 从连接中读取数据
			urlConn.setUseCaches(false); 											// 禁止缓存
			urlConn.setInstanceFollowRedirects(true);								//自动执行HTTP重定向
			urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); // 设置内容类型
			DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
			String param = "userID=" + id + "&pwd=" + pwd + "&type=regist";			//连接要提交的数据
			out.writeBytes(param);													//将要传递的数据写入数据输出流
			out.flush();	//输出缓存
			out.close();	//关闭数据输出流
			// 判断是否响应成功
			if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//Log.d("Login", "Connection error,upload userid and pwd to server next time");
				InputStreamReader in = new InputStreamReader(urlConn.getInputStream()); // 获得读取的内容
				BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
				String inputLine = null;
				String result = "";
				while ((inputLine = buffer.readLine()) != null) {
					result += inputLine + "\n";
				}
				Log.d("Login", result);
				in.close();	//关闭字符输入流
			}
			urlConn.disconnect();//断开连接
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}