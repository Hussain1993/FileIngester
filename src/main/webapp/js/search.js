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
    $scope.preview = null;
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
                }).then(function(response){
                    $scope.data = response.data;
                    if($scope.data == false)
                    {
                        $.notify({
                            // options
                            message: 'No Results'
                        },{
                            // settings
                            type: 'info'
                        });
                    }
                });
            }
            else
            {
                $http({
                    method : 'POST',
                    url : host.concat("/FileIngester/fi/search/basic"),
                    data : $scope.search
                }).then(function(response){
                    $scope.data = response.data;
                    if($scope.data == false)
                    {
                        $.notify({
                            // options
                            message: 'No Results'
                        },{
                            // settings
                            type: 'info'
                        });
                    }
                });
            }
        }
        else
        {
            $.notify({
                // options
                message: 'Please enter a search query'
            },{
                // settings
                type: 'danger'
            });
        }
    }
    $scope.previewDocument = function(rows) {
        var host = getProtocolAndHost();
        host = host.concat("/FileIngester/fi/search/preview/");
        var fullURL = host.concat(rows.fileHash);

        $http({
            method : 'GET',
            url : fullURL
        }).then(function (response){
            console.log(response.data);
        });
    }
}

function getProtocolAndHost(){
    var protocol = location.protocol;
    var slashes = protocol.concat("//");
    var host = slashes.concat(window.location.host);
    return host;
}