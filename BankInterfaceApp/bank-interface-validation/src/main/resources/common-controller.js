'use strict';

var commonController = angular.module('commonController', ['ui.bootstrap']);

   var accountType = $scope.paymentType;
   var creditCardNumber = $scope.creditCard.cardNo1;
   var expiryMonth = $scope.creditCard.expMonth.$viewValue;
   var expiryYear = $scope.creditCard.expYear.$viewValue;
   var nameOnCard = $scope.creditCard.nameOnCard;
   var cardNumberWithoutDashes = creditCardNumber.replace(/-/g,'')
   var validationMessage = $scope.validateCreditCardNumber(accountType, cardNumberWithoutDashes);
   if (validationMessage != "") {
       $scope.financialAccounts.pop();
       $scope.errorMessage = validationMessage;
       return;
   }

   $scope.validateCreditCardNumber = function(accountType, creditCardNumber) {

       creditCardNumber = creditCardNumber.replace('-','');

       var pattern = /^\d+$/;
       if (!pattern.test(creditCardNumber)) {
           return "Your credit card number or type is incorrect, please re-enter";
       }

       var cardDigitsArray = creditCardNumber.split("");
       var numberOfDigits = cardDigitsArray.length;

       if (accountType === "American Express" || accountType === "Amex") {

           if (numberOfDigits != 15) {
               return "Your credit card number or type is incorrect, please re-enter";
           }

           var firstDigits = cardDigitsArray[0] + '' + cardDigitsArray[1];
           if ((firstDigits != "34") && (firstDigits != "37")) {
               return "Your credit card number or type is incorrect, please re-enter";
           }

       } else if (accountType === "Mastercard") {

           if (numberOfDigits != 16) {
               return "Your credit card number or type is incorrect, please re-enter";
           }

           var firstDigits = Number(cardDigitsArray[0] + '' + cardDigitsArray[1]);
           if ((firstDigits < 51) || (firstDigits > 55)) {
               return "Your credit card number or type is incorrect, please re-enter";
           }

       } else if (accountType === "Visa") {

           if ((numberOfDigits != 13) && (numberOfDigits != 16)) {
               return "Your credit card number or type is incorrect, please re-enter";
           }

           var firstDigits = cardDigitsArray[0];
           if (firstDigits != "4") {
               return "Your credit card number or type is incorrect, please re-enter";
           }

       }

       var validCardNumber = $scope.validateCreditCardCheckSum(cardDigitsArray);
       if (!validCardNumber) {
           return "Your credit card number or type is incorrect, please re-enter";
       }

       return "";
   }

   $scope.validateCreditCardCheckSum = function(digits) {

       var sum = 0;
       var length = digits.length;
       for (var i = 0; i < length; i++) {

           // get digits in reverse order
           var digit = Number(digits[length - i - 1]);

           // every 2nd number multiply with 2
           if (i % 2 == 1) {
               digit *= 2;
           }
           sum += digit > 9 ? digit - 9 : digit;
       }
       return sum % 10 == 0;
  }
