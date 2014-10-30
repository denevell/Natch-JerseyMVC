Servlet MVC interface to android-manchester.co.uk
=================================================
 
This replaces the old Jsp / SimpleTags version, with a Jersey-esq system using annotation processing.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

~~ * Move into Natch-Runner
~~ * Leave some dependencies as provided
~~ * Change Natch-Runner to start this
* Run test now we've changed paths
* Run tests again when move to an empty context root
* Change the production urls based on manifest
* Generate production version of site using gradle
* Sort out uat script to use this project instead of natch-jsp

Later
=====

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
