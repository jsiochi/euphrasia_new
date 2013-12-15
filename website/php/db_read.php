<?php
	
	$response = array();

	require_once __DIR__ . '/db_connect.php';
	//open a connection
	$db = new DB_CONNECT();

	if(isset($_GET['filter_index'])){
		$index = $_GET['filter_index'];
		$result = array();
		if($index==0){
			//by user
			$filter = $_GET['field'];
			$query = sprintf("SELECT * FROM entries WHERE user_name = '%s'",mysql_real_escape_string($filter));
			$result = mysql_query($query);

		}
		if($index==1){
			//by language
			$filter = $_GET['field'];
			$query = sprintf("SELECT * FROM entries WHERE language = '%s'",mysql_real_escape_string($filter));
			$result = mysql_query($query);

		}
		if($index==2){
			//by title
			$filter = $_GET['field'];
			$query = sprintf("SELECT * FROM entries WHERE title = '%s' OR native_text='$s'",mysql_real_escape_string($filter),mysql_real_escape_string($filter));
			$result = mysql_query($query);

		}
		if($index==3){
			//by get everything
			$query = sprintf("SELECT * FROM entries");
			$result = mysql_query($query);

		}
		if (mysql_num_rows($result) > 0) {
			$response["entries"] = array();
			while($row = mysql_fetch_array($result)){
				$entry_match = array();
				$entry_match["title"] = $row['title'];
				$entry_match['tag'] = $row['tag'];
				$entry_match["native_text"] = $row["native_text"];
				$entry_match["foreign_text"] = $row["foreign_text"];
				$entry_match["date"] = $row["created_at"];
				$entry_match["audio"] = $row["audio"];
				$entry_match["phrasebook"] = $row["phrasebook"];
				$entry_match["language"] = $row["language"];
				$entry_match["user_id"] = $row["user_id"];
				array_push($response["entries"],$entry_match);
			}
			$response["success"] = 1;
			$response["message"] = "Succesfully located remote entries";
			echo json_encode($response);
		}
		else{
			$response["success"] = 0;
			$response["message"] = "No entries match search parameter!";
			echo json_encode($response);
		}

	}
	else{
		$response["success"] = 0;
		$response["message"] = "Missing field! Please specify a filter parameter.";
		echo json_encode($response);
	}

?>