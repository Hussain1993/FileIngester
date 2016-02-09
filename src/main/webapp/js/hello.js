function Hello($scope, $http) {
    $http.get('http://localhost:8080/FileIngester/fi/test').
        success(function(data) {
            $scope.greeting = data;
        });
}

function SynchStatus($scope, $http){
    $http.get('http://localhost:8080/FileIngester/fi/tasking/synchStatus').
        success(function(data){
            $scope.synchStatus = data;
        });
}