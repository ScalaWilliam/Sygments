(function(angular, factory) {
    if (typeof define === 'function' && define.amd) {
        define('angular-file-upload', ['angular'], function(angular) {
            return factory(angular);
        });
    } else {
        return factory(angular);
    }
}(angular || null, function(angular) {
    var app = angular.module('angularShowImageBackground', []);
    app.directive('ngBackground', ['$window', function ($window) {
        var helper = {
            support: !!($window.FileReader && $window.CanvasRenderingContext2D),
            isFile: function (item) {
                return angular.isObject(item) && item instanceof $window.File;
            },
            isImage: function (file) {
                var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        };
        return {
            restrict: 'A',
            template: '<img/>',
            link: function (scope, element, attributes) {
                if (!helper.support) return;
                var params = scope.$eval(attributes.ngBackground);
                if (!helper.isFile(params.file)) return;
                if (!helper.isImage(params.file)) return;
                var reader = new FileReader();
                reader.onload = onLoadFile;
                reader.readAsDataURL(params.file);
                function onLoadFile(event) {
                    element.find("img").attr("src", event.target.result);
                }
            }
        };
    }]);

    return app;
}));