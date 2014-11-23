CSC301 Project Server API (Phase 4)
=========================
URL: http://dev-firmament-772.appspot.com/index.php/api/

Basically, for the all 4 classes we focus on currently(course, lecture, assignment and lecture_time), we get to create, fetch(get), modify and delete them, plus another method to get the next assignment for a lecture. Use different http methods (GET, PUT, POST, DELETE) to perform different tasks. Be carefull to the URI and the parameters.

When sending POST request, use form content type(application/x-www-form-urlencoded).

In all responses I included success, error_code and error. But you might not need to use the error_code. I'm just thinking it might be useful in the future.

Responses are in JSON format.

Sturcture for the objects:
* Course
    * id (int) ... id
    * code (string) ... course code
    * title (string) ... title
* Lecture
    * id (int) ... id
    * course_id (string) ... the course that the lecture belongs to
    * code (string) ... title, such as "L0101"
* assignment
    * id (int) ... id
    * lecture_id (int) ... the lecture that the assignment belongs to
    * name (string) ... name, such as "Assignment 1"
    * deadline (datetime) ... the deadline which contains both date and time
* test
    * id (int) ... id
    * lecture_id (int) ... the lecture that the assignment belongs to
    * name (string) ... name, such as "Assignment 1"
    * date (date) ... the date of the test
    * start (time) ... the starting time of the test
    * end (time) ... the ending time of the test
* lecture_time
    * id (int) ...id
    * lecture_id (int) ... the lecture that the time belongs to
    * dow (int) ... day of the week. It is an integer from 0 to 6, representing the days of a week from Sunday to Saturday. For example, dow=4 means the lecture happens on Thursday
    * start (time) ... the time the lecture start. such as "14:00"
    * end (time) ... the time the lecture ends.

Database EERDiagram
![Database EERDiagram]
(https://github.com/csc301-fall2014/Proj-Morning-Team10-repo/blob/master/Phase4/server/database/EER%20Diagram.png)

When creating an object you need to provide those fields (except the id which will generate automatically). For the cases that ids of other classes are required, you need to know those ids first.

About time format:
The format of time/date in assignment and lecture_time are different.

Format of deadline in assignment is "Y-m-d H:i:s", for example, "2014-11-03 00:00:00".

dow in lecture_time is just an int.

Format for start and end in lecture_time and test is "H:i:s", for example, "14:00:00". However, "H:i" will also be accepted as input parameters.


The api starts below.

Calendar
--------
###course
* **calendar/courses[GET]**
    * Get all courses
    * URL: http://dev-firmament-772.appspot.com/index.php/api/calendar/courses
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * courses (array of object) ... courses
    * Response example:
    
```
        {"success":true,
        "error_code":0,
        "error":"success",
        "courses":"[{\"id\":1,\"code\":\"CSC301\",\"title\":\"Introduction to Software Engineering\"}]"}
```
* **calendar/course/{id}[GET]**
    * Get course by id
    * URL: http://dev-firmament-772.appspot.com/index.php/api/calendar/course/{id}
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * course (Object) ... the course of that id
* **calendar/course[PUT]**
    * Create a course
    * URL: http://dev-firmament-772.appspot.com/index.php/api/calendar/course
    * Parameters
        * code (string) ... course code
        * title (string) ... course title
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/course/{id}[POST]**
    * Modify a course
    * URL: http://dev-firmament-772.appspot.com/index.php/api/calendar/course/{id}
    * Parameters
        * code (string) ... course code
        * title (string) ... course title
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/course/{id}[DELETE]**
    * Delete a course
    * URL: http://dev-firmament-772.appspot.com/index.php/api/calendar/course/{id}
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message


###lecture
* **calendar/lectures[GET]**
    * Get all lectures for a course
    * Parameters
        * course_id (int) ... course id
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * lectures (array of objects) ... the lectures
* **calendar/lecture/{id}[GET]**
    * Get a lecture by its id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * lecture (Object) ... the lecture
* **calendar/lecture[PUT]**
    * Create a lecture
    * Parameters
        * course_id (int) ... course id
        * code (string) ... lecture code
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/lecture/{id}[POST]**
    * Modify a lecture
    * Parameters
        * course_id (int) ... the course it belongs to
        * code (string) ... lecture code
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/lecture/{id}[DELETE]**
    * Delete a lecture
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message

###assignment
* **calendar/assignments[GET]**
    * Get all assignments for a lecture
    * Parameters
        * lecture_id (int) ... lecture id
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * assignments (array of objects) ... the assignments
* **calendar/assignment/{id}[GET]**
    * Get assignment by its id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * assignment (object) ... the assignment
* **calendar/assignment[PUT]**
    * Create a new assignment
    * Parameters
        * lecure_id (int) ... the lecture it belongs to
        * name (string) ... its name
        * deadline (datetime) ... its deadline
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/assignment/{id}[POST]**
    * Modify an assignment
    * Parameters
        * lecure_id (int) ... the lecture it belongs to
        * name (string) ... its name
        * deadline (datetime) ... its deadline
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/assignment/{id}[DELETE]**
    * Delete an assignment
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message


###test
* **calendar/tests[GET]**
    * Get all tests for a lecture
    * Parameters
        * lecture_id (int) ... lecture id
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * tests (array of objects) ... the tests
* **calendar/test/{id}[GET]**
    * Get test by its id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * test (object) ... the test
* **calendar/test[PUT]**
    * Create a new test
    * Parameters
        * lecure_id (int) ... the lecture it belongs to
        * name (string) ... its name
        * date (date) ... its date
        * start (time) ... its starting time
        * end (time) ... its ending time
        * location (string) ... its location
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/test/{id}[POST]**
    * Modify an test
    * Parameters
        * lecure_id (int) ... the lecture it belongs to
        * name (string) ... its name
        * date (date) ... its date
        * start (time) ... its starting time
        * end (time) ... its ending time
        * location (string) ... its location
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/test/{id}[DELETE]**
    * Delete an test
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message


###lecture_time
* **calendar/lecture_times[GET]**
    * Get all lecture times for a lecture
    * Parameters
        * lecture_id (int) the lecture of which you are checking the time
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * lecture_times (array of objects) ... array of lecture_time objects
* **calendar/lecture_time/{id}[GET]**
    * Get the lecture_time by its id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * lecture_time (object) ... lecture_time object
* **calendar/lecture_time[PUT]**
    * Create a lecture_time
    * Parameters
        * lecture_id (int) ... the lecture
        * dow (int) ... the day of the week represented by an int
        * start (time) ... the start time
        * end (time) ... the end time
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/lecture_time/{id}[POST]**
    * Modify a lecture_time by its id
    * Parameters
        * lecture_id (int) ... the lecture
        * dow (int) ... the day of the week represented by an int
        * start (time) ... the start time
        * end (time) ... the end time
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* **calendar/lecture_time/{id}[DELETE]**
    * Delete a lecture time by its id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message


###next_assignment
* **calendar/next_assignment/{lecture_id}[GET]**
    * Get the next assignment for a lecture
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * next_assignment (object) ... the next assignment
	* Response example
```
		{"success":true,
		"error":"success",
		"error_code":0,
		"next_assignment":{
			"id":"3",
			"lecture_id":"1",
			"name":"A3",
			"deadline":"2014-11-03 00:00:00"}}
```
