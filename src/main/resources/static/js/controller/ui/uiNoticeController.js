app.controller('uiNoticeController', function ($scope, $controller, uiNoticeService) {
    $controller('baseController', {$scope: $scope});//继承
    $scope.search = function (page, rows) {
        uiNoticeService.search(page, rows).success(
            function (response) {
                $scope.noticeList = response.data.rows;
                $scope.paginationConfByUi.totalItems = response.data.total;//更新总记录数
            }
        );
    };
});
