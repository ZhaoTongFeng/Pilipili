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
	//��������ʼ�����ݿ⣨�����ã�
	public String test(){
		User user = null;

		
		//����
		if(categoryService.list().isEmpty()){
			categoryService.add(new Category("����"));
			categoryService.add(new Category("����"));
			categoryService.add(new Category("����"));
			categoryService.add(new Category("��Ϸ"));
			categoryService.add(new Category("����"));
			categoryService.add(new Category("����"));
			categoryService.add(new Category("�赸"));
			categoryService.add(new Category("�Ƽ�"));
			categoryService.add(new Category("����"));
			categoryService.add(new Category("����"));
			categoryService.add(new Category("����"));
			categoryService.add(new Category("ʱ��"));
			categoryService.add(new Category("��Ѷ"));
			categoryService.add(new Category("����"));
			categoryService.add(new Category("ר��"));
			categoryService.add(new Category("��Ӱ"));
			categoryService.add(new Category("TV��"));
			categoryService.add(new Category("Ӱ��"));
			categoryService.add(new Category("��¼"));
		}
		//�û�
		if(userService.count()==0){
			user = new User("�����û�","xxx@qq.com","passwd");
			userService.add(user);
		}else{
			user = userService.get(1);
		}
		//������Ƶ�ͷ����ļ���
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
		msg+="<div>��Ƶ��"+videofile+"</div>";
		msg+="<div>���棺"+imagefile+"</div>";

		
		//��Ƶ
		if(videoService.list(0, 10).size()==0){
			//�������ļ��������Ĳ�����
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
			
			//ÿ�����ർ��һ��������Ƶ��
			List<Category> categories= categoryService.list();
			for(Category category:categories){
				for(int i=0;i<MyConfig.TEST_IMPORT_NUM;i++){
					File vFile = videofile.listFiles()[index%num_video];
					File imFile = imagefile.listFiles()[index%num_image];
					Video video = new Video("����-"+index,"���-"+index,user);
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