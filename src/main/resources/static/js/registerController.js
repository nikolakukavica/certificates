app.controller("registerController", function($scope,$http,$cookies,$window){

    $scope.message="Find certificate";

    $scope.register = function(){
        $http.post("/users",$scope.newUser
        ).success(function(data){
            alert(data);
            window.location.href="#/login";
        });
    }
});