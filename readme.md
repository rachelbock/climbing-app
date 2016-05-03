# Clamber

The Clamber App is designed to display all of the climbs at The Circuit Bouldering Gym NE and allow for project and completion tracking.

Key Features:
- Project Tracking
- History Tracking
- Recommendations
- User Ratings
- User Comments
- Display of Updated Walls

When Clamber is launched, a login page is displayed.

![login activity](https://raw.githubusercontent.com/rachelbock/climbing-app/master/screenshots/login_page.png)

- The New User Button launches a dialog fragment that collects a username, height and skill level. If the username does not already exist, a User object is created and stored in the Clamber Database. The Home Activity is launched and the User is set there for use throughout the app.
- The Existing User Button launches a dialog fragment that collects a username and checks the clamber database to see if it exists. If it does exist, the Home Activity is launched and the User information is collected from the database and stored in the User object for use throughout the app. In the current version of the app there is no verification of the user.

### Home
The Home Activity sets up and displays the Toolbar and Tabs. It also launches the Home Fragment. This fragment displays the three most recently updated Wall Sections. When one of the Wall Section images is selected, the Climbs Fragment launches with all of the climbs that are on that wall section. 

![home page](https://raw.githubusercontent.com/rachelbock/climbing-app/master/screenshots/home_page.png)

### Walls
The Walls Fragment displays the four main Walls at the gym. When one of the Walls is selected, the Wall Section Fragment is launched. This fragment contains each of the Wall Sections that are on that main Wall. When a Wall Section is selected, the Climbs Fragment is launched with all of the climbs that are on that wall section. 

![walls](https://raw.githubusercontent.com/rachelbock/climbing-app/master/screenshots/walls_page.png)
![wall sections](https://raw.githubusercontent.com/rachelbock/climbing-app/master/screenshots/wall_section_page.png)

The Climbs Fragment displays each climb route that is set up on a Wall Section. The climb’s tape color, grade, and style are listed as well as a checkbox for the user to mark the climb as a project and a checkbox for the user to mark the climb as completed. If the user marks either project or completed, that information is sent to the clamber database.  

![climb row](https://raw.githubusercontent.com/rachelbock/climbing-app/master/screenshots/climb_rows.png)

The climb row also also includes an information button to launch a detail page. The detail page includes two main sections - one for details and the other for comments. 

- The Details section displays an image of the Wall Section that the climb is on, a ratings section and icons to indicate whether the climb is a project or has been completed by the user. The ratings piece of this section allows a user to judge how difficult a climb is. The gym grade is displayed with a section for the user to say whether they felt the climb was harder or easier than the gym’s rating for the climb. There is also a user average displayed to show how other users have rated the climb. 

- The Comments section displays all of the comments on a climb. There is an add comment icon at the bottom of the page that pops up a dialog fragment for a user to enter their own comment. After a comment has been added it is sent to the database and any other users viewing that climb will be able to see the comment.

![climb detail](https://raw.githubusercontent.com/rachelbock/climbing-app/master/screenshots/climb_detail_page.png)

### Projects
The Projects Fragment displays  information on any climb that has been marked as a Project by the user but not as Completed. This fragment also contains a Recommendations Button that launches the Recommendations Fragment. This fragment displays information on any climb that is within plus or minus one of the user defined skill level. In a future version, the user will be able to specify type preferences and recommendations will be narrowed by the type of climb they prefer as well.

![projects](https://raw.githubusercontent.com/rachelbock/climbing-app/master/screenshots/projects_page.png)

### User Info
The User Info Fragment displays the user’s name, height and skill level. There is an Update button that launches a dialog fragment for the user to update their height or skill level. The User Info fragment also has a View History button that launches a list of all of the climbs that have been marked as completed by the user. In a future version, the user will also be able to define climb type preferences from this fragment.   

![user info](https://raw.githubusercontent.com/rachelbock/climbing-app/master/screenshots/history.png)
