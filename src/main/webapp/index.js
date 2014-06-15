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
        code: "",
        xcode: ["package com.scalawilliam.sygments",
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
        highlighted: "",
        sampled: false
//        sampleSource: "https://github.com/ScalaWilliam/Sygments/blob/master/src/test/scala/com/scalawilliam/sygments/SygmentsServletTest.scala"

    };

    $scope.languages = [
        'Clojure',
        'CoffeeScript',
        'C#',
        'Erlang',
        'F#',
        'Haskell',
        'HTML',
        'Java',
        'JavaScript',
        'Lua',
        'OCaml',
        'Python',
        'Ruby',
        'Scala',
        'XML',
        'XQuery',
        'XSLT'
    ];

    $http.get("samples.xml").success(function(data) {
        var samples = $($.parseXML(data)).find("sample").map(function() {
            return {
                language:$(this).attr("language"),
                source:$(this).attr("source"),
                code: $(this).text()
            };
        }).get();
        samples.sort(Position);
        $scope.samples = samples;
        function Position(a, b) {
            return ($scope.languages.indexOf(a.language) > $scope.languages.indexOf(b.language)) ? 1 : -1;
        }
        $scope.loadSample(samples.filter(function(sample){return sample.language == 'Scala'})[0]);
    });

    $http.get("styles/internal").success(function(data) {
        $scope.styles = $($.parseXML(data)).find("style").map(function() { return $(this).text(); }).get().concat(customStyles);
    });

    $scope.highlight = function() {
        $http.post("highlight/" + encodeURIComponent($scope.state.language), $scope.state.code).success(function(data) {
            $scope.contents = $($.parseXML(data)).find("highlighted").text();
        });
    };

    $scope.loadSample = function(sample) {
        $scope.state.language = sample.language;
        $scope.state.code = sample.code;
        $scope.state.sampled = true;
        $scope.state.sampleSource = sample.source;
        $scope.$apply();
        $scope.highlight();
        // important
        $('#switch-Scala').trigger('change.bs.dropdown.data-api')
    };

    $scope.highlight();

    $scope.triggerSelect = function() {
        $('#file-select').click();
    }
});


