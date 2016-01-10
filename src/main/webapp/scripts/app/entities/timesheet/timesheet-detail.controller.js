'use strict';

angular.module('jhipstertemplateApp')
    .controller('TimesheetDetailController', function ($scope, $rootScope, $stateParams, entity, Timesheet, User) {
        $scope.timesheet = entity;
        $scope.load = function (id) {
            Timesheet.get({id: id}, function(result) {
                $scope.timesheet = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipstertemplateApp:timesheetUpdate', function(event, result) {
            $scope.timesheet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
