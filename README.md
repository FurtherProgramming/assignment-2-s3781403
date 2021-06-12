# Readme for Arub Hotdesk

------

**Course: COSC2391 (Further Programming)**

**Name:** Oliver Hale

**Student Number:** s3781403

------

###Contents

1. <a href="#projectdescription">Project Description</a>
2. <a href="#runproject">How to run the project</a>
3. <a href="#unimfeatures">Unimplemented Features / Bugs</a>
4. <a href="#designchanges">Design change justifications</a>
5. <a href="#challenges">Challenges faced / lessons learned</a>

------
<heading id="projectdescription"></heading>
##Project Description

This project contains a JavaFX application designed to allow users to book desks at their workplace and create accounts. It also allows 
admins to monitor and change booking information and user/employee information. 

It has features for the following:

####General features:

* Login - (Redirects user based on role)
* Users cannot sit in the same seat twice in a row


####Admin Features


* Manage user bookings
  * Accept bookings before the day they start
   * Cancel bookings
* Lockdown seats
  * Can lockdown all seats, use a predefined list of seats, or select specific seats to lock down.
* Add/update/delete other employees/admins
* Generate reports
   * Can generate a CSV report of bookings (based on date)
   * Can generate a CSV report of all employee information
    
####Staff Features

* Register an account (of type staff only)
* Book a seat for a certain date
  * Can't book for past dates
   * Can cancel their booking
   * Can update the seat for their booking
* Reset their password (at login screen)
  * Prompts user for a secret question, if the answer is correct, gives new password.
* 48 hours before a booking, no more modifications to their booking can be made. 
------
<heading id="runproject"></heading>
##How to run the project

**WARNING**: This project has a dependency on the SQLite JDBC to function. Specifically, you require **sqlite-jdbc-3.34.0**.

Place sqlite-jdbc-3.34.0.jar under lib in your project structure, or use intelliJ ideas project structure under file, project structure, libraries, to add the file.
If you don't do this, the program won't be able to run.

The main class for the application is "Main.java", this can be found under src.main.Main. Run this class and the application will handle the rest. 

The project is running using java 8 and javaFX 8.

For your convenience, there are a few employee/admin accounts already created. Their details are:

###Account Information

######Admin
* **Username:** admin
* **Password**: admin

######Staff 
* **Username:** staff
* **Password:** staff

I recommend changing these login details if you plan on using project for anything other than personal/academic use.
It would also be wise to enable encryption of passwords and secret answers (if I haven't implemented that feature upon final submission.)

###Examples of running project

Pictures / Images of the project structure and a few examples of the UI will be given.

------
<heading id="unimfeatures"></heading>
##Unimplemented Features so far (Edit before submit)

* Reset password
* 48 hour test case for modifying bookings
* Test classes and cases for all classes
* Admin add/update/create/delete accounts
* Lockdown based on specific seats, full lockdown, and no lockdown. (only covid spacing so far)
* OO needs improvement for the following files (implementing features before moving to abstraction)
  * BookingManagement and BookingTwo / anything that graphically displays the seats
    * could be moved to another class that the relevant classes extend - need to modify variables first

###Bugs
* User cannot attempt to login more than once, must restart application if wrong information entered.
  * Fix for this is known, just unimplemented until all features are functional.

------
<heading id="designchanges"></heading>
##Design Change Justifications

Justify the following: 
* Wireframes vs actual UI
* Controller changes and model changes over time



------
<heading id="challenges"></heading>

##Challenges and lessons learned

Quick notes to self
* Models and controllers design should be thoroughly considered beforehand
* No real knowledge of JavaFX UI components / complexity of programming some components lead to wireframes that were somewhat
inaccurate compared to end UI.
* Complications due to needing to change OO were tricky to work out
* Better understanding of lambdas, UI design, MVC, structuring and writing methods
* Came across issues with models as a result of not great understanding of SQLite and JDBC.
    * As a result: much better understanding of how to use JDBC and java for db access
    * Feel confident enough to start writing methods without frequently having to look at previous work / tutorials now.
* Workflow felt like it was relatively optimized, working on front end, to back end staff features, to admin features, worked well.
  * Not forcing 100% happiness with each feature and moving on when the base feature was met meant a faster workflow. 
    Leaving TODO's for trickier sections was also good at reminding me what needed to be completed.
  * Could work on one feature for days just trying to perfect the methods and functions, better to move on and come 
    back to it with a fresh mind.
* design of the database was pretty well considered and made writing some code sections easier, only mild changes needed
  to be made to some primary keys or unique values


------

### References and tutorials for Readme (Markdown)
- **IntelliJ IDEA MarkDown guide**. jetbrains.com/help/idea/markdown.html
- **Choose an open source license**. Github. Available at: https://choosealicense.com/
- **Getting started with writing and formatting on Github**. Github. Available at: https://help.github.com/articles/getting-started-with-writing-and-formatting-on-github/
- **Markdown here cheatsheet**. Markdown Here Wiki. Available at: https://github.com/adam-p/markdown-here/wiki/Markdown-Here-Cheatsheet
- **Markdown quick reference**. Wordpress. Available at: https://en.support.wordpress.com/markdown-quick-reference/
- **readme-template**. Dan Bader. Github. Available at: https://github.com/dbader/readme-template
- Writing READMEs. **Udacity**. Available at: https://classroom.udacity.com/courses/ud777/

