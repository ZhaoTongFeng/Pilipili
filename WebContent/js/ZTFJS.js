


function Content(source) {
    this.source = source;
    this.current = source;
    this.join = function (k, v) {
        this.current = this.current + "&" + k + "=" + v;
    };
    this.clear = function () {
        this.current = this.source;
    }
    this.get = function () {
        return this.current;
    };
}


//获取URL中GET参数
function getQueryVariable(variable){
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return(false);
}






//AjaxPost请求

function SendPost(path, content, fun,isform) {
    var outer = this;
    console.log(path+"?"+content);
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", path, true);
    //设置编码格式
    if(typeof isform!="undefind"&&isform!=null&&isform==true){
    	console.log("文件上传")
    	xmlhttp.setRequestHeader("Content-type", "multipart/form-data");    
    }else{
    	//其它场景
    	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");    
    }
    
    xmlhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            if (this.responseText == null) {return;}
            if(this.responseText == ""){return;}
            try{
            	json =JSON.parse(this.responseText);
                if(json==false){return;}
                if(json==null){return;}
                if(typeof json.code === "undefind"){return;}
                console.log(json);
                fun(json);
            }catch(e){
            	console.log(e);
            }
            if(outer.progressdiv!=null){
                outer.progressdiv.hidden=true;
            }
            if(outer.progressItem!=null){
                progressItem.style.width = 0 + "%";
            }
        }
    };
    //获取上传的进度
    xmlhttp.upload.onprogress = function (event) {
        if (event.lengthComputable) {
            var percent = event.loaded / event.total * 100;
            
            if(outer.progressItem!=null){
                progressItem.style.width = percent + "%";
            }
        }
    };
    this.progressItem=document.querySelector("#progress .progress-item");
    this.progressdiv = document.getElementById("progress");
    if(this.progressdiv!=null){
        this.progressdiv.hidden=false;
    }
    
    xmlhttp.send(content);
}









/**
 * 替换所有匹配exp的字符串为指定字符串
 * @param exp 被替换部分的正则
 * @param newStr 替换成的字符串
 */
String.prototype.replaceAll = function (exp, newStr) {
	return this.replace(new RegExp(exp, "gm"), newStr);
};

/**
 * 原型：字符串格式化
 * @param args 格式化参数值
 */
String.prototype.format = function(args) {
	var result = this;
	if (arguments.length < 1) {
		return result;
	}

	var data = arguments; // 如果模板参数是数组
	if (arguments.length == 1 && typeof (args) == "object") {
		// 如果模板参数是对象
		data = args;
	}
	for ( var key in data) {
		var value = data[key];
		if (undefined != value) {
			result = result.replaceAll("\\{" + key + "\\}", value);
		}
	}
	return result;
}





/*************************************************************************
 * 上拉刷新 需要在后面定义AppendSomething()
 * 滚动的时候还可以锚定DIV，不过目前这里已经删除了这个功能
 *************************************************************************/

//获取文档完整的高度
function getScrollHeight() {
    return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
}
//获取当前可视范围的高度
function getClientHeight() {
    var clientHeight = 0;
    if (document.body.clientHeight && document.documentElement.clientHeight) {
        clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight);
    } else {
        clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight);
    }
    return clientHeight;
}
//获取滚动条当前的位置
function getScrollTop() {
    var scrollTop = 0;
    if (document.documentElement && document.documentElement.scrollTop) {
        scrollTop = document.documentElement.scrollTop;
    } else if (document.body) {
        scrollTop = document.body.scrollTop;
    }
    return scrollTop;
}
function scrollFunc(e) {
    if("undefined" != typeof ScrollEvent){
        ScrollEvent();
    }
    if (Math.round(getScrollTop() + getClientHeight()) >= getScrollHeight() - 200) {
        AppendSomething();
    }
};
//移动设备滚动监听
function InitMobileLoad(MainRightDiv){
    try{
        MainRightDiv.addEventListener("touchstart", function (e) {
            scrollFunc();
        });
        MainRightDiv.addEventListener("touchmove", function (e) {
            scrollFunc();
        });
        MainRightDiv.addEventListener("touchend", function (e) {
            scrollFunc();
        });
    }catch(e){
        console.log(e);
    }
}
function initScrollListener(){
    //给页面绑定滑轮滚动事件 
    if (document.addEventListener) {document.addEventListener('DOMMouseScroll', scrollFunc, false);}//firefox
    window.onmousewheel = document.onmousewheel = scrollFunc;//ie 谷歌 
    //绑定移动端滑动事件
    InitMobileLoad(document.body);
}




