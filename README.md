Jersey MVC interface to android-manchester.co.uk
================================================
 
Work in progress. This replaces the old Jsp / SimpleTags version.

You currently need the rest services (See and run Natch-Runner) running on localhost:8080, unless you change the urls.

You can run it with gradle jettyStart but you need jetty-runner-9.1.0.M0.jar in the base dir.

Todo
====

shouldSeeErrorPageOnBlankInput
shouldDeletePost
shouldSeeEditAsAdmin shouldShowEditThreadLink
shouldSeePaginationAndTitlesAndMarkdown
shouldSeeErrorOnEnterOfBlankUsername
shouldSeeEditedTagsAdd
shouldSeeEditedTagsDelete
shouldSeeEditAsAdmin
shouldShowEditThreadLink
shouldSeePaginationAndTitlesAndMarkdown
ThreadAddUITests > shouldShowInputError
* There's a stray null in a schema somewhere

Later
=====

* Delete tag?
* Separate pagination class?
* Remove pagination html from codebase
* Keep old add thread data or don't allow when not logged in
