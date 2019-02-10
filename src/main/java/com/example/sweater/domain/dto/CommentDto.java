package com.example.sweater.domain.dto;

import com.example.sweater.domain.Comment;
import com.example.sweater.domain.User;

public class CommentDto {

	private Long id;
    private String text;
    private User commentAuthor;
    
	public CommentDto(Comment comment) {
		this.id = comment.getId();
		this.text = comment.getText();
		this.commentAuthor = comment.getCommentAuthor();
	}
	public Long getId() {
		return id;
	}
	public String getText() {
		return text;
	}
	public User getCommentAuthor() {
		return commentAuthor;
	}
}
