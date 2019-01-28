<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
<#if message??>
    <div class="alert alert-success" role="alert">${message}</div>
</#if>
<form method="post" action="changeEmail">
	<div class="form-group row">
        <label class="col-sm-2 col-form-label">Key:</label>
        	<div class="col-sm-6 form-inline">
				<input type="text" name="key" class="form-control ${(keyError??)?string('is-invalid', '')}" placeholder="key" />
				<button type="submit" name="button" class="btn btn-primary ml-2" value="sendKey">Send key</button>	
				<#if keyError??>
        			<div class="invalid-feedback">${keyError}</div>
				</#if>
			</div>
	</div>
	<div class="form-group row">
        <label class="col-sm-2 col-form-label">new Email:</label>
        	<div class="col-sm-6">
				<input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}" placeholder="new email" />
				<#if emailError??>
					<div class="invalid-feedback">${emailError}</div>
			 	</#if>
			</div>
	</div>
	<button type="submit" name="button" class="btn btn-primary" value="change">change email</button>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>
</@c.page>