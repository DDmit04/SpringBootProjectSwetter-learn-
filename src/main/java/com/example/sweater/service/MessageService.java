package com.example.sweater.service;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repos.MessageRepo;

@Service
public class MessageService {

	@Autowired
	private MessageRepo messageRepo;

	public Page<MessageDto> messageList(Pageable pageable, String filter, User user) {
		if (filter != null && !filter.isEmpty()) {
			return messageRepo.findByTag(filter, pageable, user);
		} else {
			return messageRepo.findAll(pageable, user);
		}
	}
	
	public Page<MessageDto> messageSub(Pageable pageable, User user) {
		Page<MessageDto> page = messageRepo.findAll(pageable, user);
		Iterator<MessageDto> iterator = page.iterator();
		while (iterator.hasNext()) {
			MessageDto message = iterator.next();
			if (!user.getSubscriptions().contains(message.getAuthor())) {
				iterator.remove();
			}
		}
		return page;
	}
	
	public Page<MessageDto> messageListForUser(Pageable pageable, User currentUser, User author) {
        return messageRepo.findByAuthor(pageable, author, currentUser);
    }
	
}
