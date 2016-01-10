'use strict';

angular.module('jhipstertemplateApp')
	.controller('TimeRegistrationDeleteController', function($scope, $uibModalInstance, entity, TimeRegistration) {

        $scope.timeRegistration = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TimeRegistration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
