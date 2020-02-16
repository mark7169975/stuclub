//服务层
app.service('loginService',function($http){


    //查询登录成功的个人信息
    this.loginName=function(){
        return $http.get('/loginName');
    };
    //查询登录的错误信息
    this.queryErrorMess=function(){
        return $http.get("/queryErrorMess");
    }
});
