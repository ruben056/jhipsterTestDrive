'use strict';

angular.module('jhipstertemplateApp')
    .controller('TimesheetFullViewController', function ($scope, $state, TimeRegistration, entity) {

        $scope.timesheet = entity;
    });
