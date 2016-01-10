'use strict';

angular.module('jhipstertemplateApp').controller('TimeRegistrationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimeRegistration', 'Timesheet',
        function($scope, $stateParams, $uibModalInstance, entity, TimeRegistration, Timesheet) {

        $scope.timeRegistration = entity;
        $scope.timesheets = Timesheet.query();
        $scope.load = function(id) {
            TimeRegistration.get({id : id}, function(result) {
                $scope.timeRegistration = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipstertemplateApp:timeRegistrationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timeRegistration.id != null) {
                TimeRegistration.update($scope.timeRegistration, onSaveSuccess, onSaveError);
            } else {
                TimeRegistration.save($scope.timeRegistration, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
