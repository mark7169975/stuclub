//控制层
app.controller('associationController', function ($scope, $controller, associationService, typeService, loginService) {

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
    //查询登录人的信息
    $scope.queryLoginMess = function () {
        loginService.loginName().success(
            function (response) {
                $scope.loginMess = response;
            }
        );
    };
    //查询一个社团详情
    $scope.findOne = function (id) {
        //查看社团详情时，showOrEdit为true是只读
        $scope.showOrEdit = true;
        associationService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    };
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
    //修改社团前查询一个社团信息
    $scope.changeEdit = function (id) {
        //把修改权限修改为false表示可以修改
        $scope.showOrEdit = false;
        associationService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
        //查询社团的所有类型
        typeService.findAll().success(
            function (response) {
                $scope.typeList = response;
            }
        );

    };
    //修改社团信息
    $scope.update = function (entity) {
        associationService.update(entity).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.reloadList();//重新加载页面
                } else {
                    alert(response.message);
                }
            }
        );
    };

    //删除社团信息
    $scope.dele = function (id, stuCode) {
        if (confirm("确认删除吗?") === true) {
            if (confirm("再次确认删除吗?") === true) {
                if (confirm("最终确定删除吗?") === true) {
                    associationService.dele(id, stuCode).success(
                        function (response) {
                            if (response.success) {
                                alert(response.message);
                                $scope.reloadList();//刷新列表
                            } else {
                                alert(response.message)
                            }
                        }
                    );
                }
            }
        }
    };
    $scope.changeManageAssId = 0;
    $scope.studentByAssIdList = {data: []};
    $scope.findOneAss = function (assId) {
        $scope.changeManageAssId=assId;
        associationService.findOneAss(assId).success(
            function (response) {
                $scope.studentByAssIdList = {data: response};
            }
        )
    };
    $scope.changeStudentByAssIdList=function(){
        $scope.studentByAssIdList = {data: []};
        $scope.yyy={};
        $scope.changeManageStuCode={};
    };

    $scope.changeManage = function (changeManageStuCode) {
        associationService.changeManage(changeManageStuCode,$scope.changeManageAssId).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.reloadList();//刷新列表
                } else {
                    alert(response.message);
                }

            });

    }

    $scope.searchEntity = {};//定义搜索对象

    //查询所有社团，默认搜索数据为null
    $scope.search = function (page, rows) {
        $scope.queryLoginMess();
        associationService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.data.rows;
                $scope.paginationConf.totalItems = response.data.total;//更新总记录数
            }
        );
    };

});	
