//控制层
app.controller('studentController', function ($scope, $controller, studentService, roleService, typeService, associationService, loginService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        studentService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        studentService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }
    //true为只读
    $scope.showOrEdit = true;
    //查询一个学生的信息
    $scope.findOne = function (id) {
        $scope.showOrEdit = true;
        studentService.findOne(id).success(
            function (response) {
                $scope.po = response;
            }
        );
    };
    //修改信息前查询数据
    $scope.oldStuCode = "";
    $scope.changeEdit = function (id) {
        $scope.showOrEdit = false;
        studentService.findOne(id).success(
            function (response) {
                $scope.po = response;
                $scope.oldStuCode = $scope.po.stuCode;
            }
        );
    };
    //修改学生信息
    $scope.update = function (oldStuCode, po) {
        studentService.update(oldStuCode, po).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    associationService.findByTypeCode($scope.typeCode).success(function (r) {
                        $scope.AssList = r;
                    });
                    $scope.reloadList();//重新加载页面
                } else {
                    alert(response.message);
                }
            }
        );

    };
    //查询所有角色
    $scope.findAllRole = function () {
        roleService.findAll().success(
            function (response) {
                $scope.roleList = response;
            }
        );
    };
    //查询所有社团类型
    $scope.findAllType = function () {
        typeService.findAll().success(
            function (response) {
                $scope.typeList = response;
            }
        );
    };
    $scope.loginMess = {};
    //查询登录人的信息
    $scope.queryLoginMess = function () {
        loginService.loginName().success(
            function (response) {
                $scope.loginMess = response;
                $scope.typeCode=1000;
            }
        );
    };
    $scope.entity = {"stuRole": {}};
    //监视typeCode的改变查询社团类型下的所有社团
    $scope.$watch('typeCode', function (newValue, oldValue) {
        if ($scope.loginMess.loginRole === 'ROLE_ADMIN') {
            associationService.findByStuCodeAndRole($scope.loginMess.loginStuCode, $scope.loginMess.loginRole).success(
                function (response) {
                    $scope.AssList = response;
                    //把查询到的社团第一个值赋值给assoId
                    $scope.entity.stuRole.assoId = $scope.AssList[0].assId;
                    //把社团id赋值给assoId，然后自动加载查询
                    $scope.assoId = $scope.entity.stuRole.assoId;
                    //如果在查询学生页面，则aaa得值就会被赋为1，否则为0
                    if ($scope.aaa === 1) {
                        $scope.reloadList();//加载查询数据
                    }
                }
            )
        }
        //判断登录人是否为超级管理员
        if ($scope.loginMess.loginRole === 'ROLE_SUPERADMIN') {
            //调用associationService的方法
            associationService.findByTypeCode(newValue == null ? 1000 : newValue).success(
                function (response) {
                    $scope.AssList = response;
                    //把查询到的社团第一个值赋值给assoId
                    $scope.entity.stuRole.assoId = $scope.AssList[0].assId;
                    //把社团id赋值给assoId，然后自动加载查询
                    $scope.assoId = $scope.entity.stuRole.assoId;
                    //如果在查询学生页面，则aaa得值就会被赋为1，否则为0
                    if ($scope.aaa === 1) {
                        $scope.reloadList();//加载查询数据
                    }
                }
            );
        }
    });
    //监控assoId的变化情况
    $scope.$watch('entity.stuRole.assoId', function (newValue, oldValue) {
        $scope.assoId = newValue;
        //如果在查询学生页面，assoId的值发生变化则刷新列表
        if ($scope.aaa === 1) {
            $scope.reloadList();//刷新列表
        }
    });
    //添加社团成员
    $scope.save = function () {
        studentService.add($scope.entity).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.entity = {"stuRole": {"roleCode": 2002, "assoId": 45}}
                } else {
                    alert(response.message);
                }
            }
        );
    };


    //删除学生信息
    $scope.dele = function (id, assoId) {
        if (confirm("确认删除吗?") === true) {
            if (confirm("再次确认删除吗?") === true) {
                if (confirm("最终确定删除吗?") === true) {
                    studentService.dele(id, assoId).success(
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

    //设置和取消管理人员
    $scope.setManage = function (stuCode, assId, sign) {
        if (confirm("确定修改吗?") === true) {
            studentService.setManage(stuCode, assId, sign).success(
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


    $scope.searchEntity = {};//定义搜索对象
    $scope.assoId = 0;//页面初始化时，未获得需要查询的社团id
    $scope.aaa = 0;//表示是否在查询学生页面
    //根据社团类型和社团查询此社团学生，如果没有输入条件则查询此社团所有学生
    $scope.search = function (page, rows) {
        //分页启动则表示在查询学生页面，把aaa改为1
        $scope.aaa = 1;
        //如果未获取需要查询的社团id时，则不查询，等到获取到社团id自动查询
        if ($scope.assoId !== 0 && $scope.assoId !== undefined) {

            //page为页码，rows为查询几条数据，assoId为查询的社团id，searchEntity为查询的数据
            studentService.search(page, rows, $scope.assoId, $scope.searchEntity).success(
                function (response) {
                    $scope.list = response.data.rows;
                    $scope.paginationConf.totalItems = response.data.total;//更新总记录数
                }
            );
        }

    }

});
