<?php
require(APPPATH . 'libraries/REST_Controller.php');

class Calendar extends REST_Controller {
  function courses_get() {
    $result_array = $this->course_model->getAll();
    $arr['success'] = true;
    $arr['error_code'] = 0;
    $arr['error'] = 'success';
    $arr['courses'] = $result_array;
    $this->response(json_encode($arr));
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
  	if(isset($_GET['id'])){
      $arr['lectures'] = $this->lecture_model->getAll($_GET['id']);
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
    $this->form_validation->set_rules('course_id', 'Course id', 'required|numerical');
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
}