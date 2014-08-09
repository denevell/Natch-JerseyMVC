Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. Just beginning. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
-----
* Don't show move to thread on first post in thread
* Don't show delete post on first post
* Shouldn't see delete icon after first page
* Delete post error message
* Test go back to homepage from single thread page
* Test content in single thread page
* No next icon when no next page
* Test what happens on 0 start and limit
* Goto end of thread on replying.
~~
* Create admin toggle service
* Update admin template for toggle (inc form)
* Add post method to toggle the admin priv
* Test it manually
* Test 1
* Test 2

Backlog
-------
