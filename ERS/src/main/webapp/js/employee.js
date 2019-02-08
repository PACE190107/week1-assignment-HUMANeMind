const d = document;
const c = console;

window.onload = () => {
//	getEmployee();
};

let logout = d.getElementById("btnLogout").addEventListener("click", getEmployee);
let submit = d.getElementById("btnSubmit").addEventListener("click", getEmployee);
let update = d.getElementById("btnUpdate").addEventListener("click", getEmployee);

let app = angular.module('ersApp', []);
app.controller('employeeCtrl', function($scope) {
	$scope.userName = "E1";
	$scope.password = "1234";
	$scope.email = "regular.employee@gmail.com";
	$scope.firstName = "Regular";
	$scope.lastName = "Employee";
	$scope.permission = "Regular";  
	
	$scope.fullName = function() {
		return $scope.firstName + " " + $scope.lastName;
	};
});

app.controller( 'reimbursementsCtrl', function($scope) {
	$scope.reimbursements = [
		    {id:'1000', expense: "Gas", expenseDate: "2-7-19", amount: "$100.00", submittedOn: new Date(), updatedBy: "Manager", status: "Approved"},
		    {id:'1001', expense: "OCA", expenseDate: "2-7-19", amount: "$250.00", submittedOn: new Date(), updatedBy: "Manager", status: "Approved"},
		    {id:'1002', expense: "Enthuware", expenseDate: "2-7-19", amount: "$300.00", submittedOn: new Date(), updatedBy: "Manager", status: "Approved"},
		    {id:'1003', expense: "Football Party", expenseDate: "2-7-19", amount: "$400.00", submittedOn: new Date(), updatedBy: "Person", status: "Denied"},
		    {id:'1004', expense: "Gas", expenseDate: "2-7-19", amount: "$500.00", submittedOn: new Date(), updatedBy: "Employee", status: "Pending"}
		  ];
	
	$scope.statuses = [
		{status: "Pending", id: "1"},
		{status: "Approved", id: "2"},
		{status: "Denied", id: "3"}
		];
	
	$scope.headers = [
		{name: "Reimbursement ID"},
		{name: "Reason for Expense"},
		{name: "Date of Expense"},
		{name: "Amount"},
		{name: "Date Submitted"},
		{name: "Last Updated By"},
		{name: "Status"}
		];
	
	$scope.statusIDs = $scope.statuses[0];
});

app.controller('reimburseCtrl', function($scope) {
	$scope.reimbursementID = "";
	$scope.status = "";
	$scope.amount = "";
	$scope.updatedBy = "";
	$scope.submittedOn = "";
	
	$scope.full = function() {
		return $scope.reimbursementID + " " + $scope.status + " " + $scope.amount + " " + $scope.updatedBy + " " + $scope.submittedOn;
	};
});

function getEmployee($scope){	
	let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if ((xhr.readyState == 4) && (xhr.status == 200)) {
            let data = xhr.responseText;
            alert(data);
        };
    };
    
    xhr.open('GET', '/ERS/html/rest/employee', true);
    xhr.send(null);
};