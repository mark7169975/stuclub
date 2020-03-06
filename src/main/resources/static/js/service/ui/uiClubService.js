//服务层
app.service('uiClubService', function ($http) {
    this.search=function(page,rows,id){
        return $http.get('/ui/association/search/'+page+'/'+rows+'/'+id);
    };
    this.queryClubMess=function(assId){
        return $http.get('/ui/association/queryClubMess/'+assId);
    };
    this.queryClubManage=function(assId){
        return $http.get('/ui/association/queryClubManage/'+assId);
    };
});
