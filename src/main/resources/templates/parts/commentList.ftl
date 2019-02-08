<#include "security.ftl">

<#include "messageList.ftl">

<div class="card-columns">
    <#list comments as comment>
    <div class="card my-3">
        <div class="m-2">
            <span>${comment.text}</span><br>
        </div>
		<div class="card-footer text-muted container">
			<div class="row">
			</div>
		</div>
	</div>
    <#else>
    	No comments
    </#list>
</div>