We have _three_ components: **UI**, **Backend Classes** and **Server&Database**

* UI
	
	UI is the frontier of our application. When user using our app, the only thing that they will pay attention to is our interface. In that case, we use Material Design to implement iCalendar. 

* Backend Classes
	
	Backend classes are responsible for all of the data involved interaction. There are four backend classes: Assignment.java, Course.java, Student.java and Test.java. 
	
	**Assignment.java** will add/remove all the assignment with assignment code, name, date and time. 
	
	**Course.java** will add/remove/save all the courses with code and tile. Note: The course name has to be unique. 
	
	**Student.java** will save/load tests/assignments and send request to our server to fetch data.
	
	**Test.java** 
* Server&Database
	
	
	
**I will use a real scenario to show how these components interact with each other. **

Feel free to use diagrams.

Choose one or two of the most significant architecture decisions you've made, and tell us:

What was the reasoning behind these decisions?

Do you still think these were good decisions?

