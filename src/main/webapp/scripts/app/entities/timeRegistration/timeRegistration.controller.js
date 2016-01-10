'use strict';

angular.module('jhipstertemplateApp')
    .controller('TimeRegistrationController', function ($scope, $state, TimeRegistration) {

        $scope.timeRegistrations = [];
        $scope.loadAll = function() {
            TimeRegistration.query(function(result) {
               $scope.timeRegistrations = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.timeRegistration = {
                rawUserInput: null,
                formattedAsTime: null,
                formattedAsDecimalHours: null,
                id: null
            };
        };
    });
