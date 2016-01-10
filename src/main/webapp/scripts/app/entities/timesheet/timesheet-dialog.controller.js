'use strict';

angular.module('jhipstertemplateApp').controller('TimesheetDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Timesheet', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Timesheet, User) {

        $scope.timesheet = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Timesheet.get({id : id}, function(result) {
                $scope.timesheet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipstertemplateApp:timesheetUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timesheet.id != null) {
                Timesheet.update($scope.timesheet, onSaveSuccess, onSaveError);
            } else {
                Timesheet.save($scope.timesheet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
