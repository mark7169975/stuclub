 //品牌控制层 
app.controller('baseController' ,function($scope){	
	
    //重新加载列表 数据
    $scope.reloadList=function(){
    	//切换页码
    	$scope.search( $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }
    //重新加载列表 数据
    $scope.reloadListByTime=function(){
        //切换页码
        $scope.search( $scope.paginationConfByTime.currentPage, $scope.paginationConfByTime.itemsPerPage);
    }
    $scope.reload=function(){
        //切换页码
        $scope.search(window.location.reload(true));
    }

    //普通页面分页控件配置
	$scope.paginationConf = {
         currentPage: 1,
         totalItems: 10,
         itemsPerPage: 10,
         perPageOptions: [10, 20, 30, 40, 50],
         onChange: function(){
        	 $scope.reloadList();//重新加载
     	 }
	};
    //时间线页面分页控件配置
    $scope.paginationConfByTime = {
        currentPage: 1,
        totalItems: 5,
        itemsPerPage: 5,
        perPageOptions: [5, 10, 20, 40, 80],
        onChange: function(){
            $scope.reloadListByTime();//重新加载
        }
    };
    $scope.selectIds=[];//选中的ID集合

	//更新复选
	$scope.updateSelection = function($event, id) {		
		if($event.target.checked){//如果是被选中,则增加到数组
			$scope.selectIds.push( id);			
		}else{
			var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx, 1);//删除 
		}
	}
	
});	