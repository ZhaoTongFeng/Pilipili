package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import bean.Category;
import bean.Comment;
import bean.Message;
import bean.User;
import bean.Video;
import service.CategoryService;
import service.CommentService;
import service.MessageService;
import service.UserService;
import service.VideoService;
import util.GoodUtil;

public class PgAction  extends ActionSupport{
	@Autowired
	private UserService userService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MessageService messageService;	
	
	//��������
	private String content="";
	
	//����ID
	private int id = 0;
	private String name;
	
	//��ҳ����
	private int maxPage = 0;
	private int prevpage = -1;
	private int page = 0;
	private int nextpage = -1;
	private ArrayList pages;
	private int start;
	private int count;
	
	private User user;
	
	private List<User> users;
	private List<Video> videos;
	private List<Comment> comments;
	private List<Category> categories;
	private List<Message> messages;
	
	
	//�������ղؼ�ɾ����Ƶ����ת���ղؼ�
	public String deleteFromBookmark(){
		setUserBySession();
		if(user!=null && id!=0){
			boolean isMark = userService.MarkVideo(user, id);
			Video video = videoService.get(id);
			videoService.marked(video, user.getId(), isMark);
			return "bookmark";
		}else{
			return "sign_in";
		}

		
		
	}
	
	//������ɾ��XX����ת��ˢ�£���XX����ҳ��
	//����id
	public String deleteUser(){
		setUserBySession();
		if(user==null){
			return "sign";
		}
		if(user.getLevel()!=MyConfig.LEVEL_ADMIN){
			return "sign";
		}
		User dbuser = userService.get(id);
		if(dbuser!=null){
			if(dbuser.getLevel()==MyConfig.LEVEL_BAN){
				//���
				if(dbuser.isVIP()){
					dbuser.setLevel(MyConfig.LEVEL_VIP);
				}else{
					dbuser.setLevel(MyConfig.LEVEL_USER);
				}
			}else{
				//���
				dbuser.setLevel(MyConfig.LEVEL_BAN);
			}
			userService.update(dbuser);
			
		}
		return "succ";
	}
	public String deleteComment(){
		setUserBySession();
		if(user==null){
			return "sign";
		}
		Comment dbcomment = commentService.get(id);
		if(dbcomment!=null){
			commentService.delete(dbcomment);
		}
		return "succ";
	}
	
	
	public String deleteVideo(){
		setUserBySession();
		if(user==null){
			return "sign";
		}
		Video dbvideo = videoService.get(id);
		if(dbvideo!=null){
			videoService.delete(dbvideo);
		}
		return "succ";
	}
	public String deleteCategory(){
		setUserBySession();
		if(user==null){
			return "sign";
		}
		if(user.getLevel()!=MyConfig.LEVEL_ADMIN){
			return "sign";
		}
		
		Category dbcategory = categoryService.get(id);
		if(dbcategory!=null){
			categoryService.delete(dbcategory);
		}
		return "succ";
	}
	
	
	//����������XX����ת��XX����ҳ��
	//����id,name
	public String updateCategory(){
		setUserBySession();
		if(user==null){
			return "sign";
		}
		if(user.getLevel()!=MyConfig.LEVEL_ADMIN){
			return "sign";
		}
		if(name!=null&&id!=0){
			Category dbCategory = categoryService.get(id);
			dbCategory.setName(name);
			categoryService.update(dbCategory);
		}
		return "succ";
	}

	//�ղؼ�
	public String bookmarkVideo(){
		setUserBySession();
		if(user==null){
			return "sign_in";
		}
		count = 20;
		Integer[] ids = GoodUtil.StringArraytoIntArray(user.getVideo_mark());

		this.setPageInfo((long) ids.length);
		videos = videoService.markList(page*count, count, ids);
		return "succ";
	}
	
	//��Ϣ
	public String messageList(){
		setUserBySession();
		if(user==null){
			return "sign_in";
		}
		count = 20;
		
		this.setPageInfo(messageService.countUser(user.getId()));
		messages = messageService.listUser(page*count, count, user.getId());
		return "succ";
	}
	
	
	public String auditManage(){
		setUserBySession();
		if(user==null){
			return "sign_in";
		}
		count = 20;
		if(user.getLevel()==MyConfig.LEVEL_ADMIN){
			this.setPageInfo(videoService.auditCount());
			videos = videoService.auditList(page*count,count);
			return "succ";
		}else{
			return "sign_in";
		}
	}
	
	
	//��ת��Searchҳ��
	public String Search(){
		
		count = 24;
		start = page*count;
		if(id==0&&content.equals("")){
			//����Ϊ�գ�����������

			this.setPageInfo(videoService.count());
			videos = videoService.list(start, count);
		}else if(id!=0&&content.equals("")){
			//���Ͳ�Ϊ�գ����ط�����
			this.setPageInfo(videoService.categoryCount(id));
			videos = videoService.categoryList(start, count, id);
		}else if(id==0&&!content.equals("")){
			//�������ݲ�Ϊ�գ�ȫ������
			this.setPageInfo(videoService.countSearch(content));
			videos = videoService.search(start, count, content);
		}else if(id!=0&&!content.equals("")){
			//��������Ϊ�գ�����������
			this.setPageInfo(videoService.countSearchWithCategory(id, content));
			videos = videoService.searchWithCategory(start, count, id, content);
		}else{
			return "index";
		}
		setUserBySession();
		categories = categoryService.list();
		return "succ";
	}


	
	//��ת������ҳ��
	public String categoryList(){
		id = id==0?1:id;
		setUserBySession();
		count = 24;
		categories = categoryService.list();

		this.setPageInfo(videoService.categoryCount(id));
		
		int start = page*count;
		videos = videoService.categoryList(start, count, id);
		return "succ";
	}

	
	//��ת�����ֹ���ҳ��
	public String manageUser(){
		setUserBySession();
		if(user==null){
			return "sign_in";
		}
		count = 20;

		if(user.getLevel()==MyConfig.LEVEL_ADMIN){
			this.setPageInfo(userService.count());
			users = userService.list(page*count,count);
			return "succ";
		}else{
			return "sign_in";
		}
	}
	//�������
	public String manageCategory(){
		setUserBySession();
		if(user==null){
			return "sign_in";
		}
		count = 20;
		start = page*count;
		Long totalCount = null;
		if(user.getLevel()==MyConfig.LEVEL_ADMIN){
			totalCount = categoryService.count();
			this.setPageInfo(totalCount);
			categories = categoryService.list();
			return "succ";
		}else{
			return "sign_in";
		}
	}
	public String manageVideo(){
		setUserBySession();
		if(user==null){
			return "sign_in";
		}
		count = 20;

		Long totalCount = null;
		if(user.getLevel()==MyConfig.LEVEL_ADMIN){
			totalCount = videoService.count();
			this.setPageInfo(totalCount);
			videos = videoService.list(page*count,count);
			
		}else{
			totalCount = videoService.countUser(user.getId());
			this.setPageInfo(totalCount);
			videos = videoService.listUser(page*count, count, user.getId());
		}
		return "succ";
	}
	
	
	public String manageComment(){
		setUserBySession();
		if(user==null){
			return "sign_in";
		}
		count = 20;
		start = page*count;
		Long totalCount = null;
		if(user.getLevel()==MyConfig.LEVEL_ADMIN){
			totalCount = commentService.count();
			this.setPageInfo(totalCount);
			comments = commentService.list(start, count);
			
		}else{
			totalCount = commentService.countUser(user.getId());
			this.setPageInfo(totalCount);
			comments = commentService.listUser(start, count, user.getId());
		}
		return "succ";
	}
	
	
	
	
	
	
	
	
	
	//��֤�Ƿ��¼
	public void setUserBySession(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User)session.getAttribute("user");
	}
	
	
	//������һ�η�ҳ����
	private void setPageInfo(Long total){
		prevpage = page-1;
		nextpage = page+1;
		pages = new ArrayList();
		//���ҳ��
		maxPage = (int) Math.ceil(total/20);
		//ҳ��
		int start = 0;
		int end = maxPage;
		if(maxPage>10){
			start = page-5<0?0:page-5;
			end = start+10;
		}
		for(int i=start;i<end&&i<maxPage;i++){pages.add(i);}
		//�����������ҳ��
		if(page>maxPage){
			page = maxPage-1;
			nextpage = page;
		}
		if(page<0){
			page=0;
			prevpage = 0;
		}
		maxPage-=1;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public VideoService getVideoService() {
		return videoService;
	}
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
	public CommentService getCommentService() {
		return commentService;
	}
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getPrevpage() {
		return prevpage;
	}
	public void setPrevpage(int prevpage) {
		this.prevpage = prevpage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getNextpage() {
		return nextpage;
	}
	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}
	public ArrayList getPages() {
		return pages;
	}
	public void setPages(ArrayList pages) {
		this.pages = pages;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
}
