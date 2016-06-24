package com.myapp.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="tag")
public class Tag {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
	private List<Message> messages ;

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Tag() {}
	
	public Tag(String name) {
		this.name = name;
	}
	
	
	public List<Message> getMessage() {
		return messages;
	}

	public void setMessage(List<Message> messages) {
		this.messages = messages;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + "]";
	}
	
}
