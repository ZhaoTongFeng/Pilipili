package bean;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import util.GoodAPI;


@Entity
@Table(name="comment")
@Component("Comment")
public class Comment implements GoodAPI{
	public Comment(){
		
	}
	public Comment(String content,User user){
		this.content = content;
		this.user = user;
	}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable=false)  
	private Integer id;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
	private Date  time_create;
    
    @Column(nullable=false)
	private String content;
    
    //点赞
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_good = 0;
    //点赞人的ID
    @Column
    private String people_good="0";
    
    //回复数量
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_reply = 0;
    
    @Column(columnDefinition="INT default 0",nullable=false)
	private Integer comment_comment_id = 0;

    @ManyToOne
    @JoinColumn(name="comment_user_id")
	@Autowired
    private User user;
    
    @ManyToOne
    @JoinColumn(name="comment_video_id")
	@Autowired
    private Video video;

    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getTime_create() {
		return time_create;
	}
	public void setTime_create(Date time_create) {
		this.time_create = time_create;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getComment_comment_id() {
		return comment_comment_id;
	}
	public void setComment_comment_id(Integer comment_comment_id) {
		this.comment_comment_id = comment_comment_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public Integer getNum_good() {
		return num_good;
	}
	public void setNum_good(Integer num_good) {
		this.num_good = num_good;
	}
	public String getPeople_good() {
		return people_good;
	}
	public void setPeople_good(String people_good) {
		this.people_good = people_good;
	}
	public Integer getNum_reply() {
		return num_reply;
	}
	public void setNum_reply(Integer num_reply) {
		this.num_reply = num_reply;
	}
	
}
