function getLogAddress(src){
	const lastIdx = src.lastIndexOf(".");
	const boardname = String(src).substring(lastIdx+1);
	return boardname.toLowerCase();
}