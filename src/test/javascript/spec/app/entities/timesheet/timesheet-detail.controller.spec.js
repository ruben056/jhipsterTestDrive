'use strict';

describe('Controller Tests', function() {

    describe('Timesheet Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTimesheet, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTimesheet = jasmine.createSpy('MockTimesheet');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Timesheet': MockTimesheet,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("TimesheetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipstertemplateApp:timesheetUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
