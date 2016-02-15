var app = angular.module("search", []);

app.controller("SynchStatusCtrl", function ($scope, $http){
        var host = getProtocolAndHost();
        $http.get(host.concat("/FileIngester/fi/tasking/synchStatus")).
            success(function(data){
                $scope.synchStatus = data;
            });
});

function SearchCtrl($scope, $http){
    $scope.search = {};
    $scope.data = null;
    $scope.performSearch = function() {
        var host = getProtocolAndHost();
        if($scope.search.searchQuery)
        {
            if($scope.search.searchQuery.indexOf("*") > -1)
            {
                $http({
                    method : 'POST',
                    url : host.concat("/FileIngester/fi/search/wildcard"),
                    data : $scope.search
                })
            }
            else
            {
                $http({
                    method : 'POST',
                    url : host.concat("/FileIngester/fi/search/basic"),
                    data : $scope.search
                }).then(function(response){
                    $scope.data = response.data;
                    console.log($scope.data);
                });
            }
        }
        else
        {
            console.log("There is nothing");
        }
    }
}

function getProtocolAndHost(){
    var protocol = location.protocol;
    var slashes = protocol.concat("//");
    var host = slashes.concat(window.location.host);
    return host;
}