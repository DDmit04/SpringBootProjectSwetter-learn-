<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<#include "parts/security.ftl">

<@c.page>

<#include "parts/messageList.ftl">

<#if redirectMessage?? && redirectMessage != "">
    <div class="alert alert-${redirectMessageType}" role="alert">${redirectMessage}</div>
</#if>

<#include "parts/CommentList.ftl" />
	<#if !isCommentEdit>
</#if>

<hr class="my-3">

<#if !gest>
	<#include "parts/CommentEdit.ftl">
</#if>

</@c.page>
