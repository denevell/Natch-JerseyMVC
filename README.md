Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

~~ * 400 etc error page
~~ * 400, 500 error logging
~~ * Look at getting 303s
* Register (and others) doesn't work when you have a slash at the end of the url 

* Do I need the 'index' on the main page?
* Sort out redirect parsing
* Keep old add thread data or don't allow when not logged in
* Basic memory leak test
* Sort out uat script to use this project instead of natch-jsp

Later
=====

* Delete single tag?
* Separate pagination class?
* Remove pagination html from codebase
* Error messages for annotation processing
* Multiple different posts to same page
* Bean validation?
* Sort and truncate tags in view object?
* Pages per thread in view object?
* Better way of concatenating mustache templates
