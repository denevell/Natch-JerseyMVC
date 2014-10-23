Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

~~ * Should see number of paginationed posts on new threads 
~~ * Should see number of paginationed posts on new posts. 

* Null on login with no query strings from single thread
* Keep old add thread data or don't allow when not logged in

Next
===

* Look up annotation processor to create code
* Move a controller to a getter / setter generated class
* Move rest of them

Later
=====

* Delete tag?
* Separate pagination class?
* Remove pagination html from codebase
