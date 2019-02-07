package com.example.sweater.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
public class Comment {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Pleace fill the message")
    @Length(max = 2048, message = "Message too long(more then 2KB)")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    private User commentAuthor;

    public Comment() {
    }
	public Comment(Long id, String text, User commentAuthor) {
		super();
		this.id = id;
		this.text = text;
		this.commentAuthor = commentAuthor;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getCommentAuthor() {
		return commentAuthor;
	}
	public void setCommentAuthor(User commentAuthor) {
		this.commentAuthor = commentAuthor;
	}
    
}
