Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

* Null in request post code thing
* Keep old add thread data or don't allow when not logged in

* Test the code generation with javawrite on using the command line

~~ * Make an annotations jar ~~
~~ * Add the code generation to Eclipse ~~
~~ * Add the code generation source to Eclipse from the Thing annotation ~~
~~ * Generate the servlet class staticially ~~
* Add to real project
* Create the generated source using Gradle
* Test with new generated servlet
* Generate the code for that servlet from the Jersey post

Later
=====

* Delete tag?
* Separate pagination class?
* Remove pagination html from codebase
