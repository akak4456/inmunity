let boardModule = (function(){
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
				alert("error occur!");
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
				alert("error occur!");
				console.log(data);
			}
		});
	};
	let deleteBoard = function(sendData,successcallback,failcallback){
		$.ajax({
			url:"/freeboard/boards/"+sendData.bno,
			type:"delete",
			contentType:"application/json",
			data:JSON.stringify(sendData),
			success:successcallback||function(data){
				alert(data);
			},
			error:failcallback||function(data){
				alert("error occur!");
				console.log(data);
			}
		});
	};
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
	};
	return {
		addBoard:addBoard,
		modifyBoard:modifyBoard,
		deleteBoard:deleteBoard,
		getFileForm:getFileForm
	};
})();