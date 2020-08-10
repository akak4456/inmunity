function moveUsingForm(id,url,page,type,keyword){
	//type과 keyword는 선택임
	if(id == null || url == null || page == null){
		//모든 파라미터는 설정되어 있어야 함
		console.log("ALL PARAMETER MUST BE SETTED");
		return;
	}
	let formObj = $("#"+id);
	formObj.attr("action",url);
	if(page != -1)
		formObj.find("input[name='page']").val(page);
	if(type != null){
		formObj.find("input[name='page']").val(1);
		formObj.find("input[name='type']").val(type);
		formObj.find("input[name='keyword']").val(keyword);
	}
	formObj.submit();
}