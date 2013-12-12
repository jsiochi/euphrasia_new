<?php
/*
Class file to connect to database
*/

class DB_CONNECT {
	//use with: $db = new DB_CONNECT();
	//creates object and opens connection to database

	//constructor
	function __construct(){
		$this->connect();
	}

	//destructor
	function __destruct(){
		$this->close();
	}

	function connect(){
		//import db connection variables
		require_once __DIR__ . '/db_config.php';

		//connect to database
		$con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) 
			or die(mysql_error());
		
		//select database
		$db = mysql_select_db(DB_DATABASE) or die(mysql_error()) 
			or die(mysql_error());
		
		//return connection cursor
		return $con;
	}

	function close(){
		mysql_close();
	}
}
?>