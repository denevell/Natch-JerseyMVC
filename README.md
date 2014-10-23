Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
-----
~~* Delete post error message~~
~~* Test for reply going to next page~~
* Test shouldn't see delete icon after first page

* Don't show move to thread on first post in thread
* Don't show delete post on first post
* Test go back to homepage from single thread page
* Test content in single thread page
* No next icon when no next page
* Test what happens on 0 start and limit
* Disabled add thread button
* Disabled next / prev button
* Test page indicators
* Keep old add thread data or don't allow when not logged in

Backlog
-------
