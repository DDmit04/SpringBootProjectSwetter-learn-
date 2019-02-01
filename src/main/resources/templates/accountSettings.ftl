<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
<h5>${name}</h5>
<#if message??>
    <div class="alert alert-success" role="alert">${message}</div>
</#if>
<form method="post">
	<div class="form-group row">
        <label class="col-sm-2 col-form-label">Your password:</label>
        <div class="col-sm-6">
            <input type="password" name="currentPassword" class="form-control ${(currentPasswordError??)?string('is-invalid', '')}" placeholder="Your password" />
            <#if currentPasswordError??>
        		<div class="invalid-feedback">
        			${currentPasswordError}
    			</div>
			</#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Password" />
            <#if passwordError??>
        		<div class="invalid-feedback">
        			${passwordError}
    			</div>
			</#if>
        </div>
    </div>
    <div class="form-group row">
    	<label class="col-sm-2 col-form-label">Password cofirmation:</label>
        <div class="col-sm-6">
            <input type="password" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}" placeholder="Password confirmation" />
            <#if password2Error??>
        		<div class="invalid-feedback">
        			${password2Error}
    			</div>
			</#if>
        </div>
    </div>
    <button class="btn btn-primary" type="submit">Save password</button>  
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="hidden" name="username" value="username" />
    <input type="hidden" name="email" value="some@some" />
</form>
<hr class="my-4">
<form method="get">
	<div class="form-group row">
    	<label for="staticEmail" class="col-sm-2 col-form-label">Your email:</label>
    	<div class="col-sm-10">
        	<input type="text" readonly class="form-control-plaintext" id="staticEmail" value="${user.email}">
    	</div>
    </div>
	<a class="btn btn-primary" href="/user/changeEmail" role="button">change email</a>
</form>
</@c.page>
