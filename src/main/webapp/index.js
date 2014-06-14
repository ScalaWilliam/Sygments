var sygmentsApp = angular.module('yay', ['ui.bootstrap', "ui.bootstrap.tpls", "ngSanitize", "angularFileUpload", "angularShowImageBackground"]);

sygmentsApp.controller('Root', function($scope, $http) {
    $scope.currentStyle = {
        id : "monokai"
    };
});
sygmentsApp.controller('Sygments', function($scope, $http, $fileUploader) {

    // Define any custom styles here. The extra ones don't get returned by the /styles endpoint.
    var customStyles = [];

    $scope.uploader = $fileUploader.create({
        scope: $scope,
        url: undefined,
        formData: [{ key: 'value' }],
        filters: []
    });

    $scope.state = {
        code: ["package com.scalawilliam.sygments",
            "",
            "import org.scalatest.{FunSuite, FunSuiteLike, Matchers}",
            "import org.scalatra.test.scalatest.ScalatraSuite",
            "",
            "class SygmentsServletTest extends FunSuite with Matchers with ScalatraSuite with FunSuiteLike  {",
            "  addServlet(classOf[SygmentsServlet], \"/*\")",
            "  test(\"That Scala code can be highlighted\") {",
            "    post(\"/highlight/scala\", \"import test._\".getBytes) {",
            "      body should include(\"<highlighted>\")",
            "    }",
            "  }",
            "}"].join("\n"),
        language: "Scala",
        highlighted: ""
    };

    $scope.languages = [
        'Scala',
        'JavaScript',
        'C#',
        'Java',
        'CoffeeScript',
        'XSLT',
        'XML',
        'XQuery',
        'F#',
        'Python',
        'HTML',
        'Ruby',
        'Haskell'
    ];


    $http.get("styles/internal").success(function(data) {
        $scope.styles = $($.parseXML(data)).find("style").map(function() { return $(this).text(); }).get().concat(customStyles);
    });

    $scope.highlight = function() {
        $http.post("highlight/" + encodeURIComponent($scope.state.language), $scope.state.code).success(function(data) {
            $scope.contents = $($.parseXML(data)).find("highlighted").text();
        });
    }

    $scope.highlight();

    $scope.triggerSelect = function() {
        $('#file-select').click();
    }
});


