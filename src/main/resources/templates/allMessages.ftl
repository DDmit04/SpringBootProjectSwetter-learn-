<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<#include "parts/security.ftl">

<@c.page>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/allMessages" class="form-inline">
            <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search by tag">
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>
</div>

<#include "parts/messageEdit.ftl" />

<#include "parts/messageList.ftl" />
<@p.pager url messagesPage />

</@c.page>
