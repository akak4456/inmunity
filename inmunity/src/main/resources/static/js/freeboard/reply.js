let replyModule = (function(){
	let totalPageNum = -1;
	let addEditoro;
	let modifyEditoro;
	let rereplyAddEditoro;
	let passParam = {
			page:1,
			size:10
	};
	
	let useremail;
	let username;
	let bno;
	let replyaddsuccessmsg;
	let replymodifysuccessmsg;
	let replydeletesuccessmsg;
	let addReply = function(sendData,successcallback,failcallback){
		$.ajax({
			url:"/freeboard/reply/"+bno,
			type:"post",
			contentType:"application/json",
			data:JSON.stringify(sendData),
			success:successcallback||function(data){
				alert(data);
			},
			error:failcallback||function(data){
				alert("error occur");
				console.log(data);
			}
		});
	};
	let modifyReply = function(sendData,successcallback,failcallback){
		$.ajax({
			url:"/freeboard/reply/"+bno+"/"+sendData.rno,
			type:"put",
			contentType:"application/json",
			data:JSON.stringify(sendData),
			success:successcallback||function(data){
				alert(data);
			},
			error:failcallback||function(data){
				alert("error occur");
				console.log(data);
			}
		});
	};
	let deleteReply = function(sendData,successcallback,failcallback){
		$.ajax({
			url:"/freeboard/reply/"+bno+"/"+sendData.rno,
			type:"delete",
			contentType:"application/json",
			data:JSON.stringify(sendData),
			success:successcallback||function(data){
				alert(data);
			},
			error:failcallback||function(data){
				alert("error occur");
				console.log(data);
			}
		});
	};
	
	
	let showModifyAfterElement = function(element){
		let inserted = "";
		inserted += "<div class='between-reply-editor'>";
		inserted += "	<div id='modify-editor'></div>";
		inserted += "	<div class='my-btn-grp clearfix' role='group'>";
		inserted += "		<button class='btn btn-success float-right reply-modifyck-btn'>댓글수정하기</button>";
		inserted += "	</div>";
		inserted += "</div>";
		element.after(inserted);
		ClassicEditor
		.create( document.querySelector( '#modify-editor' ) ,{
			toolbar: ['bold', 'italic']
        })
        .catch( error => {
        	console.error( error );
        })
		.then(function(editor){
			modifyEditoro = editor;
		});
		let rno = element.data("rno");
		let rowuseremail = element.data("useremail");
		$(".reply-modifyck-btn").click(function(e){
			let reply = modifyEditoro.getData();
			let sendData = {
					rno:rno,
					reply:reply,
					useremail:rowuseremail,
					replyer:username
			};
			modifyReply(sendData,function(data){
				alert(replymodifysuccessmsg);
				listGet();
				$(".between-reply-editor").remove();
			});
		});
	};
	let showRereplyAddAfterElement = function(element){
		let inserted = "";
		inserted += "<div class='between-reply-editor'>";
		inserted += "	<div id='rereply-editor'></div>";
		inserted += "	<div class='my-btn-grp clearfix' role='group'>";
		inserted += "		<button class='btn btn-success float-right rereply-addck-btn'>댓글추가하기</button>";
		inserted += "	</div>";
		inserted += "</div>";
		element.after(inserted);
		ClassicEditor
		.create( document.querySelector( '#rereply-editor' ) ,{
			toolbar: ['bold', 'italic']
        })
        .catch( error => {
        	console.error( error );
        })
		.then(function(editor){
			rereplyAddEditoro = editor;
		});
		let parent_rno = element.data("rno");
		$(".rereply-addck-btn").click(function(e){
			let reply = rereplyAddEditoro.getData();
			let sendData = {
					reply:reply,
					replyer:username,
					parent_rno:parent_rno,
					useremail:useremail
			};
			addReply(sendData,function(data){
				alert(replyaddsuccessmsg);
				listGet();
				$(".between-reply.editor").remove();
			});
		});
	};
	let afterListGet = function(data){
		showList(data);
		putPageNumber(data);
		$(".pagination a").click(function(e){
			e.preventDefault();
			passParam.page = $(this).attr('href');
			listGet(bno);
		});
		$(".reply-modify-btn").click(function(e){
			e.preventDefault();
			$(".between-reply-editor").remove();
			showModifyAfterElement($(this).parent().parent().parent());
		});
		$(".reply-delete-btn").click(function(e){
			e.preventDefault();
			let divElement = $(this).parent().parent().parent();
			let rno = divElement.data("rno");
			let rowuseremail = divElement.data("useremail");
			let sendData = {
					rno:rno,
					useremail:rowuseremail
			};
			deleteReply(sendData,function(data){
				alert(replydeletesuccessmsg);
				listGet();
			});
		});
		$(".rereply-add-btn").click(function(e){
			e.preventDefault();
			$(".between-reply-editor").remove();
			showRereplyAddAfterElement($(this).parent().parent().parent());
		});
	}
	let listGet = function(){
		$.getJSON("/freeboard/reply/"+bno,passParam,function(data){
			totalPageNum = data.totalPageNum;
			if(totalPageNum==0){
				$(".reply-inserted").hide();
			}else{
				$(".reply-inserted").show();
			}
			afterListGet(data);
		});
		
	};
	let showList = function(data){
		$(".reply-inserted").html("");
		let content = data.result.content;
		for(let i=0;i<content.length;i++){
			//리스트를 보여줌
			$(".reply-inserted").append(generateRow(content[i]));
		}
	};
	let generateRow = function(row){
		let ret = "";
		if(row.parent_rno == -1)
		ret += "<div class='reply'";
		else
		ret += "<div class='rereply'";
		ret += "data-rno='"+row.rno+"' data-useremail='"+row.useremail+"'>";
		ret += "	<div class='clearfix'>";
		ret += "		<span class='float-left'>"+row.replyer+"</span>";
		if(row.isdelete == "N"){
		ret += "		<span class='reply-btn float-right'><a class='reply-delete-btn'>삭제</a></span>";
		ret += "		<span class='reply-btn float-right'><a class='reply-modify-btn'>수정</a></span>";
		if(row.parent_rno == -1)
		ret += "		<span class='reply-btn float-right'><a class='rereply-add-btn'>대댓글</a></span>";
		}
		ret += "	</div>"
		ret += "	<div class='clearfix'>";
		ret += "		"+row.reply;
		ret += "	</div>"
		ret += "	<div class='clearfix'>";
		if(row.isdelete == "N"){
		ret += "	<span class='update-date float-left'>"+row.updatedate+"</span>";
		}
		ret += "	</div>"
		ret += "</div>";
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
	let showLastPage = function(){
		passParam.page = totalPageNum;
		$.getJSON("/freeboard/reply/"+bno,passParam,function(data){
			if(totalPageNum < data.totalPageNum){
				passParam.page = data.totalPageNum;
				listGet();
				return;
			}
			afterListGet(data);
		});
	};
	let init = function(email,name,no,msg1,msg2,msg3){
		bno = no;
		if(email != null){
			useremail = email;
			username = name;
			replyaddsuccessmsg = msg1;
			replymodifysuccessmsg = msg2;
			replydeletesuccessmsg = msg3;
			ClassicEditor
			.create( document.querySelector( '#reply-add-editor' ) ,{
				toolbar: ['bold', 'italic']
	        })
	        .catch( error => {
	        	console.error( error );
	        })
			.then(function(editor){
				addEditoro = editor;
			});
			$(".reply-add-btn").click(function(e){
				let reply = addEditoro.getData();
				let sendData = {
						reply:reply,
						replyer:username,
						parent_rno:-1,
						useremail:useremail
				};
				addReply(sendData,function(data){
					alert(replyaddsuccessmsg);
					showLastPage();
					addEditoro.setData("");
				});
			});
		}
		listGet();
	};
	
	return {
		init:init
	};
})();