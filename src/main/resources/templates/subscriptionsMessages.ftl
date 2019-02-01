<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>

<@p.pager url pages />
<#include "parts/messageList.ftl" />
<@p.pager url pages />

</@c.page>