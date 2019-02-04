<#include "security.ftl">

<#if activationCodeError??>
	<div class="alert alert-danger" role="alert">
		<a class="alert-link" href="user/accountSettings">${activationCodeError}</a>
	</div>
</#if>
<#if (message?? || isMessagesPage) && !gest>
<a class="btn btn-primary mb-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
	<#if isMessagesPage>
    	New message
	<#else>
    	Message editor
	</#if>
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
				<input type="text" class="form-control ${(textError??)?string('is-invalid', '')}" value="<#if message??>${message.text}</#if>" name="text" placeholder="Enter message" />
				<#if textError??>
					<div class="invalid-feedback">${textError}</div>
				</#if>
			</div>
            <div class="form-group">
                <input type="text" class="form-control ${(tagError??)?string('is-invalid', '')}"  value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Tag">
                <#if tagError??>
					<div class="invalid-feedback">${tagError}</div>
				</#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="form-group">
                <button type="submit" name="button" value="edit" class="btn btn-primary">
					<#if isMessagesPage>
    					add
					<#else>
    					edit
					</#if>
				</button>
				<#if !isMessagesPage>
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">delete</button>
					<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">Delete message</h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">Are you sure you want to delete this message?</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
								<button type="submit" name="button" value="delete" class="btn btn-primary">delete</button>
							</div>
						</div>
					</div>
				</div>
				</#if>
			</div>
        </form>
    </div>
</div>
</#if>
