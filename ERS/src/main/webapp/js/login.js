const d = document;

let app = angular.module('ersApp', []);
app.controller('employeeCtrl', function($scope, $http) {
	$scope.userName = "";
	$scope.password = "";
	$scope.email = "";
	$scope.firstName = "";
	$scope.lastName = "";
	$scope.permission = "";  
	
	$scope.fullName = function() {
		return $scope.firstName + " " + $scope.lastName;
	};
	
	 $scope.attmeptLogin = function() {
		$http.get('/ERS/html/rest/employee/101')
			  .then(function(response) {
			    $scope.employee = response.data;
			  });
	 }
	
	 $scope.attmeptCreate = function() {
	    $http.post('/ERS/html/rest/login', $scope.employee)
			    .then(function(response) {
				    $scope.employee = response.data;
				  });
	 }
});