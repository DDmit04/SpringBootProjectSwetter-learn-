<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<#include "parts/security.ftl">

<@c.page>

<#include "parts/CommentList.ftl" />

<hr class="my-3">
<#if !gest>
	<#include "parts/addComment.ftl">
</#if>

</@c.page>
