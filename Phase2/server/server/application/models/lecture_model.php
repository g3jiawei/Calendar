<?php
class lecture_model extends CI_Model {
  function getAll($course_id){
    $this->db->where('course_id', $course_id);
    $query = $this->db->get('lecture');
    return $query->result();
  }
  
  function get($id){
    $this->db->where('id', $id);
    $query = $this->db->get('lecture');
    if ($query && $query->num_rows()>0)
        return $query->row(0,'lecture');
  }
  
	function createNew($course_id, $code){
    $lecture = new Lecture();
	  $lecture->course_id = $course_id;
	  $lecture->code = $code;
    return $lecture;
	}
  
  function insert($lecture){
    return $this->db->insert('lecture', $lecture);
  }
  
  function update($lecture){
    $this->db->where('id', $lecture->id);
    return $this->db->update('lecture', array('course_id'=>$lecture->course_id, 'code'=>$lecture->code));
  }
  
  function delete($id){
    $this->db->where('id', $id);
    $this->db->delete('lecture');
  }
}