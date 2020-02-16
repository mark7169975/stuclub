//服务层
app.service('noticeService', function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../notice/findAll.do');
    }
    //分页
    this.findPage = function (page, rows) {
        return $http.get('../notice/findPage.do?page=' + page + '&rows=' + rows);
    }
    //通过公告id查询一个公告信息
    this.findOne = function (id) {
        return $http.get('/notice/findOne/' + id);
    };
    //增加公告信息
    this.add = function (loginStuCode, entity) {
        return $http.post('/notice/add/' + loginStuCode, entity);
    };
    //修改公告信息
    this.update = function (stuCode,entity) {
        return $http.post('/notice/update/'+stuCode, entity);
    };
    //删除公告信息
    this.dele = function (notId) {
        return $http.delete('/notice/delete/' + notId);
    };
    //分页查询公告信息
    this.search = function (page, rows, stuCode, role, searchEntity) {
        return $http.post('/notice/search/' + page + '/' + rows + '/' + stuCode + '/' + role, searchEntity);
    }
});
