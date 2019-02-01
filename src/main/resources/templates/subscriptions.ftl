<#import "parts/common.ftl" as c>

<@c.page>

<h3>${userChannel.username}</h3>
<div>${type}:</div>
<ul class="list-group">
	<#list users as user>
  		<li class="list-group-item mt-2" >
  			<a href="/profile/${user.id}">${user.getUsername()}</a>
  		</li>
  	<#else>
    	<h5 class="mt-2">no ${type}</h5>
    </#list>	
</ul>

</@c.page>