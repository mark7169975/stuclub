app.controller('uiClubController', function ($scope, $location, $rootScope, $controller, uiClubService) {
    $controller('baseController', {$scope: $scope});//继承

    //当且仅当path或url变化成功后触发
    $rootScope.$on('$locationChangeSuccess', function () {
        if ($location.search()['id'] !== undefined) {
            $scope.reloadListByUi();
        }
    });
    $scope.queryClubMess = function () {
        uiClubService.queryClubMess($location.search()['assId']).success(
            function (response) {
                $scope.clubMess = response;
            }
        )
    };
    $scope.queryClubManage = function () {
        uiClubService.queryClubManage($location.search()['assId']).success(
            function (response) {
                $scope.clubManage = response;
            }
        )
    };
    $scope.search = function (page, rows) {
        uiClubService.search(page, rows, $location.search()['id']).success(
            function (response) {
                $scope.clubList = response.data.rows;
                $scope.paginationConfByUi.totalItems = response.data.total;//更新总记录数
            }
        );
    };
});
