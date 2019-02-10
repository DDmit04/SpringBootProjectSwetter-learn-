<#include "security.ftl">
<#include "messageList.ftl">

<div>Comments(${commentCount}):</div>
<hr class="my-2">
<div class="card-columns">
    <#list comments.content as comment>
    <div class="card my-2">
        <div class="m-1">
        	<a class="col align-self-center" href="/profile/${comment.commentAuthor.id}">${comment.commentAuthor.username}</a>
        </div>
		<div class="card-footer text-muted container">
			<div class="row">
		    	<span> ${comment.text}</span><br>
			</div>
		</div>
	</div>
    <#else>
    	No comments
    </#list>
</div>