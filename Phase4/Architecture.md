We have _three_ components: **UI**, **Backend Classes** and **Server&Database**

* UI
	
	UI is the frontier of our application. When user using our app, the only thing that they will pay attention to is our interface. In that case, we use Material Design to implement iCalendar. 

* Backend Classes
	
	Backend classes are responsible for all of the data involved interaction. There are four backend classes: Assignment.java, Course.java, Student.java and Test.java. 
	
	**Assignment.java** will add/remove all the assignment with assignment code, name, date and time. 
	
	**Course.java** will add/remove/save all the courses with code and tile. Note: The course name has to be unique. 
	
	**Student.java** will save/load tests/assignments and send request to our server to fetch data.
	
	**Test.java** will save/remove tests with test code, name, date, from time, to time and location.
	
* Server&Database

	The server uses  **CodeIgniter Rest Server** with PHP 5.2. 
	
	The Database uses **MySQL**.
	
	The server and database will handle all the data storage operation. 
	
	All of the server and database are saved in **Google Cloud Platform**.
	
**I will use a real scenario to show how these components interact with each other. **

I am a computer science and math student. When I use this app(**Android 5.0 is required**), I will first fetch existing course from the server. User name: Group 10; Password: 123456. After I get the data, I noticed that I **CSC309** is not currently in the existing course. So I create a new course 

	code: CSC309	//don't need to worry about capitalization and space
	title: Program on Web

All the the above information will be save into a temporary file until user sent them into server. 
	
After that, I will find it in **CURRENT COURSE**. Then I want to add a **test** on **December 19, 2014**. Choose **CSC309** from the spinner.

	Name: Final
	From: 09:00
	To: 12:00
	Location: EX200
	
Similar to the previous one, all of the test information will be save by calling backend classes. 

After this, you are able to see it in the **calendar view** and **CURRENT COURSE/CSC309/Tests**
	
**Decision:**

We decided to use save data temporarily into a file and then send them back to the database. By this decision, we don't have to access our server again and again and require users have Internet connection all the time. 

We still believe this is a good decision.
