app.controller('uiActivityController', function ($scope, $controller, uiActivityService) {
    $controller('baseController', {$scope: $scope});//继承
    $scope.search = function (page, rows) {
        uiActivityService.search(page, rows).success(
            function (response) {
                $scope.activityList = response.data.rows;
                $scope.paginationConfByUi.totalItems = response.data.total;//更新总记录数
            }
        );
    };
});
