<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>

<#include "parts/messageList.ftl" />
<@p.pager url messagesPage />

</@c.page>