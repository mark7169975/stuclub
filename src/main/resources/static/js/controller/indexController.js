app.controller('indexController', function ($scope, $controller, loginService) {
    //读取当前登录人
    $scope.showLoginName = function () {
        loginService.loginName().success(
            function (response) {
                $scope.loginName = response.loginStuName;
            }
        );
    }
    //登录错误查询错误信息
    $scope.errorMess = null;
    $scope.queryErrorMess = function () {
        loginService.queryErrorMess().success(
            function (response) {
                $scope.errorMess = response.ErrorMessage;
            }
        );
    }
});
