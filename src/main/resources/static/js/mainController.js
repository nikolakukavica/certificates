app.controller("mainController", function($scope,$http,$cookies){

    if($cookies.getObject('user')==null){
        window.location.href="#/login";
    }else if($cookies.getObject('user').role!="user"){
        window.location.href="#/admin";
    }

    var user = $cookies.getObject('user');
    refresh();


    function refresh(){
        $http.get("/certificates/allAuthorities").success(function(data){
            if(data != null)
                $scope.authorities = data;

        });
        $http.get("/csrs/" + user.user_id).success(function(data){
                $scope.requests = data;
        });
        $http.get("/certificates/bySubject/" + user.user_id).success(function(data){
            $scope.myCertificates = data;
        });
    }


    $scope.generateSS = function() {
        console.log('cuvam!');
        $http.post("/certificates/newSelfSigned/" + user.user_id);
    }

    $scope.requestCertificate = function (ca, serial){
        $http.post("/csrs/create/" + user.user_id + "/" + serial + "/" + ca);
    }

    $scope.accept = function (csr_id){
        $http.post("/csrs/accept/" + csr_id);
    }

    $scope.decline = function (csr_id){
        $http.post("/csrs/decline/" + csr_id);
    }



    $scope.getCertificate = function (){
        $http.get("/certificates/getCertificate/" + $scope.serial_number).success(function(response){
            console.log("Dobio!");
            //alert(response);
        });
    }

    $scope.getStatus = function (){
        //$http.get("/certificates/getStatus/" + $scope.serial_number).success(function(data){
        //    $scope.resp = data;
        //    console.log($scope.resp);
        //});





        var req = {
            method: 'GET',
            url: "/certificates/getStatus/" + $scope.serial_number,
            headers: {
                'Content-Type': 'application/json'
            }
        }
        console.log($scope.serial_number);
        //$http(req).then(function(){...}, function(){...});
        //$http(req).then(function(result){
        //    console.log(result);
        //});

    }

    $scope.withdraw = function (){
        $http.get("/certificates/withdraw/" + $scope.serial_number).success(function(response){
            if( response == true)
                alert("success!");
            else
                alert("error!");
        });
    }

    $scope.logout = function(){
        alert("klik na logout");
        $cookies.remove('user');
        window.location.href="#/login";
    }





});