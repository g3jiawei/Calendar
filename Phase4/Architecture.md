We have _three_ components: **UI**, **Backend Classes** and **Server&Database**

* UI
	
	UI is the frontier of our Android app. When user is using our app, the only thing that they will pay attention to is our interface. In that case, we use Material Design to implement our app iCalendar. 

* Backend Classes
	
	Backend classes take the responsibilities of all the data involved in the interactions. There are four backend classes: Assignment.java, Course.java, Student.java and Test.java. 
	
	**Assignment.java** will add/remove all the assignments by assignment's code, name, date and time. 
	
	**Course.java** will add/remove/save all the courses by code and tile. Note: The course name has to be unique. 
	
	**Student.java** will save/load tests/assignments and send requests to our server to fetch data.
	
	**Test.java** will save/remove tests by test code, name, date, from time, to time and location.
	
* Server&Database

	For server, we uses  **CodeIgniter Rest Server** with PHP 5.2. 
	
	For database, we uses **MySQL**.
	
	sServer and database will handle all the data storage operation. 
	
	Both of the server and database are saved in **Google Cloud Platform**.
	
**I will use a real scenario to show how these components interact with each other. **

I am a computer science and math student. When I am using this app(**Android 5.0 is required**), I will fetch existing course from the server first. User name: Group 10; Password: 123456. After I got the data, I noticed that I **CSC309** is not currently in the existing course. So I create a new course 

	code: CSC309	//don't need to worry about capitalization and space
	title: Program on Web

All the information above will be save into a temporary file until the user sent them into server. 
	
After that, I will find it in **CURRENT COURSE**. Then I want to add a **test** on **December 19, 2014**. Choose **CSC309** from the spinner.

	Name: Final
	From: 09:00
	To: 12:00
	Location: EX200
	
Similar to the previous one, all of the test information will be save by calling backend classes. 

After this, you will be able to see it in the **calendar view** and **CURRENT COURSE/CSC309/Tests**
	
**Decision:**

We decided to use save data temporarily into a file and then send them back to the database. By this decision, we don't have to access our server again and again and require users have Internet connection all the time. 

We still believe this is a good decision.
