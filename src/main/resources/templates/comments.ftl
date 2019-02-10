<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<#include "parts/security.ftl">

<@c.page>

<#include "parts/messageList.ftl">

<#include "parts/CommentList.ftl" />
<#if !isCommentEdit>
	<@p.pager url messagesPage />
</#if>

<hr class="my-3">

<#if !gest>
	<#include "parts/CommentEdit.ftl">
</#if>

</@c.page>
