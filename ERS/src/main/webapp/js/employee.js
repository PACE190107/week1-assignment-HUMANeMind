let app = angular.module('ersApp', []);
app.controller('formCtrl', function($scope, $http){
	 $scope.logOut = function() {		    
		 window.location.replace("/ERS/html/login.html");    
		 }
	 
	if (!$scope.employee){
		 let vars = {};
		    let parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
		        vars[key] = value;
		    });
		    
		    $scope.employee = vars;
	}
	
	$http.post('/ERS/html/rest/employeeFind', $scope.employee)
    .then(function(response) {
	    $scope.employee = response.data;
	  })
	
	 $scope.attmeptEdit = function() {
		 $http.put('/ERS/html/rest/employeeUpdate', $scope.employee)
		    .then(function(response) {
			    console.log(response.data);
			  })
	 }

	$http.post('/ERS/html/rest/reimbursementByEmployee', $scope.employee)
    .then(function(response) {
	    $scope.reimbursements = response.data;
	  })	 
	
	$scope.statuses = [
		{status: "Pending", id: "1"},
		{status: "Approved", id: "2"},
		{status: "Denied", id: "3"}
		]
	
	$scope.statusIDs = $scope.statuses[0]
	
	$scope.headers = [
		{name: "Reimbursement ID"},
		{name: "Reason for Expense"},
		{name: "Date of Expense"},
		{name: "Amount"},
		{name: "Date Submitted"},
		{name: "Last Updated By"},
		{name: "Status"}
		]

	$scope.attmeptSubmit = function() {
		if ($scope.reimbursement){
	    $http.post('/ERS/html/rest/reimbursementCreate', $scope.reimbursement)
			    .then(function(response) {
				    console.log(response.data);
				  });	
		} else {
			console.log($scope.reimbursement);
		}
	 }
});