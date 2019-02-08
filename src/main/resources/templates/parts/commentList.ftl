<#include "security.ftl">

<div class="card my-3">
        <#if message.filename??>
        <img src="/img/${message.filename}" class="card-img-top">
        </#if>
        <div class="m-2">
            <span>${message.text}</span><br>
            <i><a href="/allMessages?filter=${message.tag}">#${message.tag}</a></i>
        </div>
		<div class="card-footer text-muted container">
			<div class="row">
				<a class="col align-self-center" href="comments/${message.id}">comment</a>
				<a class="col align-self-center" href="/profile/${message.author.id}">${message.authorName}</a>
				<#if !gest> 
					<a class="col align-self-center" href="/messages/${message.id}/like">
						<#if message.meLiked> 
							<i class="fas fa-heart"></i> 
						<#else> 
							<i class="far fa-heart"></i>
						</#if> 
						${message.likes}
					</a>
				<#else>
					<div class="col align-self-center">
						<i class="far fa-heart"></i>
						${message.likes}
					</div>
				</#if>
				<#if message.author.id== currentUserId> 
					<a class="col btn btn-primary" href="/profile/${message.author.id}?message=${message.id}">
						Edit 
					</a> 
				</#if>
			</div>
		</div>
	</div>

<div class="card-columns">
    <#list comments as comment>
    <div class="card my-3">
        <div class="m-2">
            <span>${comment.text}</span><br>
        </div>
		<div class="card-footer text-muted container">
			<div class="row">
			</div>
		</div>
	</div>
    <#else>
    	No comments
    </#list>
</div>