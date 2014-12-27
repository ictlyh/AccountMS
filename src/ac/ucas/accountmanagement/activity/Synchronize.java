/**
 * Project Name:AccountManagementSystem
 * File Name:Synchronize.java
 * Package Name:ac.ucas.accountmanagement.activity
 * Date:2014-12-18下午12:23:54
 * Copyright (c) 2014, luoyuanhao@software.ict.ac.cn All Rights Reserved.
 */
/**
 * ClassName: Synchronize
 * Function: 同步服务器数据库
 * @author yhluo
 * @version 
 */

package ac.ucas.accountmanagement.activity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ac.ucas.accountmanagement.dao.DBOpenHelper;
import ac.ucas.accountmanagementsystem.R;

public class Synchronize extends BaseActivity {
	
	static String HOST = "http://115.28.137.207/accountms/";
	static String PHPHandler = "handler.php";
	static String DIR = Environment.getExternalStorageDirectory().getPath() + "/";
	static String FILENAME = "tmp.txt";
	Button btnsynupload;		//上传到服务器按钮
	Button btnsyndownload;		//从服务器同步按钮
	Button btnsynback;			//返回按钮
	String userID;				//保存参数userid
	Handler handler;			//
	int flag = 0;				//标记是否成功

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.synchronize);						//设置布局文件
		btnsynupload = (Button) findViewById(R.id.btnsynupload);	//获取上传到服务器按钮
		btnsyndownload = (Button) findViewById(R.id.btnsyndownload);//获取从服务器同步按钮
		btnsynback = (Button) findViewById(R.id.btnsynback);		//获取返回按钮
		userID = this.getIntent().getStringExtra("userID");			//获取userID

		//为上传到服务器按钮添加监听事件
		btnsynupload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(new Runnable() {
					public void run() {
						DBOpenHelper db = new DBOpenHelper(Synchronize.this);
						db.exportToFile(userID, DIR + FILENAME);
						uploadFile(userID, DIR + FILENAME);
						Message m = handler.obtainMessage(); // 获取一个Message
						handler.sendMessage(m); // 发送消息
					}
				}).start();
			}
		});

		//为从服务器同步按钮添加监听事件
		btnsyndownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(new Runnable() {
					public void run() {
						String target = HOST + PHPHandler;
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
							String param = "userID=" + userID + "&type=download";					//连接要提交的数据
							out.writeBytes(param);													//将要传递的数据写入数据输出流
							out.flush();	//输出缓存
							out.close();	//关闭数据输出流
							// 判断是否响应成功
							if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
								String sourceUrl = HOST + FILENAME;
								downloadFile(userID, sourceUrl);
								DBOpenHelper db = new DBOpenHelper(Synchronize.this);
								db.importFromFile(userID, DIR + FILENAME);
								Message m = handler.obtainMessage(); // 获取一个Message
								handler.sendMessage(m); // 发送消息
							} else {
								flag = 5;
							}
							urlConn.disconnect();//断开连接
						} catch (MalformedURLException e) {
							flag = 5;
							e.printStackTrace();
						} catch (IOException e) {
							flag = 5;
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		
		//为返回按钮添加监听事件
		btnsynback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//创建Intent对象
				Intent intent = new Intent(new Intent(Synchronize.this, MainActivity.class));
				intent.putExtra("userID", userID);
				startActivity(intent);
			}
		});
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (flag == 1) {
					Toast.makeText(Synchronize.this, "同步至服务器成功",Toast.LENGTH_SHORT).show();
				} else if (flag == 2) {
					Toast.makeText(Synchronize.this, "同步至服务器失败",Toast.LENGTH_SHORT).show();
				} else if(flag == 3) {
					Toast.makeText(Synchronize.this, "从服务器同步成功",Toast.LENGTH_SHORT).show();
				} else if(flag == 4) {
					Toast.makeText(Synchronize.this, "从服务器同步失败",Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(Synchronize.this, "同步失败,请检查网络连接",Toast.LENGTH_SHORT).show();
				}
				super.handleMessage(msg);
			}
		};
	}
	
	//上传文件到服务器
	private void uploadFile(String userId, String fileName) {
        File file = new File(fileName);		// 要上传的文件
        String host = HOST + PHPHandler;	//要上传的带参数的服务器地址
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(host);
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        reqEntity.addPart("file", new FileBody (file));
        httppost.setEntity(reqEntity);
        HttpResponse response;
        HttpEntity resEntity = null;
        try {
			response = httpclient.execute(httppost);
			resEntity = response.getEntity();
			flag = 1;
		} catch (ClientProtocolException e) {
			flag = 2;
		} catch (IOException e) {
			flag = 2;
		}
    }
	
	//从服务器下载文件
	private void downloadFile(String userId, String sourceUrl) {
		try {
			//创建下载地址对应的URL对象
			URL url = new URL(sourceUrl);
			// 创建一个连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 获取输入流对象
			InputStream is = conn.getInputStream();
			if (is != null) {
				File file = new File(DIR + FILENAME);
				// 创建一个文件输出流对象
				FileOutputStream fos = new FileOutputStream(file);
				byte buf[] = new byte[128];
				// 读取文件到输出流对象中
				int len;
				while ((len = is.read(buf)) != -1) {
						fos.write(buf, 0, len);
				}
				fos.close();
			}
			is.close(); 		// 关闭输入流对象
			flag = 3;
			conn.disconnect(); 	// 关闭连接
		} catch(MalformedURLException e) {
			flag = 4;
		} catch(SocketTimeoutException e) {
			flag = 4;
		} catch(IOException e) {
			flag = 4;
		}
	}
}