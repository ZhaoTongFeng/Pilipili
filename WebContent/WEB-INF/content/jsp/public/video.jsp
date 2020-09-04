<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page isELIgnored="false"%>


<html>
<head>
<title>${video.title }</title>
<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<%@include file="/WEB-INF/content/jsp/public/header.jsp"%>
	
	<input id="video_id" hidden value="${video.id }"/>
	<input id="isVip" hidden value="${user.isVip }"/>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-1"></div>
			<!-- 左边 -->
			<div class="col-sm-8">
				<!-- 视频抬头 ：标题，播放量，弹幕，时间，作者昵称，类型-->
				<div >
					<div>
						<h4>${video.title }</h4>
					</div>
					<div>
						<small style="color:red"><s:if test="video.isVip == true">[VIP]</s:if></small>
						<small style="color:#999">${video.category.name }</small>
						<small style="color:#999">${video.num_view }播放</small>
						<small style="color:#999">${video.time_create }</small>
					</div>
					<div>
					<img src="${user.img }" style="height:20px;width:20px" class="rounded-circle"/>
					<small style="color:#999">${video.user.name }</small>
					</div>

				</div>

				<!-- 视频播放器 -->
				
				<video width="100%" height="auto" controls="controls"
					autoplay="autoplay"
					style="max-height: 700px; background-color: black;margin:16px 0px">
					<source src="${video.src }" type="video/ogg" />
					<source src="${video.src }" type="video/mp4" />
					<source src="${video.src }" type="video/webm" />
				</video>
				<!-- 弹幕发送区 -->
				<div style="width:100%;">
					
				</div>


				


				<!-- 视频数据，点赞，投币，收藏，转载 -->
				<div style="margin:0px 0px 16px 0px;">
					<button onclick="SetGood(this)" class="btn btn-light">赞：<span>${video.num_good }</span></button>
					<button onclick="GiveCoin(this)" class="btn btn-light">投币：<span>${video.balance }</span></button>
					<button onclick="Mark(this)" class="btn btn-light">收藏：<span>${video.num_mark }</span></button>
					<small>硬币余额：</small><small id="user_balance">${user.balance }</small>
					<span id="err_2"></span>
				</div>
				
				<div style="margin:0px 0px 16px 0px;">

					<small>简介：${video.introduction }</small>
				</div>

				<!-- 自定义标签 -->
				<div style="width:100%;">
					
				</div>

				<!-- 评论区 （20条）-->
				<div>
				
					<div style="padding:16px 0px">
					<h4 ><span id="num_comment">${video.num_comment }</span>评论</h4>
					</div>
					
					<!-- 发送区 -->
					
					<div class="media" >
						<s:if test="user.img == null"><img src="file/image/system/pay.png" class="mr-3 rounded-circle" style="height:64px;width:64px"/></s:if>
						<s:else><img src="${user.img }" class="mr-3 rounded-circle" style="height:64px;width:64px"/></s:else>
					  	
					  	<div class="media-body">
					  	
					  		<div class="row">					  		
						  		<div class="col-sm-11">
								  	<div class="form-group">
								    	<textarea class="form-control" id="commentEditBox" rows="3" style="height:64px"></textarea>
								  	</div>
						  		</div>
						  		<div class="col-sm-1">
						  			<button id="submit" style="width:64px;height:64px" class="btn btn-light">发表<br>评论</button>
						  		</div>
					  		</div>
					  		<!-- 错误提示 -->
					  		<div style="height:50px">
					  			<small id="errSpan" ></small>
					  			<a hidden id="disreplayA" href="javascript:void();" onclick="disReply()"><small>取消回复</small></a>
					  		</div>	
					  	</div>
					</div>
					<!-- 显示区 -->
					<div id="CommentBox">
					


					</div>
				</div>
			</div>

			<!-- 右边 -->
			<div class="col-sm-2" style="margin-top:64px;">

				<h5>相关推荐</h5>
				<div class="row" style="margin-top:16px;">
					<!-- 视频： 封面，播放量，弹幕，作者昵称，标题 -->
					<s:iterator value="videos" var="v">
						<div class="col-sm-8" style="margin-bottom: 16px">
							<a href="video?id=${v.id}">
								<img src="${v.img }" class="img-fluid" style="width:100%;height:100px"/>
							</a>
						</div>
						<div class="col-sm-4" style="padding:0px;margin:0px">
						
							<div>
							</div>
							<span>${v.title }</span> 
							<div>
								<small style="color:#999">${v.user.name }</small>
								<br>
								<small style="color:#999">${v.num_view }播放</small>
							</div>
						</div>
					</s:iterator>
				</div>
			</div>

			<div class="col-sm-1"></div>

		</div>
	</div>
</body>
</html>






<script type="text/javascript">
//点赞投币收藏边上的errSpan
err2 = $("#err_2")
	
video_id = document.getElementById("video_id").value
user_balance = document.getElementById("user_balance");

//投币
function GiveCoin(ele){
	form = new FormData();
	form.append("id",video_id);
	$.ajax({
		url:'video/giveCoin',
		type:'POST',
		data:form,
		async:false,
		cache:false,
		dataType:"json",
		contentType:false,
		processData:false,
		success:function(data){
            console.log(data)
            if(data.code==0){
            	ele.children[0].innerHTML = data.balance_video//当前视频收益
            	user_balance.innerHTML = data.balance;
            	err2.innerHTML = data.coin
            }else{
            	err2.html(data.errmsg);
            }
		},
		error:function(returndata){
			console.log(returndata)
		}
	});
}


//收藏
function Mark(ele){
	form = new FormData();
	form.append("id",video_id);
	$.ajax({
		url:'video/mark',
		type:'POST',
		data:form,
		async:false,
		cache:false,
		dataType:"json",
		contentType:false,
		processData:false,
		success:function(data){
            console.log(data)
            if(data.code==0){
            	ele.children[0].innerHTML = data.num_mark
            	if(data.mark==true){
            		err2.html("收藏成功");
            	}else{
            		err2.html("取消收藏");
            	}
            }else{
            	err2.html(data.errmsg);
            }
		},
		error:function(returndata){
			console.log(returndata)
		}
	});
};


//点赞视频
function SetGood(ele){
	form = new FormData();
	form.append("id",video_id);
	$.ajax({
		url:'video/goodVideo',
		type:'POST',
		data:form,
		async:false,
		cache:false,
		dataType:"json",
		contentType:false,
		processData:false,
		success:function(data){
            console.log(data)
            if(data.code==0){
            	ele.children[0].innerHTML = data.num_good;
            	if(data.good==true){
            		err2.html("点赞成功");
            	}else{
            		err2.html("取消点赞");
            	}
            }else{
            	err2.html(data.errmsg);
            }
		},
		error:function(returndata){
			console.log(returndata)
		}
	});	
}

//点赞评论
function SetCommentGood(ele){
	comment_id = ele.attributes["comment_id"].value
	form = new FormData();
	form.append("id",comment_id);
	$.ajax({
		url:'comment/goodComment',
		type:'POST',
		data:form,
		async:false,
		cache:false,
		dataType:"json",
		contentType:false,
		processData:false,
		success:function(data){
          console.log(data)
          if(data.code==0){
          	ele.children[0].innerHTML = data.num_good;
          }
		},
		error:function(returndata){
			console.log(returndata)
		}
	});
}



//当前评论/回复目标ID，如果是0表示评论视频，否则是回复评论
var comment_id = 0;

//点击加载回复后，把他的回复插入到这个box中
var curCommentBox = null;

var curloadReplyErrSpan = null;



//返回待插入节点的上一个兄弟节点
function getReplyEle(){

	return curCommentBox.children[curCommentBox.children.length-1];
}

var num_commentSpan = document.getElementById("num_comment");
const errSpan = document.getElementById("errSpan");
const CommentBox = document.getElementById("CommentBox");
const commentEditBox = document.getElementById("commentEditBox");
const loadReplyErrSpan = document.getElementById("loadReplyErrSpan");

var disreplayA = document.getElementById("disreplayA");



//往目标节点插入评论
function InsertTo(parent,comment, element,isChild,isVip) {
	//如果是子评论，media class加一个mt-3往里面缩进
	template = "";
	if(isChild){
		template += '<div  class="media mt-3" style="min-height:100px;">';
		template +='<img class="mr-3 rounded-circle" src="{img}" style="height:32px;width:32px"/>';
	}else{
		template += '<div  class="media" style="min-height:100px;">';
		template +='<img class="mr-3 rounded-circle" src="{img}" style="height:64px;width:64px"/>';
	}
	//根据id找到点击回复之后的box
	template +='<div class="media-body" id="comment_{comment_id}">';
	if(isVip==true){
		template +='<h6 style="font-size:12px;color:gold">{user_name}</h6>';	
	}else{
		template +='<h6 style="font-size:12px;">{user_name}</h6>';	
	}
	
	
	template +='<p style="font-size:14px">{content}</p>';
	template +='<small style="padding-right:8px">{time}</small>'
	template +='<a style="padding-right:8px" href="javascript:void(0);" comment_id="{comment_id}" onclick="SetCommentGood(this)" ><small>{num_good}</small><small style="padding-left:4px">赞</small></a>';
	template +='<a style="padding-right:8px" href="javascript:void(0);" onclick="reply(this)" comment_id="{comment_id}" ><small>回复</small></a>';
	
	//评论发送成功之后，在找到的box的这个位置插入子评论即可（倒数第二个的后面）
	template +="<div>"
	template +='<small>总共</small><small id="num_reply_{comment_id}">{num_reply}</small><small>条回复，</small>';
	template +='<a id="disreplayA" href="javascript:void(0);" onclick="loadReply(this)" comment_id="{comment_id}"><small>加载回复 <span id="ErrSpan_{comment_id}"></span></small></a>'
	template +='<hr>';
	template +='</div>';
	template +='</div>';
	template +='</div>';
	
	node = document.createElement("div");
	node.innerHTML = template.format(comment);
	node.id = comment.comment_id;
	
	parent.insertBefore(node,element );
}


//从Bean组织上面模板需要的数据
function getCommentData(model,con){
	img = "file/image/system/pay.png";
	if(model.user.img!=null){
		img = model.user.img
	}
	return {
			img : img,
			user_name : model.user.name,
			content : con,
			time : model.time_create,
			comment_id : model.id,
			num_good:model.num_good,
			num_reply:model.num_reply
		};
}










//点击加载回复
function replyLoader() {
	var outer = this;
	this.page = 0;
	this.isCanRequest = true;
	this.reset = function(){
		this.page = 0;
		this.isCanRequest = true;
	}
	this.load = function() {
		if (this.isCanRequest == false) {
			return;
		}
		this.isCanRequest = false;
		path = "comment/getChildComment"
		content = "page=" + this.page + "&video_id=" + video_id+ "&comment_id=" + comment_id;
		SendPost(path, content, function(res) {
			if (res.code == 0) {
				if (res.comments.length == 0) {
					curloadReplyErrSpan.innerHTML = "没有更多评论";
					return;
				}
				for (var i = 0; i < res.comments.length; i++) {
					comment = getCommentData(res.comments[i],res.comments[i].content);
					InsertTo(curCommentBox,comment, getReplyEle(),true,res.comments[i].user.isVip);
				}

				outer.isCanRequest = true;
				outer.page++;
			} else {
				errSpan.innerHTML = res.errmsg;
			}

		});
	}
}

var rLoader = new replyLoader();

//加载更多回复
function loadReply(element){
	//每次都要检测id是不是之前的id，如果是就继续翻页加载，如果不是，要把page重置
	
	newId = element.attributes["comment_id"].value;
	if(comment_id!=newId){
		rLoader.reset();
		comment_id = newId;
	}
	curloadReplyErrSpan = document.getElementById("ErrSpan_"+comment_id);
	curCommentBox = document.getElementById("comment_"+comment_id);

	rLoader.load();	
}




//回复评论，1.设置目标ID
function reply(element) {
	comment_id = element.attributes["comment_id"].value;
	
	curCommentBox = document.getElementById("comment_"+comment_id);

	tempa = document.createElement("a");
	tempa.href="#commentEditBox";
	tempa.click();
	disreplayA.hidden=false;
}


//取消回复，1.重置相关数据和视图
function disReply() {
	comment_id = 0;
	curCommentBox = null;
	disreplayA.hidden=true;
}


//发送评论，2.如果comment_id不等于0，在服务端就会做回复处理，返回这里
document.getElementById("submit").onclick = function() {
	con = commentEditBox.value;

	if(con.length==0){
		
		errSpan.innerHTML = "评论不能为空";
		return;
	}
	$.ajax({
		url:'comment/addComment',
		type:'POST',
		async:false,
		cache:false,
		data:{
			content:con,
			video_id:video_id,
			comment_id:comment_id
		},
		dataType:"json",

		success:function(data){
            console.log(data)
            if(data.code==0){
            	newComment = getCommentData(data.newComment,con);
            	if(data.isReply){
    				//回复
    				
    				errSpan.innerHTML = "回复成功";
    				
    				num_reply = document.getElementById("num_reply_"+comment_id).innerHTML = data.comment.num_reply//回复目标数据
    				InsertTo(curCommentBox,newComment, getReplyEle(),true,isVip.value);//插入到刚才点的回复按钮对应的box下面
            		
            	}else{
    				//评论视频
    				errSpan.innerHTML = "评论成功";
    				InsertTo(CommentBox,newComment,CommentBox.children[0],false,isVip.value);//插入到box的最前面
            	}
    			disReply();
    			commentEditBox.value="";
    			num_commentSpan.innerHTML = data.num_comment;
            }else{
            	errSpan.innerHTML = res.errmsg;
            }

		},
		error:function(returndata){
			console.log(returndata)
		}
	});
}














//滚动加载评论
function Loader() {
	var outer = this;
	this.page = 0;
	this.isCanRequest = true;

	this.load = function() {
		if (this.isCanRequest == false) {
			return;
		}
		this.isCanRequest = false;
		path = "comment/getMoreComment"
		content = "page=" + this.page + "&video_id=" + video_id;
		SendPost(path, content, function(res) {
			if (res.code == 0) {
				if (res.data.length == 0) {
					return;
				}
				for (var i = 0; i < res.data.length; i++) {
					comment = getCommentData(res.data[i],res.data[i].content);
					InsertTo(CommentBox,comment, null,false,res.data[i].user.isVip);
				}

				outer.isCanRequest = true;
				outer.page++;
			} else {
				errSpan.innerHTML = res.errmsg;
			}

		});
	}
}







//绑定到滚动监听
var loader = new Loader();
loader.load();
function AppendSomething() {
	loader.load();
}
initScrollListener();









</script>


