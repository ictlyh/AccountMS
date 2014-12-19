<?php
	//定义一个数据库全局变量
	global $db;
	//绑定数据库
	$db = new mysqli("localhost","root","root","account");
	IF (mysqli_connect_errno())
	{
		printf("Database Connect Failed. Error: %s\n",mysqli_connect_error());
		exit();
	}
	$db->query("CREATE TABLE IF NOT EXSISTS tb_pwd (_id VARCHAR(20) PRIMARY KEY,password VARCHAR(20));");
	$db->query("CREATE TABLE IF NOT EXSISTS tb_inaccount (userID VARCHAR(20), _id INTEGER, money DECIMAL, time VARCHAR(10), type VARCHAR(10),handler VARCHAR(100),mark VARCHAR(200),	PRIMARY KEY(userID, _id));");
	$db->query("CREATE TABLE IF NOT EXSISTS tb_outaccount (userID VARCHAR(20), _id INTEGER, money DECIMAL, time VARCHAR(10), type VARCHAR(10), address VARCHAR(100), mark VARCHAR(200),PRIMARY KEY(userID, _id));");
	$db->query("CREATE TABLE IF NOT EXSISTS tb_flag (userID VARCHAR(20), _id INTEGER, flag VARCHAR(200), PRIMARY KEY(userID, _id));");
?>