Servlet MVC interface to android-manchester.co.uk
=================================================
 
This replaces the old Jsp / SimpleTags version, with a Jersey-esq system using annotation processing.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

* Highlight selected page
* Test for tags being too large
* Sort out registering problem

Later
=====

* Why isn't 404 a 404 on the live server?
* Remove annotations in favour of programmatic - test memory use before and after
* Sort and truncate tags in view object?
* Bean validation?
* Delete single tag?
* Error messages for annotation processing
* Multiple different posts to same page
* Error page for a 404 on a thread / single post
* Keep post content on error
