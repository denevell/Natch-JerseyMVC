Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

* Keep old add thread data or don't allow when not logged in

Code generation
===============

* Make a servlet that does the same job as PwRequestController
  * Register a servlet programmatically
  * Find out how to do the return mustache from java
* Test
* Generate the code for that servlet from the Jersey post

Later
=====

* Delete tag?
* Separate pagination class?
* Remove pagination html from codebase
