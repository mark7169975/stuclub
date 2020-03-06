//服务层
app.service('uiIndexService', function ($http) {
    //UI首页查询公告信息，只查询6条信息
    this.findAllNotice = function () {
        return $http.get('/ui/notice/findAll');
    };
    //UI首页查询活动信息，只查询6条信息
    this.findAllActivity = function () {
        return $http.get('/ui/activity/findAll');
    }
});
