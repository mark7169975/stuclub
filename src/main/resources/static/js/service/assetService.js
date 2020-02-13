//服务层
app.service('assetService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../asset/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../asset/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../asset/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../asset/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../asset/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../asset/delete.do?ids='+ids);
	}
	//查询资产，默认搜索条件为null
	this.search=function(page,rows,searchEntity){
		return $http.post('/asset/search/'+page+'/'+rows, searchEntity);
	};
});
