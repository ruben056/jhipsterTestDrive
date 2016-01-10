'use strict';

describe('Controller Tests', function() {

    describe('TimeRegistration Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTimeRegistration, MockTimesheet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTimeRegistration = jasmine.createSpy('MockTimeRegistration');
            MockTimesheet = jasmine.createSpy('MockTimesheet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TimeRegistration': MockTimeRegistration,
                'Timesheet': MockTimesheet
            };
            createController = function() {
                $injector.get('$controller')("TimeRegistrationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipstertemplateApp:timeRegistrationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
