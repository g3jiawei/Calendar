<?php
class lecture_time_model extends CI_Model {
  function getAll($lecture_id){
    $this->db->where('lecture_id', $lecture_id);
    $query = $this->db->get('lecture_time');
    return $query->result;
  }
  
  function get($id){
    $this->db->where('id', $id);
    $query = $this->db->get('lecture_time');
    if ($query && $query->num_rows()>0)
        return $query->row(0,'lecture_time');
  }
  
	function createNew($lecture_id, $dow, $start, $end){
    $lecture_time = new lecture_time();
	  $lecture_time->lecture_id = $lecture_id;
	  $lecture_time->dow = $dow;
    $lecture_time->start = $start;
    $lecture_time->end = $end;
    return $lecture_time;
	}
  
  function insert($lecture_time){
    return $this->db->insert('lecture_time', $lecture_time);
  }
  
  function update($lecture_time){
    $this->db->where('id', $lecture_time->id);
    return $this->db->update('lecture_time', array('lecture_id'=>$lecture_time->lecture_id, 'dow'=>$lecture_time->dow, 'start'=>$lecture_time->start, 'end'=>$lecture_time->end));
  }
  
  function delete($id){
    $this->db->where('id', $id);
    $this->db->delete('lecture_time');
  }
}