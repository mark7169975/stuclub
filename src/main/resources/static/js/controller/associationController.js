 //控制层 
app.controller('associationController' ,function($scope,$controller   ,associationService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		associationService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		associationService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		associationService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
    $scope.typeList = [
        {typeId: "1", typeName: "学术研究", typeCode: "1"},
        {typeId: "2", typeName: "体育竞技", typeCode: "2"},
        {typeId: "3", typeName: "兴趣爱好", typeCode: "3"}
    ];
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=associationService.update( $scope.entity ); //修改  
		}else{
			serviceObject=associationService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
                    alert(response.message);
                    $scope.entity={};
                    //window.location.href("http://localhost:8888/static/pages/club/club-register.html");
					//重新查询 
		        	//$scope.reload();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		associationService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		associationService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.data.rows;
				$scope.paginationConf.totalItems=response.data.total;//更新总记录数
			}			
		);
	}
    
});	
