<?php
require "./database.php";

if(isset($_POST['userID']) && isset($_POST['type']))
{
	if($_POST['type'] == 'download'])
	{
		exportToFile($_POST['userID']);
	}
	else if($_POST['type'] == 'upload' && $_FILES)
	{
		move_uploaded_file($_FILES['file']["tmp_name"],$_FILES["file"]["name"]);
		$db->query("DELETE FROM tb_inaccount WHERE userID = '".$_POST['userID']."'");
		$db->query("DELETE FROM tb_outaccount WHERE userID = '".$_POST['userID']."'");
		$db->query("DELETE FROM tb_flag WHERE userID = '".$_POST['userID']."'");
		importFromFile();
	}
	else if(isset($_POST['pwd']))
	{
		$db->query("INSERT INTO tb_pwd VALUES('".addslashes($_POST['userID'])."','".addslashes($_POST['pwd'])."')");
	}
}

function exportToFile($userid)
{
	$dem = ',';
	$fp = fopen("tmp.txt","w");
	$res = $db->query("SELECT * FROM tb_inaccount WHERE userID = '$userid' ORDER BY userID;");
	$num = mysql_num_rows($res);
	fwrite($fp, strval($num)."\n");
	while($row = $res->fetch_row())
	{
		$line = $row[0] + $dem + strval($row[1]) + $dem + strval($row[2]) + $dem + $row[3] + $dem + $row[4] + $dem + $row[5] + $dem + $row[6] + $dem + "\n";
		fwrite($fp, $line);
	}
	
	$res = $db->query("SELECT * FROM tb_outaccount WHERE userID = '$userid' ORDER BY userID;");
	$num = mysql_num_rows($res);
	fwrite($fp, strval($num)."\n");
	while($row = $res->fetch_row())
	{
		$line = $row[0] + $dem + strval($row[1]) + $dem + strval($row[2]) + $dem + $row[3] + $dem + $row[4] + $dem + $row[5] + $dem + $row[6] + $dem + "\n";
		fwrite($fp, $line);
	}
	
	$res = $db->query("SELECT * FROM tb_flag WHERE userID = '$userid' ORDER BY userID;");
	$num = mysql_num_rows($res);
	fwrite($fp, strval($num)."\n");
	while($row = $res->fetch_row())
	{
		$line = $row[0] + $dem + strval($row[1]) + $dem + strval($row[2]) + $dem + $row[3] + $dem + "\n";
		fwrite($fp, $line);
	}
}
function importFromFile()
{
	$dem = ',';
	$fp = fopen("tmp.txt","r");
	$num = intval(fgets($fp));
	for($i = 0; $i < $num; $i++)
	{
		$line = fgets($fp);
		$begin = 0;
		$end = strpos($line, $dem, $begin);
		$userid = substr($line, $begin, $end - $begin);
		$begin++;
		$end = strpos($line, $dem, $begin);
		$id = intval(substr($line, $begin, $end - $begin));
		$begin++;
		$end = strpos($line, $dem, $begin);
		$money = floatval(substr($line, $begin, $end - $begin));
		$begin++;
		$end = strpos($line, $dem, $begin);
		$time = substr($line, $begin, $end - $begin);
		$begin++;
		$end = strpos($line, $dem, $begin);
		$type = substr($line, $begin, $end - $begin);
		$begin++;
		$end = strpos($line, $dem, $begin);
		$handler = substr($line, $begin, $end - $begin);
		$begin++;
		$end = strpos($line, $dem, $begin);
		$mark = substr($line, $begin, $end - $begin);
		$db->query("INSERT INTO tb_inaccount VALUES('$userid','$id','$money','$time','$type','$handler','$mark');");
	}
	
	$num = intval(fgets($fp));
	for($i = 0; $i < $num; $i++)
	{
		$line = fgets($fp);
		$begin = 0;
		$end = strpos($line, $dem, $begin);
		$userid = substr($line, $begin, $end - $begin);
		$begin++;
		$end = strpos($line, $dem, $begin);
		$id = intval(substr($line, $begin, $end - $begin));
		$begin++;
		$end = strpos($line, $dem, $begin);
		$money = floatval(substr($line, $begin, $end - $begin));
		$begin++;
		$end = strpos($line, $dem, $begin);
		$time = substr($line, $begin, $end - $begin);
		$begin++;
		$end = strpos($line, $dem, $begin);
		$type = substr($line, $begin, $end - $begin);
		$begin++;
		$end = strpos($line, $dem, $begin);
		$address = substr($line, $begin, $end - $begin);
		$begin++;
		$end = strpos($line, $dem, $begin);
		$mark = substr($line, $begin, $end - $begin);
		$db->query("INSERT INTO tb_outaccount VALUES('$userid','$id','$money','$time','$type','$address','$mark');");
	}
	
	$num = intval(fgets($fp));
	$line = fgets($fp);
	for($i = 0; $i < $num; $i++)
	{
		$begin = 0;
		$end = strpos($line, $dem, $begin);
		$userid = substr($line, $begin, $end - $begin);
		$begin++;
		$end = strpos($line, $dem, $begin);
		$id = intval(substr($line, $begin, $end - $begin));
		$begin++;
		$end = strpos($line, $dem, $begin);
		$flag = substr($line, $begin, $end - $begin);
		$db->query("INSERT INTO tb_flag VALUES('$userid','$id','$flag');");
	}
}
?>