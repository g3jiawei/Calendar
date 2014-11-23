<?php
class test_model extends CI_Model {
  function getAll($lecture_id){
    $this->db->where('lecture_id', $lecture_id);
    $query = $this->db->get('test');
    return $query->result();
  }
  
  function get($id){
    $this->db->where('id', $id);
    $query = $this->db->get('test');
    if ($query && $query->num_rows()>0)
      return $query->row(0,'test');
  }
  
	function createNew($lecture_id, $name, $date, $start, $end, $location){
    $test = new test();
	  $test->lecture_id = $lecture_id;
	  $test->name = $name;
    $test->date = $date;
    $test->start = $start;
    $test->end = $end;
    $test->location = $location;
    return $test;
	}
  
  function insert($test){
    return $this->db->insert('test', $test);
  }
  
  function update($test){
    $this->db->where('id', $test->id);
    return $this->db->update('test', array('lecture_id'=>$test->lecture_id, 'name'=>$test->name, 'date'=>$test->date, 'start'=>$test->start, 'end'=>$test->end, 'location'=>$test->location));
  }
  
  function delete($id){
    $this->db->where('id', $id);
    $this->db->delete('test');
  }
  
}