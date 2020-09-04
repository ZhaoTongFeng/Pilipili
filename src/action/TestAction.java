package action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import bean.Category;
import bean.User;
import bean.Video;
import service.CategoryService;
import service.UserService;
import service.VideoService;
import util.FileUtil;

/**************************************
 * Index
 * 
 **************************************/
public class TestAction extends ActionSupport{
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private VideoService videoService;
	public String msg;
	//操作：初始化数据库（开发用）
	public String test(){
		User user = null;

		
		//类型
		if(categoryService.list().isEmpty()){
			categoryService.add(new Category("动画"));
			categoryService.add(new Category("番剧"));
			categoryService.add(new Category("国创"));
			categoryService.add(new Category("游戏"));
			categoryService.add(new Category("漫画"));
			categoryService.add(new Category("音乐"));
			categoryService.add(new Category("舞蹈"));
			categoryService.add(new Category("科技"));
			categoryService.add(new Category("数码"));
			categoryService.add(new Category("生活"));
			categoryService.add(new Category("鬼畜"));
			categoryService.add(new Category("时尚"));
			categoryService.add(new Category("资讯"));
			categoryService.add(new Category("娱乐"));
			categoryService.add(new Category("专栏"));
			categoryService.add(new Category("电影"));
			categoryService.add(new Category("TV剧"));
			categoryService.add(new Category("影视"));
			categoryService.add(new Category("纪录"));
		}
		//用户
		if(userService.count()==0){
			user = new User("测试用户","xxx@qq.com","passwd");
			userService.add(user);
		}else{
			user = userService.get(1);
		}
		//创建视频和封面文件夹
		String videoPath = "file/video/test/video";
		String imagePath = "file/video/test/image";
		String root = ServletActionContext.getServletContext().getRealPath("");
		File videofile = new File(root,videoPath);
		File imagefile = new File(root,imagePath);
		if(!videofile.exists()){
			videofile.mkdirs();
		}
		if(!imagefile.exists()){
			imagefile.mkdirs();
		}
		
		msg = "";
		msg+="<div>视频："+videofile+"</div>";
		msg+="<div>封面："+imagefile+"</div>";

		
		//视频
		if(videoService.list(0, 10).size()==0){
			//重命名文件，有中文不得行
			int num_video = videofile.listFiles().length;
			int num_image = imagefile.listFiles().length;
			if(num_image==0||num_video==0){
				return "show";
			}
			int index = 0;
			for(int i=0;i<num_video;i++){
				String name_new = String.valueOf(System.currentTimeMillis())+String.valueOf(i);
				File file = videofile.listFiles()[i];
				File newFile = new File(videofile+"\\"+name_new+FileUtil.getExtension(file.getName()));
				file.renameTo(newFile);
			}
			for(int i=0;i<num_image;i++){
				String name_new = String.valueOf(System.currentTimeMillis())+String.valueOf(i);
				
				File file = imagefile.listFiles()[i];
				File newFile = new File(imagefile+"\\"+name_new+FileUtil.getExtension(file.getName()));
				file.renameTo(newFile);
			}
			
			//每个种类导入一定数量视频，
			List<Category> categories= categoryService.list();
			for(Category category:categories){
				for(int i=0;i<MyConfig.TEST_IMPORT_NUM;i++){
					File vFile = videofile.listFiles()[index%num_video];
					File imFile = imagefile.listFiles()[index%num_image];
					Video video = new Video("标题-"+index,"简介-"+index,user);
					video.setSrc(videoPath+"/"+vFile.getName());
					video.setImg(imagePath+"/"+imFile.getName());
					video.setCategory(category);
					video.setLevel(MyConfig.LEVEL_PUBLIC);
					index++;
					System.out.println(video.getSrc());
					videoService.add(video);
				}
			}
			return "index";
		}
		return "show"; 
	}



	
	
	
	
	/**************************************
	 * Setter&Getter
	 * 
	 **************************************/

	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public CategoryService getCategoryService() {
		return categoryService;
	}


	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}


	public VideoService getVideoService() {
		return videoService;
	}


	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
}