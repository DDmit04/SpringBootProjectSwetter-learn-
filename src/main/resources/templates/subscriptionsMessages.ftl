<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>

<@p.pager url messagesPage />
<#include "parts/messageList.ftl" />
<@p.pager url messagesPage />

</@c.page>