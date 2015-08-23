var data;
var XMLHttpReq = false;
var operateName = "";

//创建Ajax对象       
function createXMLHttpRequest() {
	if(window.ActiveXObject) { 
		XMLHttpReq = new Array("Msxml2.XMLHTTP.6.0", "Msxml2.XMLHTTP.5.0", "Msxml2.XMLHTTP.4.0", "MSXML2.XMLHTTP.3.0", "MSXML2.XMLHTTP", "Microsoft.XMLHTTP");
		XMLHttpReq=createReq(XMLHttpReq);
	} else if (window.XMLHttpRequest) { 
		XMLHttpReq = new XMLHttpRequest();
		/*
		try {
			XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				XMLHttpReq = new ActiveXObject("'MSXML2.XMLHTTP.3.0");
			}
		}
		*/
	}
}

 function createReq(axarray) {

  var returnValue;  
  for (var i = 0; i < axarray.length; i++) {
    try {
      returnValue = new ActiveXObject(axarray[i]);
      break;
    }
    catch (ex) { /* ignore */ }
  }
  return returnValue;
};

//发送请求函数
function sendRequest(url, name) {

	//创建Ajax.Request对象，对应于发送请求
	operateName = name;
	createXMLHttpRequest();
	XMLHttpReq.open("GET", url, true);
	XMLHttpReq.onreadystatechange = processResponse;
	
	XMLHttpReq.send(null);

}
//返回信息函数
function processResponse(request){

	if (XMLHttpReq.readyState == 4) {
		if(operateName==undefined){
			data = XMLHttpReq.responseText; 
		}else{
			var str = operateName+"= XMLHttpReq.responseText;"
			eval(str);
		}
	}
}