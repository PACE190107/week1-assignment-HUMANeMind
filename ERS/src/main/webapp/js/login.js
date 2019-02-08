let app = angular.module('ersApp', []);
app.controller('employeeCtrl', function($scope, $http) {
	$scope.attmeptLogin = function() {
		if ($scope.employee) {
			$http.post('/ERS/html/rest/employeeLogin', $scope.employee)
			.then(function(response) {
						$scope.employee = response.data;
						if ($scope.employee.permissionId == "2")
							{
								window.location.replace("/ERS/html/manager.html?employeeID=" + $scope.employee.employeeID);
							} else{
								window.location.replace("/ERS/html/employee.html?employeeID=" + $scope.employee.employeeID);
							}						
					});
		}
	}

	$scope.attmeptCreate = function() {
		if ($scope.employee.userName && $scope.employee.password) {
			$http.post('/ERS/html/rest/employeeNew', $scope.employee)
			.then(function(response) {
						$scope.employee = response.data;
						window.location.replace("/ERS/html/employee.html?employeeID=" + $scope.employee.employeeID);
					});
		}
	}

	$scope.logOut = function() {
		location.reload();
	}
});