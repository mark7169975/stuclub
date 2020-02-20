//审批中心控制层
app.controller('activityController', function ($scope, $controller, approvalService, associationService) {

    $controller('baseController', {$scope: $scope});//继承

    //查询所有社团信息
    $scope.assoList = {data: []};
    $scope.findAllAsso = function () {
        associationService.findAll().success(
            function (response) {
                $scope.assoList = {data: response};
            }
        )
    };

    //活动审核审批
    $scope.changeApproval = function (actId, mark) {
        approvalService.changeApproval(actId, mark).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.reloadList();//刷新列表
                } else {
                    alert(response.message);
                }
            }
        )
    };

    $scope.searchEntity = {};//定义搜索对象

    //审核中心分页查询数据
    $scope.search = function (page, rows) {
        approvalService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.data.rows;
                $scope.paginationConf.totalItems = response.data.total;//更新总记录数
            }
        );
    };

});
