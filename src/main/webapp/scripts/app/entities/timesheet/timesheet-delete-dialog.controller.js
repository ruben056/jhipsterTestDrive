'use strict';

angular.module('jhipstertemplateApp')
	.controller('TimesheetDeleteController', function($scope, $uibModalInstance, entity, Timesheet) {

        $scope.timesheet = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Timesheet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
