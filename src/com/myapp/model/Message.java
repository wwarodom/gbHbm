package com.myapp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)	
	private User user;
		
	private String message;
	
	// @JoinTable is used to decide the relationship Owner
	// Thus, we use messages.setTags() (and cannot use tags.setMessages()
	@ManyToMany(fetch = FetchType.EAGER ,  cascade = CascadeType.ALL)
	@JoinTable(name = "message_tag", 
		joinColumns = 		 { @JoinColumn(name = "message_id")}, 
		inverseJoinColumns = { @JoinColumn(name = "tag_id") })		
	private List<Tag> tags;
		
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Message() {}
	
	public Message(User user,String message){
		this.user = user;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + "]";
	}
  
	
}
