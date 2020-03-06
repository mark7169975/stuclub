//服务层
app.service('activityService', function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../activity/findAll.do');
    }
    //分页
    this.findPage = function (page, rows) {
        return $http.get('../activity/findPage.do?page=' + page + '&rows=' + rows);
    }
    //查询实体
    this.findOne = function (id) {
        return $http.get('/activity/findOne/' + id);
    };
    //驳回活动重新申请
    this.reapply = function (actId) {
        return $http.post('/activity/reapply/' + actId);
    };

    //增加
    this.add = function (loginStuCode, remain, entity) {
        return $http.post('/activity/add/' + loginStuCode + '/' + remain, entity);
    };
    //修改
    this.update = function (entity) {
        return $http.post('../activity/update.do', entity);
    }
    //删除
    this.dele = function (id) {
        return $http.delete('/activity/delete/' + id);
    };
    //搜索
    this.search = function (page, rows, stuCode, role, searchEntity) {
        return $http.post('/activity/search/' + page + '/' + rows + '/' + stuCode + '/' + role, searchEntity);
    }
});
