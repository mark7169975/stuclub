//控制层
app.controller('activityController', function ($scope, $controller, activityService, assetService, loginService, associationService) {

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
    //查询所有有资源的资产信息
    $scope.assetIds = [];
    $scope.AssetList = [];
    $scope.findAllAsset = function () {
        assetService.findAll().success(
            function (response) {
                $scope.AssetList = response;
            }
        )
    };

    //监控assetIds的变化情况
    $scope.remain = [];
    $scope.$watch('assetIds', function (newValue, oldValue) {
        //下拉框选择和删除，表单的动态显示
        $scope.condition = function (x) {
            for (var i = 0; i < $scope.assetIds.length; i++) {
                return x.assetId === $scope.assetIds[i]
                    || x.assetId === $scope.assetIds[i + 1]
                    || x.assetId === $scope.assetIds[i + 2]
                    || x.assetId === $scope.assetIds[i + 3]
                    || x.assetId === $scope.assetIds[i + 4]
                    || x.assetId === $scope.assetIds[i + 5]
                    || x.assetId === $scope.assetIds[i + 6]
                    || x.assetId === $scope.assetIds[i + 7]
                    || x.assetId === $scope.assetIds[i + 8]
                    || x.assetId === $scope.assetIds[i + 9]
                    || x.assetId === $scope.assetIds[i + 10];
            }
        };
        //获取下拉框删除的资产
        for (var i = 0; i < newValue.length; i++) {
            for (var j = 0; j < oldValue.length; j++) {
                if (oldValue[j] === newValue[i]) {
                    oldValue.splice(j, 1);
                    j = j - 1;
                }
            }
        }
        //把删除的资产需要申请的数量清除
        for (var i = 0; i < $scope.AssetList.length; i++) {
            if ($scope.AssetList[i].assetId === oldValue[0]) {
                $scope.remain[i] = "";
            }
        }
        //判断表单头是否显示
        if ($scope.assetIds.length === 0) {
            $scope.sign = true;
        }
        if ($scope.assetIds.length !== 0) {
            $scope.sign = false;
        }
    });

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
        activityService.add($scope.loginMess.loginStuCode, $scope.remain, $scope.entity).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    //框架页面刷新
                    location.reload();
                } else {
                    alert(response.message);
                }
            }
        );
    };


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

    //社团活动分页查询数据
    $scope.search = function (page, rows) {
        activityService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.data.rows;
                $scope.paginationConf.totalItems = response.data.total;//更新总记录数
            }
        );
    };

});	
