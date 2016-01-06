'use strict';

angular.module('jhipstertemplateApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


