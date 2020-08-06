let addBoard = function(sendData,successcallback,failcallback){
	$.ajax({
		url:"/freeboard/boards",
		type:"post",
		contentType:"application/json",
		data:JSON.stringify(sendData),
		success:successcallback||function(data){
			alert(data);
		},
		error:failcallback||function(data){
			console.log(data);
		}
	});
};
let modifyBoard = function(sendData,successcallback,failcallback){
	$.ajax({
		url:"/freeboard/boards/"+sendData.bno,
		type:"put",
		contentType:"application/json",
		data:JSON.stringify(sendData),
		success:successcallback||function(data){
			alert(data);
		},
		error:failcallback||function(data){
			console.log(data);
		}
	});
};
let deleteBoard = function(sendData,successcallback,failcallback){
	$.ajax({
		url:"/freeboard/boards/"+sendData.bno,
		type:"delete",
		success:successcallback||function(data){
			alert(data);
		},
		error:failcallback||function(data){
			console.log(data);
		}
	});
}
let getFileForm = function(editoro){
	const editorData = editoro.getData();
	//console.log(editorData);
	let images = $("figure img");
	let fileForm = [];
	for(let i=0;i<images.length;i++){
		let imageSrc = $(images.get(i)).attr('src');
		//console.log(imageSrc);
		let firstIdx = imageSrc.indexOf('/',1);
		let lastIdx = imageSrc.lastIndexOf('/');
		//console.log(firstIdx+" "+lastIdx);
		let uploadPath = imageSrc.substring(firstIdx+1,lastIdx);
		let uploadFileName = imageSrc.substring(lastIdx+1);
		//console.log(uploadPath+" "+uploadFileName);
		let fForm = {"uploadPath":uploadPath,"uploadFileName":uploadFileName};
		fileForm.push(fForm);
	}
	return fileForm;
}
let boardListModule = (function(){
	let passParam = {
		page:$("#pageForm").find("input[name='page']").val(),
		size:$("#pageForm").find("input[name='size']").val()
	};
	let listGet = function(){
		$.getJSON("/freeboard/boards/",passParam,function(data){
			//console.log(data);
			showList(data);
			putPageNumber(data);
			$(".pagination a").click(function(e){
				e.preventDefault();
				passParam.page = $(this).attr('href');
				listGet();
			});
			
			$(".new-article").click(function(e){
				e.preventDefault();
				moveUsingForm(
						"pageForm",
						"/freeboard/addView",
						passParam.page
				);
			});
		});
	};
	
	let showList = function(data){
		$(".inserted").html("");
		let content = data.result.content;
		for(let i=0;i<content.length;i++){
			//리스트를 보여줌
			$(".inserted").append(generateRow(content[i]));
		}
		$(".inserted a").click(function(e){
			e.preventDefault();
			let no = $(this).attr('href');
			moveUsingForm(
					"pageForm",
					"/freeboard/oneView/"+no,
					passParam.page
			);
		});
	};
	let generateRow = function(row){
		let ret = "";
		ret += "<tr>";
		ret += "	<th scope='row'>"+row.bno+"</th>";
		ret += "	<td><a href='"+row.bno+"'>"+row.title+"</a></td>";
		ret += "	<td>"+"작성자"+"</td>";
		ret += "	<td>"+row.regdate+"</td>";
		ret += "	<td>"+"0"+"</td>";
		ret += "	<td>"+"0"+"</td>";
		ret += "	<td>"+"0"+"</td>";
		ret += "</tr>";
		return ret;
	};
	
	let putPageNumber = function(data){
		let str = "";
		if(data.prevPage != null)
			str += "<li class='page-item'><a class='page-link' href='"+(data.prevPage.pageNumber+1)+"'>이전</a></li>";
		for(let i=0;i<data.pageList.length;i++){
			let pagenum = (data.pageList[i].pageNumber+1);
			let addedClass = "";
			if(passParam.page == pagenum)
				addedClass="active";
			str += "<li class='page-item "+addedClass+"'><a class='page-link' href='"+pagenum+"'>"+pagenum+"</a></li>";
		}
		if(data.nextPage != null)
			str += "<li class='page-item'><a class='page-link' href='"+(data.nextPage.pageNumber+1)+"'>다음</a></li>";
		$(".pagination").html(str);
	};
	
	return {
		listGet:listGet
	};
})();

let boardGetModule = (function(){
	let getOne = function(bno){
		console.log("/freeboard/"+bno);
		$.getJSON("/freeboard/boards/"+bno,function(data){
			//board표시하기
			console.log(data);
			$("#title").val(data.title);
			ClassicEditor
    				.create( document.querySelector( '#editor' ) ,{
    				})
    				.catch( error => {
    					console.error( error );
    				} )
					.then(function(editor){
						editor.setData(data.content);
						editor.isReadOnly = true;
					});
		});
		$(".modify-delete-btn").click(function(e){
			e.preventDefault();
			moveUsingForm(
					"pageForm",
					"/freeboard/modifyView/"+bno,
					-1
			);
		});
	};
	return {
		getOne:getOne
	};
})();

let boardAddModule = (function(){
	let editoro;
	let successmsg;
	let init = function(msg){
		successmsg = msg;
		ClassicEditor
		.create( document.querySelector( '#editor' ) ,{
			extraPlugins: [FileUploadAdapterPlugin ]
        })
        .catch( error => {
        	console.error( error );
        })
		.then(function(editor){
			editoro = editor
		});
		//editor를 설정합니다.
		
		$(".exit-btn").click(function(){
			moveUsingForm(
					"pageForm",
					"/freeboard/listView",
					-1
			);
		});
		//exit btn에 이벤트 추가
		
		$(".add-btn").click(function(){
			let title = $("#title").val();
			let content = editoro.getData();
			let sendData = {
					title:title,
					content:content,
					fileUpload:getFileForm(editoro)
			};
			addBoard(sendData,function(data){
				alert(successmsg);
				moveUsingForm(
					"pageForm",
					"/freeboard/listView",
					-1
				);
			});
			
		})
		//add btn에 이벤트 추가
	};
	
	return{
		init:init
	};
})();

let boardModifyModule = (function(){
	let editoro;
	let modifysuccessmsg;
	let deletesuccessmsg;
	let deleteconfirmmsg;
	let init = function(bno,msg1,msg2,msg3){
		modifysuccessmsg = msg1;
		deletesuccessmsg = msg2;
		deleteconfirmmsg = msg3;
		$.ajax({
			type:'get',
			url:'/freeboard/boards/'+bno,
			success:getSuccess,
			error:function(data){
				console.log(data);
			}
		});
		
		$(".modify-btn").click(function(e){
			let sendData = {
					title:$("#title").val(),
					content:editoro.getData(),
					bno:bno,
					fileUpload:getFileForm(editoro)
			}
			console.log(sendData);
			modifyBoard(sendData,function(data){
				alert(modifysuccessmsg);
				moveUsingForm(
						"pageForm",
						"/freeboard/listView",
						-1
				);
			});
		});
		$(".delete-btn").click(function(e){
			if(confirm(deleteconfirmmsg)){
				let sendData = {
						bno:bno
				}
				deleteBoard(sendData,function(data){
						alert(deletesuccessmsg);
						moveUsingForm(
								"pageForm",
								"/freeboard/listView",
								-1
						);
				});
			}
		});
		$(".exit-btn").click(function(e){
			moveUsingForm(
					"pageForm",
					"/freeboard/oneView/"+bno,
					-1
			);
		});
	};
	let getSuccess = function(data){
		$("#title").val(data.title);
		ClassicEditor
		.create( document.querySelector( '#editor' ) ,{
			extraPlugins: [FileUploadAdapterPlugin ]
		})
		.catch( error => {
			console.error( error );
		} )
		.then(function(editor){
			editoro = editor;
			editoro.setData(data.content);
		});
		//editor를 설정합니다.
	}
	
	return{
		init:init
	};
})();