function moveUsingForm(id,url,page){
	if(id == null || url == null || page == null){
		//모든 파라미터는 설정되어 있어야 함
		console.log("ALL PARAMETER MUST BE SETTED");
		return;
	}
	let formObj = $("#"+id);
	formObj.attr("action",url);
	if(page != -1)
		formObj.find("input[name='page']").val(page);
	formObj.submit();
}