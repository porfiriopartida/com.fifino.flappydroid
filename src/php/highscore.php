<?php
header('Access-Control-Allow-Origin: *');
class HighScore{
	protected $filename = "highscore.txt";
	public function read(){
		$myfile = fopen($this->filename, "r") or die("Unable to open file!");
		$size = filesize($this->filename);
		if($size){
			$res = fread($myfile,$size);
			fclose($myfile);
			return $res;
		}
		return "";
	}
	public function write(){
		$jsonScore = $this->read();
		$scoreObject = json_decode($jsonScore);
		$currentHighscore = $scoreObject->highscore;
		$postHighscore = $_POST['highscore'];
		$response = $_POST;
		if($postHighscore > $currentHighscore ){
			$myfile = fopen($this->filename, "w") or die("Unable to open file!");
			$data = json_encode($_POST);
			fwrite($myfile, $data);
			fclose($myfile);
		} else {
			$response['highscore'] = $currentHighscore;
		}
		$data = json_encode($response);
		return $data;
	}
}
$highScore = new HighScore();
if(sizeof($_POST) > 0){
	echo $highScore->write();
} else{
	echo $highScore->read();
}
exit(0);
