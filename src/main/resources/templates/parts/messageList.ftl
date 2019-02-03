<#include "security.ftl">

<div class="card-columns">
    <#list pages.content as message>
    <div class="card my-3">
        <#if message.filename??>
        <img src="/img/${message.filename}" class="card-img-top">
        </#if>
        <div class="m-2">
            <span>${message.text}</span><br>
            <i><a href="/main?filter=${message.tag}">#${message.tag}</a></i>
        </div>
		<div class="card-footer text-muted container">
			<div class="row">
				<a class="col align-self-center" href="/profile/${message.author.id}">${message.authorName}</a> 
				<a class="col align-self-center" href="/messages/${message.id}/like">
					<#if message.meLiked> 
						<i class="fas fa-heart"></i> 
					<#else>
						<i class="far fa-heart"></i>
					</#if> 
					${message.likes}
				</a>
				<#if message.author.id== currentUserId> 
					<a class="col btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">
						Edit 
					</a> 
				</#if>
			</div>
		</div>
	</div>
    <#else>
    	No message
    </#list>
</div>