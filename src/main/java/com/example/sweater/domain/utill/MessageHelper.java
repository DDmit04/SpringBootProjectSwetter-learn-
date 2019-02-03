package com.example.sweater.domain.utill;

import com.example.sweater.domain.User;

public abstract class MessageHelper {

	public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
	}
	
}
