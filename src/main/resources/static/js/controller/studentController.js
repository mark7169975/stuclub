//控制层
app.controller('studentController', function ($scope, $controller, studentService, roleService, typeService, associationService) {

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
    //查询实体
    $scope.findOne = function (id) {
        $scope.showOrEdit = true;
        studentService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }
    //修改状态
    $scope.changeEdit = function (id) {
        $scope.showOrEdit = false;
        studentService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }
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
    }
    $scope.entity = {"stuRole": {}};
    //监视typeCode的改变查询社团类型下的所有社团
    $scope.$watch('typeCode', function (newValue, oldValue) {
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
                    $scope.entity = {}
                } else {
                    alert(response.message);
                }
            }
        );
    };


    //批量删除
    $scope.dele = function (id) {
        //获取选中的复选框
        studentService.dele(id).success(
            function (response) {
                if (response.success) {
                    alert(response.message)
                    $scope.reloadList();//刷新列表
                }
            }
        );
    }

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
