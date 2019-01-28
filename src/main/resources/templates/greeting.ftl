<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
<#if message??>
    <div class="alert alert-${messageType}" role="alert">
        ${message}
    </div>
</#if>
<h5>Hello, ${name!Gest}</h5>
<div>This is a simple clone off Twitter</div>
</@c.page>
