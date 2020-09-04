
//jQuery方式
$(document).ready(function(){
var tempimg = {};
	imgs = document.getElementsByTagName("img");
	for(var i=1;i<imgs.length;i++){
		if(imgs[i].attributes["video_src"]!=null){
			//video_src = imgs[i].attributes["video_src"].value;
			//console.log(video_src)
			imgs[i].onmouseover = function(){

				video_src = this.attributes["video_src"].value;
				video_id = this.attributes["video_id"].value;
				tempimg[video_id] = this;
				
				videoNode = document.createElement("video");
				videoNode.setAttribute("video_id",video_id);
				videoNode.style="width:100%;height:120px;background-color:black;"
				videoNode.autoplay="autoplay"
				sourceNode = document.createElement("source");
				sourceNode.src = video_src;
				
				
				videoNode.insertBefore(sourceNode,null);
				videoNode.onmouseout = function(){
					video_id = this.attributes["video_id"].value;
					this.parentNode.replaceChild(tempimg[video_id],this);
				}


				this.parentNode.replaceChild(videoNode,this);
			}
		}
	}
});
