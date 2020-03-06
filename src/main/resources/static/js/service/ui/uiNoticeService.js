//服务层
app.service('uiNoticeService', function ($http) {
    this.search=function(page,rows){
        return $http.get('/ui/notice/search/'+page+'/'+rows);
    }
});
