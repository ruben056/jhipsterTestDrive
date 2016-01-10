'use strict';

angular.module('jhipstertemplateApp')
    .factory('Author', function ($resource, DateUtils) {
        return $resource('api/authors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.birhtDate = DateUtils.convertLocaleDateFromServer(data.birhtDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.birhtDate = DateUtils.convertLocaleDateToServer(data.birhtDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.birhtDate = DateUtils.convertLocaleDateToServer(data.birhtDate);
                    return angular.toJson(data);
                }
            }
        });
    });
