//资产归还控制层
app.controller('assetReturnController', function ($scope, $location, $controller, assetService) {

    $controller('baseController', {$scope: $scope});//继承


    $scope.searchEntity = {};//定义搜索对象
    //搜索
    $scope.search = function (page, rows) {
        //如果sign为0 代表在查询资产列表页面
        assetService.searchReturn(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.data.rows;
                $scope.paginationConf.totalItems = response.data.total;//更新总记录数
            }
        );
    }
    $scope.findOne = function (assetLeaseInfoList) {
        $scope.assetInfo = assetLeaseInfoList;
    }
    //资产归还
    $scope.assetReturn = function (actId) {
        if (confirm("确认已经归还吗?") === true) {
            assetService.assetReturn(actId).success(
                function (response) {
                    if (response.success) {
                        alert(response.message);
                        $scope.reloadList();//刷新列表
                    } else {
                        alert(response.message);
                    }
                }
            );
        }
    };

});
