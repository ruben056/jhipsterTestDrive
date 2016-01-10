'use strict';

angular.module('jhipstertemplateApp')
    .controller('TimesheetController', function ($scope, $state, Timesheet) {

        $scope.timesheets = [];
        $scope.loadAll = function() {
            Timesheet.query(function(result) {
               $scope.timesheets = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.timesheet = {
                description: null,
                month: null,
                year: null,
                id: null
            };
        };
    });
