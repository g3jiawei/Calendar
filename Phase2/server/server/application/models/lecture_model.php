<?php
class lecture_model extends CI_Model {
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
    return $this->db->update('lecture', array('code'=>$lecture->course_id, 'title'=>$lecture->code));
  }
}