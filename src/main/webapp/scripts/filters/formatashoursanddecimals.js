'use strict';

/**
 * @ngdoc filter
 * @name jhipstertemplateApp.filter:formatAsHoursAndDecimals
 * @function
 * @description
 * # formatAsHoursAndDecimals
 * Filter in the jhipstertemplateApp.
 */
angular.module('jhipstertemplateApp')
  .filter('formatAsHoursAndDecimals', function () {
    return function (val) {

        if (val == undefined || val.length == 0) {
            return;
        }

        var twoDigits = new RegExp("^[0-9]{1,2}$");
        if (twoDigits.test(val)) {
            return val.toString();
        }

        var threeDigits = new RegExp("^[0-9]{3}$");
        if (threeDigits.test(val)) {
            var part1 = val.substring(0, 1);
            var part2 = val.substring(1, 3);
            if (part2 >= 60) {
                part1 = Number(part1) + 1;
                part2 = part2 - 60;
            }
            part2 = Math.round((part2 / 6 * 10));
            if (part2.toString().length == 1) {
                part2 = "0" + part2
            }
            return part1 + "," + part2;
        }

        var twoGroups = new RegExp("^([0-9]{1,2})([:.,]{1})([0-9]{1,2})$");
        if (twoGroups.test(val)) {
            var matcher = twoGroups.exec(val);
            var part1 = matcher[1];
            var separator = matcher[2];
            var part2 = matcher[3];

            // input allready decimal : only fill out zeros
            if ("," == separator || "." == separator) {
                if (part2.toString().length == 1) {
                    part2 = part2 + "0";
                }
                return part1 + "," + part2;
            }


            if (part2.toString().length == 1) {
                part2 = part2 + "0"
            }
            part2 = Math.round((part2 / 6 * 10));
            if (part2.toString().length == 1) {
                part2 = "0" + part2
            }
            return part1 + "," + part2;
        }

        console.log("No valid value : " + val);
        return;
    };
  });
