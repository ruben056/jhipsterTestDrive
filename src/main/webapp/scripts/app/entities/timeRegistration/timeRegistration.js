'use strict';

angular.module('jhipstertemplateApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('timeRegistration', {
                parent: 'entity',
                url: '/timeRegistrations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TimeRegistrations'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeRegistration/timeRegistrations.html',
                        controller: 'TimeRegistrationController'
                    }
                },
                resolve: {
                }
            })
            .state('timeRegistration.detail', {
                parent: 'entity',
                url: '/timeRegistration/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TimeRegistration'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeRegistration/timeRegistration-detail.html',
                        controller: 'TimeRegistrationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TimeRegistration', function($stateParams, TimeRegistration) {
                        return TimeRegistration.get({id : $stateParams.id});
                    }]
                }
            })
            .state('timeRegistration.new', {
                parent: 'timeRegistration',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeRegistration/timeRegistration-dialog.html',
                        controller: 'TimeRegistrationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    rawUserInput: null,
                                    formattedAsTime: null,
                                    formattedAsDecimalHours: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('timeRegistration', null, { reload: true });
                    }, function() {
                        $state.go('timeRegistration');
                    })
                }]
            })
            .state('timeRegistration.edit', {
                parent: 'timeRegistration',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeRegistration/timeRegistration-dialog.html',
                        controller: 'TimeRegistrationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TimeRegistration', function(TimeRegistration) {
                                return TimeRegistration.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeRegistration', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('timeRegistration.delete', {
                parent: 'timeRegistration',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeRegistration/timeRegistration-delete-dialog.html',
                        controller: 'TimeRegistrationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeRegistration', function(TimeRegistration) {
                                return TimeRegistration.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeRegistration', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
