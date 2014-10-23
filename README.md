Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
-----
* Style tag on single thread page
* When no next page, next item goes to current
* When no prev page, prev item goes to current
* Should see number of paginationed posts on new posts

* Test content in single thread page
* Test what happens on 0 start and limit
* Disabled add thread button
* Disabled next / prev button
* Keep old add thread data or don't allow when not logged in

Backlog
-------
