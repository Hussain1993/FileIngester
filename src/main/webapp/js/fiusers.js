var app = angular.module("welcome", []);


function UserController($scope, $http){
    var host = getProtocolAndHost();
    $scope.user = {};
    $scope.loginUser = function() {
        $http({
            method : 'POST',
            url : host.concat("/FileIngester/fi/users/login"),
            data : $scope.user
        }).then(function successCallback(response){
            if(response.status == 200)
            {
                window.location.href = "./pages/index.html"
            }
        }, function errorCallBack(response){
            $.notify({
            	// options
            	message: 'Email Address and Password were not recognised, please try again'
            },{
            	// settings
            	type: 'danger'
            });
        });
    }
}

function UserLoginController($scope, $http){
    var host = getProtocolAndHost();
    $scope.user = {};
    $scope.loginUser = function() {
        $http({
            method : 'POST',
            url : host.concat("/FileIngester/fi/users/login"),
            data : $scope.user
        }).then(function successCallback(response){
            if(response.status == 200)
            {
                window.location.href = "./index.html"
            }
            else
            {
                $.notify({
                    // options
                    message: 'Email Address and Password were not recognised, please try again'
                },{
                    // settings
                    type: 'danger'
                });
            }
        });
    }
}

function UserRegister($scope, $http){
    var host = getProtocolAndHost();
    $scope.user = {};
    $scope.registerUser = function() {
        $http({
            method : 'POST',
            url : host.concat("/FileIngester/fi/users/register"),
            data : $scope.user
        }).then(function successCallback(response) {
            $.notify({
                // options
                message: 'Email Address and Password were not recognised, please try again'
            },{
                // settings
                type: 'success'
            });
        });
    }
}

function getProtocolAndHost(){
    var protocol = location.protocol;
    var slashes = protocol.concat("//");
    var host = slashes.concat(window.location.host);
    return host;
}