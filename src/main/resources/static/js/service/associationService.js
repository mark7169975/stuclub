//服务层
app.service('associationService', function ($http) {

    //审核中心页面，查询所有社团信息
    this.findAll = function () {
        return $http.get('/association/findAll');
    };
    //分页
    this.findPage = function (page, rows) {
        return $http.get('../association/findPage.do?page=' + page + '&rows=' + rows);
    }
    //通过登录网址管理员信息，查询此管理员加入的所有是管理角色的社团
    this.findByStuCodeAndRole = function (stuCode, role) {
        return $http.get('/association/findByStuCodeAndRole/' + stuCode + '/' + role);
    };
    //查询一个社团详情
    this.findOne = function (id) {
        return $http.get('/association/findOne/' + id);
    };
    //通过社团类型查询所有社团
    this.findByTypeCode = function (code) {
        return $http.get('/association/findByTypeCode/' + code);
    };
    //社团申请的service,entity为传输的添加数据
    this.add = function (entity) {
        return $http.post('/association/add', entity);
    };
    //修改社团信息
    this.update = function (entity) {
        return $http.post('/association/update', entity);
    };
    //删除社团信息
    this.dele = function (id, stuCode) {
        return $http.delete('/association/delete/' + id + "/" + stuCode);
    };
    //换届前查询此社团所有学生
    this.findOneAss = function (assId) {
        return $http.get('/association/findOneAss/' + assId)
    };
    //社长换届
    this.changeManage = function (changeManageStuCode, changeManageAssId) {
        return $http.post('/association/changeManage/' + changeManageStuCode + '/' + changeManageAssId)
    };
    //查询所有社团service，搜索条件默认为null
    this.search = function (page, rows, searchEntity) {
        return $http.post('/association/search/' + page + "/" + rows, searchEntity);
    };
});
