<?php
	$response = array();

	//if(!empty($_POST['foreign_text'])&&!empty($_POST['native_text'])&& !empty($_POST['title'])){
	//if(1==1) {
		$foreign_text = $_POST['foreign_text'];
		$native_text = $_POST['native_text'];
		$title = $_POST['title'];
		$user_id = $_POST['user_id'];
		$tags = $_POST['tag'];
		$phrasebook = $_POST['phrasebook'];
		$language = $_POST['language'];
		$date = $_POST['date'];
		$audio = $_POST['audio'];
		require_once __DIR__ . '/db_connect.php';

		$db = new DB_CONNECT();

		$result = mysql_query("INSERT INTO entries(title,native_text,foreign_text,user_id,tag,phrasebook,language,created_at,audio) VALUES('$title,$native_text,$foreign_text,$user_id,$tag,$phrasebook,$language,$date,$audio')");
		
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
	// else{
// 		$response["success"] = 0;
// 		$response["message"] = "Missing required field! 
// 		Please enter title, native text, and foreign text";
// 		echo json_encode($response);
// 	}
	?>