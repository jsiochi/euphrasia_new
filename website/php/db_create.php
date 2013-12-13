<?php
	$response = array();

	if(isset($_POST['foreign_text'])&&isset($_POST['native_text'])&& isset($_POST['title'])){
		$foreign_text = $_POST['foreign_text'];
		$native_text = $_POST['native_text'];
		$title = $_POST['title'];

		require_once __DIR__ . '/db_connect.php';

		$db = new DB_CONNECT();

		$result = mysql_query("INSERT INTO entries(title,native_text,foreign_text) VALUES('$title,$native_text,$foreign_text')");
		
		if($result){
			$response["success"] = 1;
			$response["message"] = "Entry successfully created";
		}
		else{
			$response["success"] = 0;
			$response["message"] = "Failed to create entry";

			echo json_encode($response);
		}	
	}
	else{
		$response["success"] = 0;
		$response["message"] = "Missing required field! 
		Please enter title, native text, and foreign text";
		echo json_encode($response);
	}
?>