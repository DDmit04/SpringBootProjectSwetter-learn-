<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<#include "parts/security.ftl">

<@c.page>

<#include "parts/CommentList.ftl" />

<#if !gest>
	<#include "parts/addComment.ftl">
</#if>

</@c.page>
