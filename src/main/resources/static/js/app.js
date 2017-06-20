var app = angular.module('app', ['ngRoute','ngResource','ngCookies']);
app.config(function($routeProvider){
    $routeProvider
        .when('/login',{
            templateUrl:'/views/login.html',
            controller: 'loginRegisterController'
        })
        .when('/register',{
            templateUrl:'/views/register.html',
            controller: 'registerController'
        })
        .when('/admin',{
            templateUrl:'/views/admin.html',
            controller: 'adminController'
        })
        .when('/main',{
            templateUrl:'/views/main.html',
            controller: 'mainController'
        })
        .otherwise(
            { redirectTo: '/login'}
        );
});
