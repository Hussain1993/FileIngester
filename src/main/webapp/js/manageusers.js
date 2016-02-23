var app = angular.module("manage-users", []);

app.controller("SynchStatusCtrl", function ($scope, $http){
        var host = getProtocolAndHost();
        $http.get(host.concat("/FileIngester/fi/tasking/synchStatus")).
            success(function(data){
                $scope.synchStatus = data;
            });
});

function manageUsers($scope, $http){
    $scope.data = null;
    var host = getProtocolAndHost();
    var fullURL = host.concat("/FileIngester/fi/users/all");
    $http({
        method : 'GET',
        url : fullURL
    }).then(function successCallback(response){
        $scope.data = response.data;
    }, function errorCallBack(response){
        console.log("There are no users in the system");
    });

    $scope.deleteUser = function(users){
        var host = getProtocolAndHost();
        var fullURL = host.concat("/FileIngester/fi/users/delete/"+users.emailAddress);
        $http({
            method : 'GET',
            url : fullURL
        }).then(function(){
            console.log("The user has been deleted from the database");
            var host = getProtocolAndHost();
            var fullURL = host.concat("/FileIngester/fi/users/all");
            $http({
                method : 'GET',
                url : fullURL
            }).then(function successCallback(response){
                $scope.data = response.data;
            }, function errorCallBack(response){
                console.log("There are no users in the system");
            });
        });
        console.log("The user will like to delete the user with email " + users.emailAddress);
    }
}

function getProtocolAndHost(){
    var protocol = location.protocol;
    var slashes = protocol.concat("//");
    var host = slashes.concat(window.location.host);
    return host;
}