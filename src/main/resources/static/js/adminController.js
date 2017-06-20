/**
 * Created by Bender on 5/16/2017.
 */
app.controller("adminController", function($scope,$http,$cookies,$window){

    if($cookies.getObject('user')==null){
        window.location.href="#/login";
    }else if($cookies.getObject('user').role!="admin"){
        window.location.href="#/main";
    }

    refresh();

    //var user = $cookies.getObject("user");
    //console.log(user.country);
    //console.log(user.givenName);

    function refresh(){
        $http.get("/users/unapproved"
        ).success(function(data){
            $scope.users = data;
        });
    }



    $scope.cancel = function(user) {
        $http.post("/users/cancel/" + user.user_id
        ).success(function(data){
            refresh();
        });
    }

    $scope.approve = function(user) {
        $http.post("/users/approve/" + user.user_id
        ).success(function(data){
            refresh();
        });
    }

    $scope.logout = function(){
        alert("klik na logout");
        $cookies.remove('user');
        window.location.href="#/login";
    }

});