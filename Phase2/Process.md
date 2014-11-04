# Develop Process
#### Team member:
Chuan-Keng Chou

Chidinma Nwaka

Shihao Zhao

Hongyi Guo

Zhonghao Liu

Manke Luo

Jiawei Li

__Scrum master__: _None_ ([reason](#major-decisions))


#### Daily Scrum Meetings (4 meetings in total):
Meeting #1: 27/10 - BA2210

* Job Distribution
	* G1: Deal with server and database (Hongyi)
	* G2: Deal with classes and functions (Jimmy, Chidi and Manke)
	* G3: Deal with UI and interactions (Jiawei, Shihao and Zhonghao)

Meeting #2: 30/11 - BA2210

* server side and database basically finished
	* Hongyi briefly demonstrate how to interact with his database
* we decide to write calendar-view by ourself instead of using build-in Android calendar

Meeting #3: 01/11 - BA2210

* small demonstration of calendar-view in front of groups
	* start to working on calendar-view with "__add test__", "__add assignment__", "__add lecture__" function and "__add new course button__".
* class and functions still working on it
* we wanted to have a back button at the beginning but we realized that we can just use the build-in __back__ button on the phone since we are working on an Android app instead of iOS app

Meeting #4: 03/11 - BA2210

* database linking finished
* after a debate, we decide to have a buffer to temporarily store the data before we fully exit the app

### Use of GitHub Issue Management System
Most of our communication were done via Facebook. However, we do have a few times of using GitHub issue such as the theme issue. (2014.11.4)

### Major Decisions:
* Do not choose to have a specific scrum master. For each meeting, we choose one master and change the master for every meeting
	* The reason for not choosing scrum master is that we decided to have multiple masters and each one of us have a chance to be the leader during the meeting and distribute the task. The advantage is that we will all be familiar with each others' style and make the communication easier.
* Use __PHP__ framework, CodeIgniter as our server instead of node.js
	* Hongyi took CSC309 before and familiar with PHP and most of our team member are currently taking 309 and 343(database). It will be a lot easier to explain the corresponding knowledge of server and database.
* Create another calendar-view for our app instead of using build-in calendar
	* In the future implementation, we want to have _sync_ and _login_ feature and it's not safe to use build-in calendar
* Do not implement a separate __back__ button inside our app
	* Use the build-in __back__ button in Android devices can save a lot of work.
	* Having another button for a existed feature(back) is stupid.
* Use __buffer__ to save the data temporarily
	* As a fail safe in case our app cannot properly connect with our database 
* Demonstration simplification
	* Reason?
* progress on link between server and interface needs to be evaluated.
	* Reason?