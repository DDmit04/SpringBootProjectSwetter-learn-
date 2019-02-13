package com.example.sweater.domain.dto;

import com.example.sweater.domain.Comment;
import com.example.sweater.domain.User;

public class CommentDto {

	private Long id;
    private String text;
    private Long pluses;
    private boolean mePlused;
    private User commentAuthor;
    
	public CommentDto(Comment comment, Long pluses, boolean mePluses) {
		this.id = comment.getId();
		this.text = comment.getText();
		this.pluses = pluses;
		this.mePlused = mePluses;
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
	public Long getPluses() {
		return pluses;
	}
	public boolean isMePlused() {
		return mePlused;
	}
}
