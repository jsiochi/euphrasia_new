<?php
	$response = array();

	if(isset($_POST['user_name']) && isset($_POST['pass']) && isset($_POST['user_mail'])){
		$user_name = $_POST['user_name'];
		$pass = $_POST['pass'];
		$email = $_POST['user_mail'];
		
		require_once __DIR__ . '/db_connect.php';

		$db = new DB_CONNECT();
		$query = sprintf("INSERT INTO users(user_name, pass, user_mail) VALUES('%s','%s','%s')",
			mysql_real_escape_string($user_name),mysql_real_escape_string($pass),mysql_real_escape_string($email));
		$result = mysql_query($query);
		
		if($result){
			$response["success"] = 1;
			$response["message"] = "User successfully created";
			echo json_encode($response);
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