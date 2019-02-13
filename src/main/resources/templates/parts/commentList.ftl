<#include "security.ftl">

<div>Comments(${commentCount}):</div>
<hr class="my-2">
<div class="card-columns">
    <#list comments.content as comment>
    <div class="card my-2">
        <div class="m-1">
        	<a class="col" href="/profile/${comment.commentAuthor.id}">${comment.commentAuthor.username}</a>
        </div>
		<div class="card-footer text-muted container">
			<div class="row ml-1">
		    	<div class="text-dark">${comment.text}</div><br>
		    	<div class="col">
		    	</div>
		    	<div class="col">
		    	</div>
		    	<div class="col">
		    		<#if comment.commentAuthor.id == currentUserId && !isCommentEdit>
		    			<a class="btn btn-primary btn-sm" href="${message.id}/edit/${comment.id}">edit</a>
		    		</#if>
		    	</div>
			</div>
		</div>
	</div>
    <#else>
    	No comments
    </#list>
</div>