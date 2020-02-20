//审批中心服务层
app.service('approvalService', function ($http) {
    //活动审批审核
    this.changeApproval = function (actId, mark) {
        return $http.post('/activity/changeApproval/' + actId + '/' + mark);
    };

    //活动审批分页查询活动
    this.search = function (page, rows, searchEntity) {
        return $http.post('/activity/searchApproval/' + page + '/' + rows, searchEntity);
    };
});
