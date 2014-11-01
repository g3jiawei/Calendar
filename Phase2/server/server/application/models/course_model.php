<?php
class course_model extends CI_Model {
  function get($id){
    $this->db->where('id', $id);
    $query = $this->db->get('course');
    if ($query && $query->num_rows()>0)
        return $query->row(0,'course');
  }
  function getAll(){
    $query=$this->db->get('course');
    return $query->result();
  }
  
	function createNew($code, $title){
      $course = new Course();
	    $course->code = $code;
	    $course->title = $title;
      return $course;
	}
  
  function insert($course){
    return $this->db->insert('course', $course);
  }
  
  function update($course){
    $this->db->where('id', $course->id);
    return $this->db->update('course', array('code'=>$course->code, 'title'=>$course->title));
  }
  
  function delete($id){
    $this->db->where('id', $id);
    $this->db->delete('course');
  }
}