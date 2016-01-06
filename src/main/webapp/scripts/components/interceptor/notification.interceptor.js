 'use strict';

angular.module('jhipstertemplateApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-jhipstertemplateApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-jhipstertemplateApp-params')});
                }
                return response;
            }
        };
    });
