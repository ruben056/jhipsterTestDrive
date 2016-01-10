'use strict';

angular.module('jhipstertemplateApp')
    .factory('TimeRegistration', function ($resource, DateUtils) {
        return $resource('api/timeRegistrations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
