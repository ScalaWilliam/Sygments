# Scala William's Sygments: Visual Pygments for Scala

## What is it?

Sygments is a syntax highlighting service and HTML5 app that uses [Pygments](http://pygments.org/).

Pygments is an excellent program syntax highlighter developed in Python.

Sygments lets you try different Pygments themes against a range of programming languages, including custom backgrounds.

It is especially suited for creating presentations.

See the demo at [Scala William's Sygments](https://www.scalawilliam.com/1406/sygments) website.

## Sample

Here is a sample rendering. It looks lovely:

![A cut out from the slides](theme.png)

## What problem does it solve?

I originally created part of this application when working on the presentation of the [XML Processing in Scala paper](http://tinyurl.com/XMLLondon2014-XMLScala) I co-authored with [@fancellu](https://github.com/fancellu/).

Syntax highlighter themes do not work well with custom backgrounds. I had chosen a background in Google Presentations but it did not work nicely with IntelliJ's highlighting for XQuery and Scala.

1. Finding the right syntax highlighting theme for the background you've chosen

2. Highlighting your code so it is ready to be copy-and-pasted straight into Google Presentation or other targets

Out of the initial manual work, I created this nice usable app.

## Instructions

1. Install Java and Maven
2. In a terminal, run: ```mvn clean compile test jetty:start -Djetty.port=7874```
3. Go to [http://localhost:7874/](http://localhost:7874/)

### Reverse-proxying 

If you want to host this behind an nginx reverse proxy and need to configure a context path, then add the ```-DcontextPath=/some/place``` to the above line of code.

## Performance

Highlighter interface performance:

![Interface performance](performance.png)

## Technology stack

  * HTML5
  * AJAX
  * REST
  * jetty-maven-plugin
  * Front-end:
    * AngularJS
    * Webjars
    * Bootstrap
    * Angular-UI
    * jQuery
    * [angular-file-upload](https://github.com/danialfarid/angular-file-upload)
    * [Bootswatch](http://bootswatch.com/darkly/)
    * [Bootstrap enhancement](http://behigh.github.io/bootstrap_dropdowns_enhancement/)
  * Back-end:
    * Scala
    * scala-maven-plugin
    * scala-xml
    * Jetty
    * Jython
    * [Pygments](http://pygments.org/)
    * [Scalactic](http://scalatest.org/release_notes/2.2.0)
  * Test:
    * ScalaTest
    * [scalatra-scalatest](http://www.scalatra.org/2.3/guides/testing/scalatest.html)
    * [scalatest-maven-plugin](http://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin)

## Copyrights

Please refer to source material for copyright detail. The intention of this code is fair use, attributing authors via a web link.

Sygments back end code is available under the MIT license.
