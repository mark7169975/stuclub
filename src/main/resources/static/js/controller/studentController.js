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
    //监视typeCode的改变查询社团
    $scope.$watch('typeCode', function (newValue, oldValue) {
        //调用associationService的方法
        associationService.findByTypeCode(newValue == null ? 1000 : newValue).success(
            function (response) {
                $scope.AssList = response;
            }
        );
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

    //搜索
    $scope.search = function (page, rows) {
        studentService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.data.rows;
                $scope.paginationConf.totalItems = response.data.total;//更新总记录数
            }
        );
    }

});
