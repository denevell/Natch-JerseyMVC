Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

~~ * Upload the annotation processor repo
~~ * Test project again
~~ * Use new repo as a dependency
~~ * Use new servlet generator on register
~~ * Use new servlet generator on logout
~~ * Use new servlet generator on login
~~ * Use new servlet generator on admin
~~ * Use new servlet generator on thread delete
* Use new servlet generator on threads
* Look at implementing the path param

* Keep old add thread data or don't allow when not logged in
* Admin stays on after logout
* Register doesn't work when you have a slash at the end of the url 
* Admin logged out server error

Later
=====

* Delete tag?
* Separate pagination class?
* Remove pagination html from codebase
* Error messages for annotation processing
* Sort out redirect parsing
* Memory leaks?
* Multiple different posts to same page
