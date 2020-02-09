//服务层
app.service('studentService', function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get("/student/findAll");
    }
    //分页
    this.findPage = function (page, rows) {
        return $http.get('../student/findPage.do?page=' + page + '&rows=' + rows);
    }
    //查询一个学生的信息
    this.findOne = function (id) {
        return $http.get('/student/findOne/' + id);
    };
    //增加社团成员
    this.add = function (entity) {
        return $http.post('/student/add', entity);
    };
    //修改学生信息
    this.update = function (oldStuCode, po) {
        return $http.post('/student/update/' + oldStuCode, po);
    };
    //删除学生信息
    this.dele = function (id, assoId) {
        return $http.delete('/student/delete/' + id + "/" + assoId);
    };
    //修改是否为管理人员
    this.setManage = function (stuCode, assoId,sign) {
        return $http.post('/student/updateManage/' + stuCode + "/" + assoId+"/"+sign);
    };
    //查询此社团所有学生信息
    this.search = function (page, rows, assoId, searchEntity) {
        return $http.post('/student/search/' + page + "/" + rows + "/" + assoId, searchEntity);
    }
});
