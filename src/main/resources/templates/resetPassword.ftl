<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page> 
<#if message??>
	<div class="alert alert-success" role="alert">${message}</div>
</#if>
<form method="post" action="passwordRecover">
	<div class="form-group row">
		<label class="col-sm-2 col-form-label">Username:</label>
		<div class="col-sm-6 form-inline">
			<input type="text" name="username" class="form-control ${(usernameError??)?string('is-invalid', '')}" placeholder="Enter your username" />
			<button type="submit" name="button" class="btn btn-primary ml-2">Sand key</button>
			<#if usernameError??>
		    	<div class="invalid-feedback">${usernameError}</div>
			</#if>
		</div>
	</div>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>

</@c.page>