Jersey MVC interface to android-manchester.co.uk
================================================

Work in progress. Just beginning. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
-----
* ~~Library to change query parameter in string~~
* ~~Make add thread to new pagination page return a redirected GET~~
* ~~Pagination templates~~
* ~~Keep reset password if dodgy input~~
* ~~Pw reset should be in pwreset template?~~
* ~~Test to show reset password has been successful~~
* ~~Use session state in admin controller~~
* ~~Change pw module tests~~
* Delete thread service code
* Delete thread view for error

Backlog
-------

* Test if recovery email is invalid?
* Test go back to homepage from single thread page
* Test content in single thread page
