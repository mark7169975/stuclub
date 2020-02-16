//控制层
app.controller('activityController', function ($scope, $controller, activityService, loginService, associationService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        activityService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }


    //分页
    $scope.findPage = function (page, rows) {
        activityService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        activityService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }
    //查询登录人的信息
    $scope.queryLoginMess = function () {
        loginService.loginName().success(
            function (response) {
                $scope.loginMess = response;
                $scope.findByStuCodeAndRole($scope.loginMess.loginStuCode, $scope.loginMess.loginRole)
            }
        );
    };
    //通过登录人学号和角色信息查询社团
    $scope.entity = {};
    $scope.findByStuCodeAndRole = function (stuCode, role) {
        associationService.findByStuCodeAndRole(stuCode, role).success(
            function (response) {
                $scope.AssList = response;
                $scope.entity.assId = $scope.AssList[0].assId;
            }
        )
    };
    //社团活动添加
    $scope.add = function () {
       // $scope.entity.startTime = new Date($scope.entity.startTime);
        activityService.add($scope.loginMess.loginStuCode, $scope.entity).success(
            function (response) {
                if (response.success) {
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        activityService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        activityService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

});	
