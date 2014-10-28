Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

~~ * Test all again
~~ * Convert post delete 
~~ * Convert post edit
~~ * Convert post move to thread
~~ * Convert post reply
* Convert post single
* Convert thread single`
* Look at getting 303s

* Admin stays on after logout
* Admin logged out server error -- general error message
~~
* Sort and truncate tags in view object?
* Pages per thread in view object?
* Keep old add thread data or don't allow when not logged in
* Register doesn't work when you have a slash at the end of the url 


Later
=====

* Delete tag?
* Separate pagination class?
* Remove pagination html from codebase
* Error messages for annotation processing
* Sort out redirect parsing
* Memory leaks?
* Multiple different posts to same page
* Servlet 400s on missing params, optional params, check for bad conversion
