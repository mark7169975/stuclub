app.controller('uiIndexController', function ($scope, $controller, uiIndexService) {
    $controller('baseController', {$scope: $scope});//继承
    //UI首页查询公告信息
    $scope.findAllNotice = function () {
        uiIndexService.findAllNotice().success(
            function (response) {
                $scope.noticeList = response;
            }
        );
    }
    //读取列表数据绑定到表单中
    $scope.findAllActivity = function () {
        uiIndexService.findAllActivity().success(
            function (response) {
                $scope.ActivityList = response;
            }
        );
    }
});
