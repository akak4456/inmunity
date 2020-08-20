$(".boardindex-btn").click(function(e){
		e.preventDefault();
		window.location.href = "/";
	});
	$(".notice-btn").click(function(e){
		e.preventDefault();
		window.location.href = "/notifyboard/boards";
	});
	if(rootAddress == "/notifyboard"){
		$(".notice-btn").addClass('active');
	}else{
		$(".boardindex-btn").addClass('active');
	}
	$(".locale-ko-btn").click(function(e){
		e.preventDefault();
		window.location.href = window.location.href.split('?')[0]+"?locale=ko";
	});
	$(".locale-en-btn").click(function(e){
		e.preventDefault();
		window.location.href = window.location.href.split('?')[0]+"?locale=en";
	});
	
$(".newbieboard-btn").click(function(e){
	e.preventDefault();
		window.location.href = "/newbieboard/boards";
})
$(".jobboard-btn").click(function(e){
	e.preventDefault();
		window.location.href = "/jobboard/boards";
})