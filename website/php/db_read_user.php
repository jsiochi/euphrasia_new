<?php

$response = array();

if(isset($_GET['user_name']) && isset($_GET['pass'])){
	$user_name = $_GET['user_name'];
	$password = $_GET['pass'];
	require_once __DIR__ . '/db_connect.php';

	$db = new DB_CONNECT();
	$query = sprintf("SELECT user_id FROM users WHERE user_name = '%s' AND pass = '%s'",
		mysql_real_escape_string($user_name),mysql_real_escape_string($password));
	$result = mysql_query($query);
	if(!empty($result)){
		if(mysql_num_rows($result)>0){
			$result = mysql_fetch_array($result);
			$product = array();
			$product['user_id'] = $result['user_id'];
			// $product["user_name"] = $result["user_name"];
			// $product['pass'] = $result["pass"];
			$response["success"] = 1;
			$response["product"] = array();
			array_push($response['product'],$product);
		}
		else{
			$response['success'] = 0;
			$response["message"] = "No such user";
		}
	}
}
else{
		$response['success'] = 0;
		$response['message'] = "Missing fields.";
	}
?>