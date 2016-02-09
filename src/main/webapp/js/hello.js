function Hello($scope, $http) {
    $http.get('http://localhost:8080/FileIngester/fi/test').
        success(function(data) {
            $scope.greeting = data;
        });
}

function SynchStatus($scope, $http){
    var protocol = location.protocol;
    var slashes = protocol.concat("//");
    var host = slashes.concat(window.location.host);
    $http.get(host.concat("/FileIngester/fi/tasking/synchStatus")).
        success(function(data){
            $scope.synchStatus = data;
        });
}