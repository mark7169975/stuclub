//服务层
app.service('studentService',function($http){

	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get("/student/findAll");
	}
	//分页
	this.findPage=function(page,rows){
		return $http.get('../student/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('/student/findOne/'+id);
	}
	//增加社团成员
	this.add=function(entity){
		return  $http.post('/student/add',entity );
	};
	//修改
	this.update=function(entity){
		return  $http.post('../student/update.do',entity );
	}
	//删除
	this.dele=function(id){
		return $http.delete('/student/delete/'+id);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('/student/search/'+page+"/"+rows, searchEntity);
	}
});
