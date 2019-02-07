<#include "security.ftl">

<div class="card-columns">
    <#list comments as comment>
    <div class="card my-3">
        <div class="m-2">
            <span>${comment.text}</span><br>
        </div>
		<div class="card-footer text-muted container">
			<div class="row">
				<a class="col align-self-center" href="/profile/${comment.commentAuthor.id}">${comment.commentAauthor}</a>
			</div>
		</div>
	</div>
    <#else>
    	No comments
    </#list>
</div>