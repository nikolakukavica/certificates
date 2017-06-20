/**
 * Created by Bender on 5/15/2017.
 */
app.controller("loginRegisterController", function($scope,$http,$cookies,$window){

    /*if($cookies.get('user')!=null){
        alert("usao");
        if($cookies.get('user').role=="admin")
            window.location.href="#/admin";
        else if($cookies.get('user').role=="user"){
            window.location.href="#/main";
                    alert("usao u usera");
            }
    }*/

    $scope.message="Find certificate";



    /*$scope.register = function(){
        $http.post("/users",$scope.newUser
        ).success(function(data){
            alert(data);
        });
    }*/


    $scope.login = function(){
        console.log("eo maloo");
        console.log($scope.loggingUser.email);
        console.log($scope.loggingUser.password);
        $http.get("/users/" + $scope.loggingUser.email +"/"+ $scope.loggingUser.password
        ).success(function(data){
            if(data != null){
                $cookies.putObject("user", data);
                console.log("ovo su podaci dobijeni");
                console.log(data);
                if(data.role=="admin")
                    window.location.href="#/admin";
                else
                    window.location.href="#/main";
            }
        });
    }

    $scope.redirectToRegister=function(){
        window.location.href="#/register";
    }

});