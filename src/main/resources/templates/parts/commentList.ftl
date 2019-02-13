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
			    	<#if !gest> 
						<a class="col align-self-center <#if comment.mePlused>text-success</#if>" href="/comments/${comment.id}/plus">
							<#if comment.mePlused> 
								<i class="fas fa-plus text-success"></i> 
							<#else> 
								<i class="fas fa-plus"></i>
							</#if> 
							${comment.pluses}
						</a>
					<#else>
						<div class="col align-self-center">
							<i class="far fa-plus"></i>
							${comment.pluses}
						</div>
					</#if>
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