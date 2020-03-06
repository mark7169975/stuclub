//服务层
app.service('uiActivityService', function ($http) {
    this.search=function(page,rows){
        return $http.get('/ui/activity/search/'+page+'/'+rows);
    }
});
