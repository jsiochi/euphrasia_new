<?php
	$response = array();

	if(isset($_POST['user_name']) && isset($_POST['password']) && isset($_POST['email'])){
		$user_name = $_POST['user_name'];
		$pass = $_POST['password'];
		$email = $_POST['email'];
		
		require_once __DIR__ . '/db_connect.php';

		$db = new DB_CONNECT();

		$result = mysql_query("INSERT INTO users(user_name, pass, user_mail) VALUES('$user_name,$pass,$email')");
		
		if($result){
			$response["success"] = 1;
			$response["message"] = "User successfully created";
		}
		else{
			$response["success"] = 0;
			$response["message"] = "Failed to create new user";

			echo json_encode($response);
		}	
	}
	else{
		$response["success"] = 0;
		$response["message"] = "Missing required field! 
		Please enter user name, email, and password.";
		echo json_encode($response);
	}
?>