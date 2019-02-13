package com.example.sweater.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.example.sweater.domain.utill.MessageHelper;

@Entity
public class Message {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Pleace fill the message")
    @Length(max = 2048, message = "Message too long(more then 2KB)")
    private String text;
    @Length(max = 255, message = "Tag too long(more then 255)")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    
    @ManyToMany
    @JoinTable(
    		name = "message_likes",
    		joinColumns = {@JoinColumn(name = "message_id")},
    		inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<User>();
    
    @OneToMany(mappedBy = "commentedMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<Comment>();
    
    private String filename;

    public Message() {
    }
    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }
    public String getAuthorName() {
    	return MessageHelper.getAuthorName(author);
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
	public Set<User> getLikes() {
		return likes;
	}
	public void setLikes(Set<User> likes) {
		this.likes = likes;
	}
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
}
