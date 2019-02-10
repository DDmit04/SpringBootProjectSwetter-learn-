package com.example.sweater.repos;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import com.example.sweater.domain.Comment;
import com.example.sweater.domain.Message;
import com.example.sweater.domain.dto.CommentDto;

public interface CommentRepo extends CrudRepository<Comment, Long> {

	@Query("select new com.example.sweater.domain.dto.CommentDto(" +
		   "  c " +
		   ") " +
		   "from Comment c " +
		   "where c.commentedMessage = :commentedMessage " + 
		   "group by c")
	Page<CommentDto> findBycommentedMessage(Pageable pageable, @Param("commentedMessage") Message commentedMessage);
	
}