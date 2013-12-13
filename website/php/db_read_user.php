<?php

$response = array();

if(isset($_GET['user_name']) && isset($_GET['password'])){
	$user_name = $_POST['user_name'];
	$password = $_POST['password'];
	require_once __DIR__ . '/db_connect.php';

	$db = new DB_CONNECT();
	$result = mysql_query("SELECT *FROM users WHERE user_name = '$user_name' AND pass = '$password'" );
	if(!empty($result)){
		if(mysql_num_rows($result)>0){
			$result = mysql_fetch_array($result);
			$product = array();
			$product['user_id'] = $result['user_id'];
			$product["user_name"] = $result["user_name"];
			$product['password'] = $result["pass"];
			$response["success"] = 1;
			$response["product"] = array();
			array_push($response['product'],$product);
		}
		else{
			$response['success'] = 0;
			$response["message"] = "No such user";
		}
	}
	else{
		$response['success'] = 0;
		$response['message'] = "Missing fields.";
	}
}
?>