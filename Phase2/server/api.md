CSC301 Project Server API
=========================
URL: {BASE_URL}/index.php/api/

Basically, for the all 4 classes we focus on currently(course, lecture, assignment and lecture_time), we get to create, fetch(get), modify and delete them, plus another method to get the next assignment for a lecture.

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
* lecture_time
    * id (int) ...id
    * lecture_id (int) ... the lecture that the time belongs to
    * dow (int) ... day of the week. It is an integer from 0 to 6, representing the days of a week from Sunday to Saturday. For example, dow=4 means the lecture happens on Thursday
    * start (time) ... the time the lecture start. such as "14:00"
    * end (time) ... the time the lecture ends.

When creating an object you need to provide those fields (except the id which will generate automatically). For the cases that ids of other classes are required, you need to know those ids first.

The api starts.

Calendar
--------
###calendar/course
* ###calendar/course[GET]
    * Get all courses
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * courses (array of object) ... courses
    * Response example:
    

        {"success":true,
        "error_code":0,
        "error":"success",
        "courses":"[{\"id\":1,\"code\":\"CSC301\",\"title\":\"Introduction to Software Engineering\"}]"}
* ###calendar/course/{id}[GET]
    * Get course by id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * course (Object) ... the course of that id
* ###calendar/course[PUT]
    * Create a course
    * Parameters
        * code (string) ... course code
        * title (string) ... course title
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* ###calendar/course/{id}[POST]
    * Modify a course
    * Parameters
        * code (string) ... course code
        * title (string) ... course title
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* ###calendar/course/{id}[DELETE]
    * Selete a course
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message


###calendar/lecture
* ###calendar/lecture[GET]
    * Get all lectures for a course
    * Parameters
        * course_id (int) ... course id
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * lectures (array of objects) ... the lectures
* ###calendar/lecture/{id}[GET]
    * Get a lecture by its id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * lecture (Object) ... the lecture
* ###calendar/lecture[PUT]
    * Create a lecture
    * Parameters
        * course_id (int) ... course id
        * code (string) ... lecture code
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* ###calendar/lecture/{id}[POST]
    * Modify a lecture
    * Parameters
        * course_id (int) ... the course it belongs to
        * code (string) ... lecture code
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* ###calendar/lecture/{id}[DELETE]
    * Delete a lecture
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message

###calendar/assignment
* ###calendar/assignment[GET]
    * Get all assignments for a lecture
    * Parameters
        * lecture_id (int) ... lecture id
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * assignments (array of objects) ... the assignments
* ###calendar/assignment/{id}[GET]
    * Get assignment by its id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * assignment (object) ... the assignment
* ###calendar/assignment[PUT]
    * Create a new assignment
    * Parameters
        * lecure_id (int) ... the lecture it belongs to
        * name (string) ... its name
        * deadline (datetime) ... its deadline
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* ###calendar/assignment/{id}[POST]
    * Modify an assignment
    * Parameters
        * lecure_id (int) ... the lecture it belongs to
        * name (string) ... its name
        * deadline (datetime) ... its deadline
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
* ###calendar/assignment/{id}[DELETE]
    * Delete an assignment
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message

###calendar/lecture_time
* ###calendar/lecture_time[GET]
    * Get all lecture times for a lecture
    * Parameters
        * lecture_id (int) the lecture of which you are checking the time
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * lecture_times (array of objects) ... array of lecture_time objects
* ###calendar/lecture_time/{id}[GET]
    * Get the lecture_time by its id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * lecture_time (object) ... lecture_time object
* ###calendar/lecture_time[PUT]
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
* ###calendar/lecture_time/{id}[POST]
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
* ###calendar/lecture_time/{id}[DELETE]
    * Delete a lecture time by its id
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message


###calendar/next_assignment
* ###calendar/next_assignment/{lecture_id}[GET]
    * Get the next assignment for a lecture
    * no parameters
    * Response
        * success (boolean) ... success or not
        * error_code (int) ... error code
        * error (string) ... error_message
        * next_assignment_id (int) ... the id of the next assignment

