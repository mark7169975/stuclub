//控制层
app.controller('noticeController', function ($scope, $controller, noticeService, loginService, associationService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        noticeService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        noticeService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //通过公告id查询一个公告信息
    $scope.findOne = function (id) {
        noticeService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    };
    //修改公告信息
    $scope.update = function (stuCode, entity) {
        noticeService.update(stuCode, entity).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.reloadListByTime();//查询公告信息
                } else {
                    alert(response.message);
                }
            }
        );
    };
    //添加公告信息
    $scope.add = function () {
        noticeService.add($scope.loginMess.loginStuCode, $scope.entity).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.entity = {};
                    $scope.entity.assId = $scope.AssList[0].assId;
                } else {
                    alert(response.message);
                }
            }
        );
    };


    //删除公告信息
    $scope.dele = function (notId) {
        if (confirm("确认删除吗?") === true) {
            if (confirm("再次确认删除吗?") === true) {
                if (confirm("最终确定删除吗?") === true) {
                    noticeService.dele(notId).success(
                        function (response) {
                            if (response.success) {
                                alert(response.message);
                                $scope.reloadListByTime();//查询公告信息
                            } else {
                                alert(response.message);
                            }
                        }
                    );
                }
            }
        }
    }
    //查询登录人的信息
    $scope.queryLoginMess = function () {
        loginService.loginName().success(
            function (response) {
                $scope.loginMess = response;
                //把页面加载时查询的登录信息赋值给stuCode和role，便于查询公告信息
                $scope.stuCode = $scope.loginMess.loginStuCode;
                $scope.role = $scope.loginMess.loginRole;
                //如果在分页的页面则发送查询公告信息的连接
                if ($scope.sign === 1) {
                    $scope.reloadListByTime();//查询公告信息
                }
                //如果不在分页页面，则不触发此链接
                if ($scope.sign === 0) {
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


    $scope.searchEntity = {};//定义搜索对象
    //定义查询公告的管理员学号
    $scope.stuCode = "";
    //定义查询公告的管理员角色
    $scope.role = "";
    //定义标记，为了判断是否在分页的页面
    $scope.sign = 0;
    //查询管理员所管理的社团公告
    $scope.search = function (page, rows) {
        //如果在分页页面，则复制标记为1
        $scope.sign = 1;
        //判断学号和角色是否为空，不为空则查询公告信息
        if ($scope.stuCode !== "" && $scope.role !== "") {
            noticeService.search(page, rows, $scope.stuCode, $scope.role, $scope.searchEntity).success(
                function (response) {
                    $scope.list = response.data.rows;
                    $scope.paginationConfByTime.totalItems = response.data.total;//更新总记录数
                }
            );
        }

    };

});	
