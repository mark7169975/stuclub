//控制层
app.controller('associationController', function ($scope, $controller, associationService, typeService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        associationService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        associationService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }


    //查询所有社团类型
    $scope.findAllType = function () {
        typeService.findAll().success(
            function (response) {
                $scope.typeList = response;
            }
        );
    }
    //查询实体
    $scope.findOne = function (id) {
        $scope.showOrEdit = true;
        associationService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }
    //true为只读
    $scope.showOrEdit = true;
    //社团申请提交数据
    $scope.save = function () {
        associationService.add($scope.entity).success(
            function (response) {
                if (response.success) {
                    //添加成功弹出成功信息
                    alert(response.message);
                    //给社团类型赋值默认1000，避免下拉框显示不成功
                    $scope.entity = {"typeCode": 1000};
                } else {
                    //添加失败弹出失败信息
                    alert(response.message);
                }
            }
        );
    };
    //修改状态
    $scope.changeEdit = function (id) {
        $scope.showOrEdit = false;
        associationService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
        typeService.findAll().success(
            function (response) {
                $scope.typeList = response;
            }
        );

    }
    //修改社团信息
    $scope.update = function (entity) {
        associationService.update(entity).success(
            function (response) {
                if (response.success) {
                    $scope.entity = response;
                }
            }
        );
    }

    //删除
    $scope.dele = function (id) {
        //获取选中的复选框
        associationService.dele(id).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        associationService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.data.rows;
                $scope.paginationConf.totalItems = response.data.total;//更新总记录数
            }
        );
    }

});	
