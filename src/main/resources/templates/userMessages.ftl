<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>

<#include "parts/security.ftl">

<#if redirectMessage?? && redirectMessage != "">
    <div class="alert alert-${redirectMessageType}" role="alert">${redirectMessage}</div>
</#if>
<h3> ${userChannel.username}
	<#if !gest>
		<#if !isCurrentUser> 
			<#if isSubscriber> 
				<a class="btn btn-info" href="/user/unsubscribe/${userChannel.id}">Unsubscribe</a> 
			<#else> 
				<a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Subscribe</a>
			</#if>
		</#if>
	</#if>
	<a class="btn btn-info" href="/user/subscribers/${userChannel.id}/list">Subscribers list
	<span class="badge badge-light">${subscribersCount}</span>
	</a>
</h3>
<a href="/user/subscriptions/${userChannel.id}/list">Watch <#if isCurrentUser>my <#else>${userChannel.username}'s </#if>subscriptions (${subscriptionsCount})</a>
<#if isCurrentUser> 
	<div class="mt-2">
		<#include "parts/messageEdit.ftl" />
	</div>
</#if> 

<@p.pager url pages />
<#include "parts/messageList.ftl" />
<@p.pager url pages />

</@c.page>