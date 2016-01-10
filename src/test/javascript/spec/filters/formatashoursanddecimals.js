'use strict';

describe('Filter: formatAsHoursAndDecimals', function () {

  // load the filter's module
  beforeEach(module('jhipstertemplateApp'));

  // initialize a new instance of the filter before each test
  var formatAsHoursAndDecimals;
  beforeEach(inject(function ($filter) {
    formatAsHoursAndDecimals = $filter('formatAsHoursAndDecimals');
  }));


    it('should return the input:"', function() {
        expect(formatAsHoursAndDecimals('3')).toBe('3');
        expect(formatAsHoursAndDecimals('30')).toBe('30');
    });

    it('should return the input with zeros filled out at the end:"', function() {
        expect(formatAsHoursAndDecimals('3,00')).toBe('3,00');
        expect(formatAsHoursAndDecimals('3,05')).toBe('3,05');
        expect(formatAsHoursAndDecimals('3,50')).toBe('3,50');
        expect(formatAsHoursAndDecimals('3,5')).toBe('3,50');

        expect(formatAsHoursAndDecimals('3.00')).toBe('3,00');
        expect(formatAsHoursAndDecimals('3.05')).toBe('3,05');
        expect(formatAsHoursAndDecimals('3.50')).toBe('3,50');
        expect(formatAsHoursAndDecimals('3.5')).toBe('3,50');
    });

    it('should return the input with minutes converted to decimals:"', function() {
        expect(formatAsHoursAndDecimals('3:00')).toBe('3,00');
        expect(formatAsHoursAndDecimals('3:03')).toBe('3,05');
        expect(formatAsHoursAndDecimals('3:30')).toBe('3,50');
        expect(formatAsHoursAndDecimals('3:3')).toBe('3,50');
    });

    // special case: experimatal to ease the input for the user:
    // 3 digits : assumes first digit is hours, the rest is minutes
    // when minitus > 60, an hour is added...
    it('should return a decimal represantation:"', function() {
        expect(formatAsHoursAndDecimals('830')).toBe('8,50');
        expect(formatAsHoursAndDecimals('990')).toBe('10,50');
        expect(formatAsHoursAndDecimals('203')).toBe('2,05');
        expect(formatAsHoursAndDecimals('230')).toBe('2,50');
        expect(formatAsHoursAndDecimals('3:3')).toBe('3,50');
        expect(formatAsHoursAndDecimals('859')).toBe('8,98');
        expect(formatAsHoursAndDecimals('861')).toBe('9,02');
    });

});
