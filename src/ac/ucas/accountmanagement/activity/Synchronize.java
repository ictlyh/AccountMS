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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ac.ucas.accountmanagement.dao.DBOpenHelper;
import ac.ucas.accountmanagementsystem.R;

public class Synchronize extends BaseActivity {
	
	static String HOST = "http://host:port/accountms/upload/";
	//static String DIR = "/mnt/sdcard/";
	static String DIR = Environment.getExternalStorageDirectory().getPath() + "/";
	static String FILENAME = "tmp.txt";
	Button btnsynupload;		//上传到服务器按钮
	Button btnsyndownload;		//从服务器同步按钮
	Button btnsynback;			//返回按钮
	String userID;				//保存参数userid

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
				DBOpenHelper db = new DBOpenHelper(Synchronize.this);
				db.exportToFile(userID, DIR + FILENAME);
				//uploadFile(userID, DIR + FILENAME);
				Toast.makeText(Synchronize.this, "已同步到服务器", Toast.LENGTH_SHORT).show();
			}
		});

		//为从服务器同步按钮添加监听事件
		btnsyndownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String sourceUrl = HOST + FILENAME;
				//downloadFile(userID, sourceUrl);
				DBOpenHelper db = new DBOpenHelper(Synchronize.this);
				db.importFromFile(userID, DIR + FILENAME);
				Toast.makeText(Synchronize.this, "已和服务器同步", Toast.LENGTH_SHORT).show();
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
	}
	
	//上传文件到服务器
	private void uploadFile(String userId, String fileName) {
        String BOUNDARY = "---------------------------7db1c523809b2";//数据分割线
        File file = new File(fileName);   // 要上传的文件
        String host = HOST + "param?userID=" + userId; //要上传的带参数的服务器地址
        
        try {
            //byte[] after = ("--" + BOUNDARY + "--\n").getBytes("UTF-8");
            
            // 构造URL和Connection
            URL url = new URL(host);            
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            
            // 设置HTTP协议的头属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.setRequestProperty("Content-Length", String.valueOf(file.length()));
            conn.setRequestProperty("HOST", url.getHost());
            conn.setDoOutput(true);
            
            // 得到Connection的OutputStream流，准备写数据
            OutputStream out = conn.getOutputStream();
            InputStream in = new FileInputStream(file);
            
            // 写文件数据。因为服务器地址已经带有参数了，所以这里只要直接写入文件部分就可以了。
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            // 数据结束标志，整个HTTP报文就构造结束了。
            //out.write(after);
            in.close();
            out.close();
            //Log.d("Synchronize.uploadFile():", "uploadFile 返回码为: " + conn.getResponseCode());
            //Log.d("Synchronize.uploadFile():", "uploadFile 返回信息为: " + conn.getResponseMessage());   
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	//从服务器下载文件
	private void downloadFile(String userId, String sourceUrl) {
		try {
			//创建下载地址对应的URL对象
			URL url = new URL(sourceUrl);
			// 创建一个连接
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			// 获取输入流对象
			InputStream is = urlConn.getInputStream();
			if (is != null) {
				// 获取文件的扩展名
				String expandName = sourceUrl.substring(sourceUrl.lastIndexOf(".") + 1, sourceUrl.length()).toLowerCase();
				// 获取文件名
				String fileName = sourceUrl.substring(sourceUrl.lastIndexOf("/") + 1, sourceUrl.lastIndexOf("."));
				// 在SD卡上创建文件
				File file = new File(Environment.getExternalStorageDirectory().getPath() + fileName + "." + expandName);
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
			is.close(); 			// 关闭输入流对象
			urlConn.disconnect(); 	// 关闭连接
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
