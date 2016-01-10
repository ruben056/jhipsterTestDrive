'use strict';

angular.module('jhipstertemplateApp')
    .controller('TimeRegistrationDetailController', function ($scope, $rootScope, $stateParams, entity, TimeRegistration, Timesheet) {
        $scope.timeRegistration = entity;
        $scope.load = function (id) {
            TimeRegistration.get({id: id}, function(result) {
                $scope.timeRegistration = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipstertemplateApp:timeRegistrationUpdate', function(event, result) {
            $scope.timeRegistration = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
