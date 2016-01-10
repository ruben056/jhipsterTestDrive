'use strict';

angular.module('jhipstertemplateApp').controller('AuthorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Author', 'Book',
        function($scope, $stateParams, $uibModalInstance, entity, Author, Book) {

        $scope.author = entity;
        $scope.books = Book.query();
        $scope.load = function(id) {
            Author.get({id : id}, function(result) {
                $scope.author = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipstertemplateApp:authorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.author.id != null) {
                Author.update($scope.author, onSaveSuccess, onSaveError);
            } else {
                Author.save($scope.author, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForBirhtDate = {};

        $scope.datePickerForBirhtDate.status = {
            opened: false
        };

        $scope.datePickerForBirhtDateOpen = function($event) {
            $scope.datePickerForBirhtDate.status.opened = true;
        };
}]);
