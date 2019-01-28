<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page> 
<#if message??>
	<div class="alert alert-success" role="alert">${message}</div>
</#if>
<form method="post">
	<div class="form-group row">
		<label class="col-sm-2 col-form-label">Key:</label>
		<div class="col-sm-6 form-inline">
			<input type="text" name="key" class="form-control ${(keyError??)?string('is-invalid', '')}" placeholder="key" />
			<#if keyError??>
	        	<div class="invalid-feedback">${keyError}</div>
			</#if>
		</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-2 col-form-label">New password:</label>
		<div class="col-sm-6">
			<input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="new password" />
			<#if passwordError??>
				<div class="invalid-feedback">${passwordError}</div>
			</#if>
		</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-2 col-form-label">Password cofirmation:</label>
		<div class="col-sm-6">
			<input type="password" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}" placeholder="Password cofirmation" />
			<#if password2Error??>
				<div class="invalid-feedback">${password2Error}</div>
			</#if>
		</div>
	</div>
	<button type="submit" name="button" class="btn btn-primary" value="change">Change password</button>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>

</@c.page>