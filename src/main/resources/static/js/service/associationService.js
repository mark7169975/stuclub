//服务层
app.service('associationService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../association/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../association/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('/association/findOne/'+id);
	}
    //通过社团类型查询所有社团
    this.findByTypeCode=function(code){
        return $http.get('/association/findByTypeCode/'+code);
    }
	//社团申请的service,entity为传输的添加数据
	this.add=function(entity){
		return  $http.post('/association/add',entity );
	};
	//修改 
	this.update=function(entity){
		return  $http.post('../association/update.do',entity );
	}
	//删除
	this.dele=function(id){
		return $http.delete('/association/delete/'+id);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('/association/search/'+page+"/"+rows, searchEntity);
	}    	
});
