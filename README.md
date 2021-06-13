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
* Add/update/delete other employees/admins. - **Update is unimplemented**
* Generate reports - **Unimplemented**
   * Can generate a CSV report of bookings (based on date)
   * Can generate a CSV report of all employee information
    
####Staff Features

* Register an account (of type staff only)
* Book a seat for a certain date
  * Can't book for past dates
   * Can cancel their booking
   * Can update the seat for their booking
* Reset their password (at login screen) - **Unimplemented**
  * Prompts user for a secret question, if the answer is correct, gives new password.
* 48 hours before a booking, no more modifications to their booking can be made. - **Unimplemented**

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

~~Pictures / Images of the project structure and a few examples of the UI will be given.~~

------
<heading id="unimfeatures"></heading>
##Unimplemented Features so far (Edit before submit)

* Reset password
* 48 hour test case for modifying bookings
* Test classes and cases for all classes
* Admin modify other accounts
* Lockdown based on specific seats (Can lockdown based on three conditions)
* OO needs improvement for a number of files and models.
* Admins generate reports of employee information and bookings

###Bugs
* User cannot attempt to login more than once, must restart application if wrong information entered.
  * Fix for this is known, just unimplemented until all features are functional.

------
<heading id="designchanges"></heading>
##Design Change Justifications

###Wireframe design change justifications

The majority of wireframes and taskflows that were originally created ended up being changed in some way. 
While they looked alright, the issue with a number of them was the complexity that their design features added to
the code.

There was quite a few popup windows, animations, confirmation windows, etc. Which added an extra degree of difficulty
that unfortunately in this iteration of the project was unable to be completed.

Over time, the FXML files featured changes, some were deleted as the features that they offered could be met 
in other pages, and some were added as they offered improved functionality.

There are still some pages at time of submission (sunday night) that have unfortunately not been fully completed.


###Controller / model changes over time

Throughout the creation of this program the controllers and models underwent several changes from my originally intended design.

One example is the booking controller, this was originally just ONE controller for three pages. I learned quickly that having
three controllers, and therefore three initialize() made things much easier. It also cleaned up the code, and made it a little 
less messy.

Another example is the addition of more controllers for things, like the scene controller, which was added to help
draw new scenes and also to draw AlertBoxes. 

The controllers also ended up being split into two different folders, admin and staff. To me this made things easier
to read and comprehend which files were doing what. 

There are still some empty controllers in the final submission (unless extension until Monday is granted), and these 
would have either offered additional functionality that was missed in the final increment, or improved the OO of my program.

The number one most important one of these to me is the DrawSeatsController, which would have helped by allowing
BookingTwoController, ManageBookingController and the adminLockdownBookingController to extend it and reduce the amount of
similar code between them. 



------
<heading id="challenges"></heading>

##Challenges and lessons learned

###Challenges

* Models and controllers design should be thoroughly considered beforehand
* No real knowledge of JavaFX UI components / complexity of programming some components lead to wireframes that were somewhat
inaccurate compared to end UI.
* Complications due to needing to change OO were tricky to work out
  * This is the one that I wish I'd had more time to complete, as I know what I'd do to fix the design of my project.
  For example: the bookings controller mentioned earlier, some utility functions like redirecting, and the registration feature 
    all could have been improved with greater consideration by me beforehand
* Designing the UI -> Currently, I feel as my UI is very functional. That is to say, not exceedingly pretty. With more time and my now better knowledge
of using javaFX and sceneBuilder, I would definitely change quite a few things.
    * I also regret not getting around to adding images to my pages, as this is something I learned how to do but didn't implement as I
  hadn't met base functionality. 
  

###Lessons learned

* Better understanding of lambdas, UI design, MVC, structuring and writing methods.
  * I felt like over time, especially when I began coding the admin side of things, my knowledge of how 
  to structure the code and how to implement features was so greatly increased. I learnt where errors would come from, 
    was able to identify things much quicker that could potentially cause problems. There's a lot that I'd change about
    it still, but I'm relatively happy with how what I did complete ended up turning out.

* Came across issues with models as a result of not great understanding of SQLite and JDBC.
    * As a result: much better understanding of how to use JDBC and java for db access
    * Feel confident enough to start writing methods without frequently having to look at previous work / tutorials now.
    * At the start, I had to look at tutorials/google things/look at previously created models, now I feel reasonably comfortable
     in writing a model / method in a model without looking at anything else.
      * This also includes learning where errors/bugs are coming from
* One of the main, most frustrating things for me, especially at the start, was receiving errors such as:
  preparedStatement null pointer, database connection closed. Now I am able to identify why those are happening
  and figure out ways to fix them. (Unfortunately, toward the end of the project, when I was rushing a little, my solution
  to these was a little dirty and fast).
* Workflow felt like it was relatively optimized, working on front end, to back end staff features, to admin features, worked well.
  * Not forcing 100% happiness with each feature and moving on when the base feature was met meant a faster workflow. 
    Leaving TODO's for trickier sections was also good at reminding me what needed to be completed.
  * Could work on one feature for days just trying to perfect the methods and functions, better to move on and come 
    back to it with a fresh mind.
  * That being said, I also learned the importance of starting things earlier. If i'd started the project a week or so before I did, 
  I would've had much more time to work on things and be happy with what I had. 
    
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

