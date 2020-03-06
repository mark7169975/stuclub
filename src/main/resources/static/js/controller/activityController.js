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
                $scope.act = response;
            }
        );
    };

    //查询登录人的信息
    $scope.queryLoginMess = function () {
        loginService.loginName().success(
            function (response) {
                $scope.loginMess = response;
                //如果标记为0，则表示不在分页页面
                $scope.stuCode = $scope.loginMess.loginStuCode;
                $scope.role = $scope.loginMess.loginRole;
                if ($scope.signPage === 1) {
                    $scope.reloadListByTime();//查询活动信息
                }
                if ($scope.signPage === 0) {
                    $scope.findByStuCodeAndRole($scope.loginMess.loginStuCode, $scope.loginMess.loginRole)
                }
            }
        );
    };
    //通过登录人学号和角色信息查询社团
    $scope.entity = {};
    $scope.AssList = {};
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
    //重新申请活动
    $scope.reapply = function (actId) {
        if (confirm("确认重新申请吗?") === true) {
            activityService.reapply(actId).success(
                function (response) {
                    if (response.success) {
                        alert(response.message);
                        $scope.reloadListByTime();//查询活动信息
                    } else {
                        alert(response.message);
                    }
                }
            )
        }
    };

    //删除活动
    $scope.dele = function (id) {
        if (confirm("确认删除吗?") === true) {
            if (confirm("再次确认删除吗?") === true) {
                if (confirm("最终确定删除吗?") === true) {
                    activityService.dele(id).success(
                        function (response) {
                            if (response.success) {
                                alert(response.message);
                                $scope.reloadListByTime();//刷新列表
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
    //定义查询公告的管理员学号
    $scope.stuCode = "";
    //定义查询公告的管理员角色
    $scope.role = "";
    //定义标记，为了判断是否在分页的页面
    $scope.signPage = 0;
    //社团活动分页查询数据
    $scope.search = function (page, rows) {
        $scope.signPage = 1;
        if ($scope.stuCode !== "" && $scope.role !== "") {
            activityService.search(page, rows, $scope.stuCode, $scope.role, $scope.searchEntity).success(
                function (response) {
                    $scope.list = response.data.rows;
                    $scope.paginationConfByTime.totalItems = response.data.total;//更新总记录数
                }
            );
        }
    };

});	
