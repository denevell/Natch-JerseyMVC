Servlet MVC interface to android-manchester.co.uk
=================================================
 
This replaces the old Jsp / SimpleTags version, with a Jersey-esq system using annotation processing.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

* Sort out uat script to use this project instead of natch-jsp

Later
=====

* Set input objects to fields not getters and setters
* Sort and truncate tags in view object?
* Pages per thread in view object?
* Bean validation?
* Delete single tag?
* Separate pagination class?
* Remove pagination html from codebase
* Error messages for annotation processing
* Multiple different posts to same page
* Error page for a 404 on a thread / single post
* Keep post content on error
