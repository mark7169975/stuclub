//服务层
app.service('assetService',function($http){
	    	
	//查询所有有资源的资产信息
	this.findAll=function(){
		return $http.get('/asset/findAll');
	};
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../asset/findPage.do?page='+page+'&rows='+rows);
	}
	//查询一个资产实体
	this.findOne=function(id){
		return $http.get('/asset/findOne/'+id);
	};
	//增加资产
	this.add=function(entity){
		return  $http.post('/asset/add',entity );
	};
	//修改资产信息
	this.update=function(entity){
		return  $http.post('/asset/update',entity );
	};
	//删除资产
	this.dele=function(id){
		return $http.delete('/asset/delete/'+id);
	};
	//查询资产，默认搜索条件为null
	this.search=function(page,rows,searchEntity){
		return $http.post('/asset/search/'+page+'/'+rows, searchEntity);
	};
    //查询资产归还，默认搜索条件为null
    this.searchReturn=function(page,rows,searchEntity){
        return $http.post('/asset/searchReturn/'+page+'/'+rows, searchEntity);
    };
    //资产归还
	this.assetReturn=function (actId) {
		return $http.delete('/asset/assetReturn/'+actId);
    }
});
