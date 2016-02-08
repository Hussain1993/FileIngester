function Hello($scope, $http) {
    $http.get('http://localhost:8080/FileIngester/fi/test').
        success(function(data) {
            $scope.greeting = data;
        });
}