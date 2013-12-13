<?php

	$response = array();
	
	if(isset($_POST("user_id"))){
		$user_id = $_POST["user_id"];

		require_once __DIR__ . '/db_connect.php';

		$db = new DB_CONNECT();
		//delete everything from database for this user
		$result = mysql_query("DELETE FROM entry WHERE user_id='$user_id'");
		if($result){
			$response["success"] = 1;
			$response["message"] = "All entries removed for user.";
		}
		else{
			$response["success"] = 0;
			$response["message"] = "Failed to clear entries for user.";

			echo json_encode($response);
		}
		else{
		$response["success"] = 0;
		$response["message"] = "Cannot locate user id!";
		echo json_encode($response);
	}	
	}
?>