'use strict';

angular.module('jhipstertemplateApp')
    .factory('Timesheet', function ($resource, DateUtils) {
        return $resource('api/timesheets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    console.log("retrieved from server:\n" + JSON.stringify(data, null, 2));
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
