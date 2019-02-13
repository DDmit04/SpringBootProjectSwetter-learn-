create table comment_pluses (
		user_id bigint not null references usr,
		comment_id bigint not null references comment,
		primary key(user_id, comment_id) 
)