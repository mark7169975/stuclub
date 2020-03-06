//控制层
app.controller('assetController', function ($scope, $location, $controller, assetService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        assetService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        assetService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询一个资源实体
    $scope.findOne = function (id) {
        assetService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    };

    //保存资产
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.assetId != null) {//如果有ID
            serviceObject = assetService.update($scope.entity); //修改资产
        } else {
            serviceObject = assetService.add($scope.entity);//增加资产
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.entity = {};
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //删除资产信息
    $scope.dele = function (id) {
        if (confirm("确认删除吗?") === true) {
            if (confirm("再次确认删除吗?") === true) {
                if (confirm("最终确定删除吗?") === true) {
                    assetService.dele(id).success(
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
            }
        }
    };

    $scope.searchEntity = {};//定义搜索对象
    //搜索
    $scope.search = function (page, rows) {
        //如果sign为0 代表在查询资产列表页面
        assetService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.data.rows;
                $scope.paginationConf.totalItems = response.data.total;//更新总记录数
            }
        );
    }

});	
