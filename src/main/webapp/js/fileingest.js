var app = angular.module("fileingester", []);

app.controller("SynchStatusCtrl", function ($scope, $http){
        var protocol = location.protocol;
        var slashes = protocol.concat("//");
        var host = getProtocolAndHost();
        $http.get(host.concat("/FileIngester/fi/tasking/synchStatus")).
            success(function(data){
                $scope.synchStatus = data;
            });
});

function getProtocolAndHost(){
    var protocol = location.protocol;
    var slashes = protocol.concat("//");
    var host = slashes.concat(window.location.host);
    return host;
}