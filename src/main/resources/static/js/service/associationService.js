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
	//查询一个社团详情
	this.findOne=function(id){
		return $http.get('/association/findOne/'+id);
	};
    //通过社团类型查询所有社团
    this.findByTypeCode=function(code){
        return $http.get('/association/findByTypeCode/'+code);
    };
	//社团申请的service,entity为传输的添加数据
	this.add=function(entity){
		return  $http.post('/association/add',entity );
	};
	//修改社团信息
	this.update=function(entity){
		return  $http.post('/association/update',entity );
	};
	//删除社团信息
	this.dele=function(id,stuCode){
		return $http.delete('/association/delete/'+id+"/"+stuCode);
	};
	//查询所有社团service，搜索条件默认为null
	this.search=function(page,rows,searchEntity){
		return $http.post('/association/search/'+page+"/"+rows, searchEntity);
	};
});
