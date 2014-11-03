<?php
class assignment_model extends CI_Model {
  function getAll($lecture_id){
    $this->db->where('lecture_id', $lecture_id);
    $query = $this->db->get('assignment');
    return $query->result();
  }
  
  function get($id){
    $this->db->where('id', $id);
    $query = $this->db->get('assignment');
    if ($query && $query->num_rows()>0)
      return $query->row(0,'assignment');
  }
  
	function createNew($lecture_id, $name, $deadline){
    $assignment = new assignment();
	  $assignment->lecture_id = $lecture_id;
	  $assignment->name = $name;
    $assignment->deadline = $deadline;
    return $assignment;
	}
  
  function insert($assignment){
    return $this->db->insert('assignment', $assignment);
  }
  
  function update($assignment){
    $this->db->where('id', $assignment->id);
    return $this->db->update('assignment', array('lecture_id'=>$assignment->lecture_id, 'name'=>$assignment->name, 'deadline'=>$assignment->deadline));
  }
  
  function delete($id){
    $this->db->where('id', $id);
    $this->db->delete('assignment');
  }
  
  function nextAssignment($id){
    $sql = "SELECT * FROM assignment WHERE deadline >= STR_TO_DATE('".date('Y-m-d H:i:s')."', '%Y-%m-%d %H:%i:%s') ORDER BY deadline ASC;";
    $query = $this->db->query($sql);
    if ($query && $query->num_rows()>0){
      return $query->row(0,'assignment');
    }
  }
}