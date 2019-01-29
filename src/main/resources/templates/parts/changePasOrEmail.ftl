<#macro changer isPasChange>
	<#if message??>
	    <div class="alert alert-success" role="alert">${message}</div>
	</#if>
	<form method="post" action="changeEmail">
		<#if isPasChange>
			<div class="form-group row">
		        <label class="col-sm-2 col-form-label">Username:</label>
		        	<div class="col-sm-6 form-inline">
						<input type="text" name="username" class="form-control ${(usernameError??)?string('is-invalid', '')}" placeholder="Entyour username" />
						<#if usernameError??>
		        			<div class="invalid-feedback">${usernameError}</div>
						</#if>
					</div>
			</div>
		</#if>
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
		<#if !isPasChange>
			<div class="form-group row">
		        <label class="col-sm-2 col-form-label">New email:</label>
		        	<div class="col-sm-6">
						<input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}" placeholder="new email" />
						<#if emailError??>
							<div class="invalid-feedback">${emailError}</div>
					 	</#if>
					</div>
			</div>
		</#if>
		<#if isPasChange>
			<div class="form-group row">
		        <label class="col-sm-2 col-form-label">New password:</label>
		        	<div class="col-sm-6">
						<input type="email" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="new password" />
					 	<#if passwordError??>
							<div class="invalid-feedback">${passwordError}</div>
					 	</#if>
					</div>
			</div>
			<div class="form-group row">
		        <label class="col-sm-2 col-form-label">Password cofirmation:</label>
		        	<div class="col-sm-6">
						<input type="email" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}" placeholder="Password cofirmation" />
					 	<#if password2Error??>
							<div class="invalid-feedback">${password2Error}</div>
					 	</#if>
					</div>
			</div>
		</#if>
		<button type="submit" name="button" class="btn btn-primary" value="change"><#if isPasChange>Change password<#else>Change Email</#if></button>
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
	</form>
</#macro>