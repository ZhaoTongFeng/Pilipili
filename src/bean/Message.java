package bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//H
@Entity
@Table(name="message")

//Spring
@Component("Message")
public class Message {
	public Message(){
		
	}
	public Message(String content,User fromUser,User toUser){
		this.content = content;
		this.fromUser = fromUser;
		this.toUser = toUser;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable=false)  
	private Integer id;
    
	@Column(nullable=false)
    private String content;	
	
	
    @Column
    private Date time_create = new Date();
    
    @ManyToOne
    @JoinColumn(name="message_user_from_id")
	@Autowired
    private User fromUser;
    
    @ManyToOne
    @JoinColumn(name="message_user_to_id")
	@Autowired
    private User toUser;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime_create() {
		return time_create;
	}
	public void setTime_create(Date time_create) {
		this.time_create = time_create;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
    
    
}
