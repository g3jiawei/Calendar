<?php
require(APPPATH . 'libraries/REST_Controller.php');
error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
class Calendar extends REST_Controller {
  function courses_get() {
    $result_array = $this->course_model->getAll();
    $arr['success'] = true;
    $arr['error_code'] = 0;
    $arr['error'] = 'success';
    $arr['courses'] = $result_array;
    $this->response($arr);
  }
  
  function course_get($id) {
    $result = $this->course_model->get($id);
    if(!is_null($result)){
      $arr['success'] = true;
      $arr['error_code'] = 0;
      $arr['error'] = 'success';
      $arr['course'] = $result;
    }else{
      $arr['success'] = false;
      $arr['error_code'] = 1;
      $arr['error'] = 'No such course';
    }
    $this->response($arr);
  }
  
  function course_put(){
    $_POST = $this->put();
    $this->load->library('form_validation');
    $this->form_validation->set_rules('code', 'Course Code', 'required');
    if($this->form_validation->run()){
      $course = $this->course_model->createNew($_POST['code'], $_POST['title']);
      $this->course_model->insert($course);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "code is required.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function course_post($id){
    $this->load->library('form_validation');
    $this->form_validation->set_rules('code', 'Course Code', 'required');
    if($this->form_validation->run()){
      $course = new Course();
      $course->id = $id;
      $course->code = $_POST['code'];
      $course->title = $_POST['title'];
      $this->course_model->update($course);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "code is required.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function course_delete($id){
    $this->course_model->delete($id);
    $arr['success'] = true;
    $arr['error'] = 'success';
    $arr['error_code'] = 0;
    $this->response($arr);
  }
  
  function lectures_get(){
  	if(isset($_GET['course_id'])){
      $arr['lectures'] = $this->lecture_model->getAll($_GET['course_id']);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = 'course_id is required';
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function lecture_get($id){
    $result = $this->lecture_model->get($id);
    if(!is_null($result)){
      $arr['success'] = true;
      $arr['error_code'] = 0;
      $arr['error'] = 'success';
      $arr['lecture'] = $result;
    }else{
      $arr['success'] = false;
      $arr['error_code'] = 1;
      $arr['error'] = 'No such lecture';
    }
    $this->response($arr);
  }
  
  function lecture_put(){
    $_POST = $this->put();
    $this->load->library('form_validation');
    $this->form_validation->set_rules('course_id', 'Course id', 'required|numeric');
    $this->form_validation->set_rules('code', 'Lecture Code', 'required');
    if($this->form_validation->run()){
      $lecture = $this->lecture_model->createNew($_POST['course_id'], $_POST['code']);
      $this->lecture_model->insert($lecture);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "Parameters are not valid.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function lecture_post($id){
    $this->load->library('form_validation');
    $this->form_validation->set_rules('course_id', 'Course id', 'required|numeric');
    $this->form_validation->set_rules('code', 'Lecture Code', 'required');
    if($this->form_validation->run()){
      $lecture = $this->lecture_model->createNew($_POST['course_id'], $_POST['code']);
      $lecture->id = $id;
      $this->course_model->update($lecture);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "Parameters are not valid.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function lecture_delete($id){
    $this->lecture_model->delete($id);
    $arr['success'] = true;
    $arr['error'] = 'success';
    $arr['error_code'] = 0;
    $this->response($arr);
  }
  
  function assignments_get(){
  	if(isset($_GET['lecture_id'])){
      $arr['assignments'] = $this->assignment_model->getAll($_GET['lecture_id']);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = 'lecture_id is required';
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function assignment_get($id){
    $result = $this->assignment_model->get($id);
    if(!is_null($result)){
      $arr['success'] = true;
      $arr['error_code'] = 0;
      $arr['error'] = 'success';
      $arr['assignment'] = $result;
    }else{
      $arr['success'] = false;
      $arr['error_code'] = 1;
      $arr['error'] = 'No such assignment';
    }
    $this->response($arr);
  }
  
  function assignment_put(){
    $_POST = $this->put();
    $this->load->library('form_validation');
    $this->form_validation->set_rules('lecture_id', 'Lecture id', 'required|numeric');
    $this->form_validation->set_rules('name', 'Assignment Name', 'required');
    //$this->form_validation->set_rules('deadline', 'Assignment deadline', 'required');
    if($this->form_validation->run()){
      $assignment = $this->assignment_model->createNew($_POST['lecture_id'], $_POST['name'], $_POST['deadline']);
      $this->assignment_model->insert($assignment);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "Parameters are not valid.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function assignment_post($id){
    $this->load->library('form_validation');
    $this->form_validation->set_rules('lecture_id', 'Lecture id', 'required|numeric');
    $this->form_validation->set_rules('name', 'Day of the Week', 'required');
    //$this->form_validation->set_rules('deadline', 'deadlineing time', 'required');
    if($this->form_validation->run()){
      $assignment = $this->assignment_model->createNew($_POST['lecture_id'], $_POST['name'], $_POST['deadline']);
      $assignment->id = $id;
      $this->assignment_model->update($assignment);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "Parameters are not valid.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function assignment_delete($id){
    $this->assignment_model->delete($id);
    $arr['success'] = true;
    $arr['error'] = 'success';
    $arr['error_code'] = 0;
    $this->response($arr);
  }
  
  function tests_get(){
  	if(isset($_GET['lecture_id'])){
      $arr['tests'] = $this->test_model->getAll($_GET['lecture_id']);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = 'lecture_id is required';
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function test_get($id){
    $result = $this->test_model->get($id);
    if(!is_null($result)){
      $arr['success'] = true;
      $arr['error_code'] = 0;
      $arr['error'] = 'success';
      $arr['test'] = $result;
    }else{
      $arr['success'] = false;
      $arr['error_code'] = 1;
      $arr['error'] = 'No such test';
    }
    $this->response($arr);
  }
  
  function test_put(){
    $_POST = $this->put();
    $this->load->library('form_validation');
    $this->form_validation->set_rules('lecture_id', 'Lecture id', 'required|numeric');
    $this->form_validation->set_rules('name', 'test Name', 'required');
    if($this->form_validation->run()){
      $test = $this->test_model->createNew($_POST['lecture_id'], $_POST['name'], $_POST['date'], $_POST['start'], $_POST['end'], $_POST['location']);
      $this->test_model->insert($test);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "Parameters are not valid.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function test_post($id){
    $this->load->library('form_validation');
    $this->form_validation->set_rules('lecture_id', 'Lecture id', 'required|numeric');
    $this->form_validation->set_rules('name', 'Day of the Week', 'required');
    if($this->form_validation->run()){
      $test = $this->test_model->createNew($_POST['lecture_id'], $_POST['name'], $_POST['date'], $_POST['start'], $_POST['end'], $_POST['location']);
      $test->id = $id;
      $this->test_model->update($test);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "Parameters are not valid.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function test_delete($id){
    $this->test_model->delete($id);
    $arr['success'] = true;
    $arr['error'] = 'success';
    $arr['error_code'] = 0;
    $this->response($arr);
  }
  
  
  
  function lecture_times_get(){
  	if(isset($_GET['lecture_id'])){
      $arr['lecture_times'] = $this->lecture_time_model->getAll($_GET['lecture_id']);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = 'lecture_id is required';
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function lecture_time_get($id){
    $result = $this->lecture_time_model->get($id);
    if(!is_null($result)){
      $arr['success'] = true;
      $arr['error_code'] = 0;
      $arr['error'] = 'success';
      $arr['lecture_time'] = $result;
    }else{
      $arr['success'] = false;
      $arr['error_code'] = 1;
      $arr['error'] = 'No such lecture_time';
    }
    $this->response($arr);
  }
  
  function lecture_time_put(){
    $_POST = $this->put();
    $this->load->library('form_validation');
    $this->form_validation->set_rules('lecture_id', 'Lecture id', 'required|numeric');
    $this->form_validation->set_rules('dow', 'Day of the Week', 'required|numeric');
    $this->form_validation->set_rules('start', 'Starting time', 'required');
    $this->form_validation->set_rules('end', 'Finishing time', 'required');
    if($this->form_validation->run()){
      $lecture_time = $this->lecture_time_model->createNew($_POST['lecture_id'], $_POST['dow'], $_POST['start'], $_POST['end']);
      $this->lecture_time_model->insert($lecture_time);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "Parameters are not valid.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function lecture_time_post($id){
    $this->load->library('form_validation');
    $this->form_validation->set_rules('lecture_id', 'Lecture id', 'required|numeric');
    $this->form_validation->set_rules('dow', 'Day of the Week', 'required|numeric');
    $this->form_validation->set_rules('start', 'Starting time', 'required');
    $this->form_validation->set_rules('end', 'Finishing time', 'required');
    if($this->form_validation->run()){
      $lecture_time = $this->lecture_time_model->createNew($_POST['lecture_id'], $_POST['dow'], $_POST['start'], $_POST['end']);
      $lecture_time->id = $id;
      $this->course_model->update($lecture_time);
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
    }else{
      $arr['success'] = false;
      $arr['error'] = "Parameters are not valid.";
      $arr['error_code'] = 1;
    }
    $this->response($arr);
  }
  
  function lecture_time_delete($id){
    $this->lecture_time_model->delete($id);
    $arr['success'] = true;
    $arr['error'] = 'success';
    $arr['error_code'] = 0;
    $this->response($arr);
  }
  
  function next_assignment_get($lecture_id){
    $dt = date('Y-m-d H:i:s');
    $assignment=$this->assignment_model->nextAssignment($lecture_id);
    if(!isset($assignment)){
      $arr['success'] = false;
      $arr['error'] = "No next assignment for this lecture.";
      $arr['error_code'] = 1;
    }else{
      $arr['success'] = true;
      $arr['error'] = 'success';
      $arr['error_code'] = 0;
      $arr['next_assignment'] = $assignment;
    }
    $this->response($arr);
  }
}