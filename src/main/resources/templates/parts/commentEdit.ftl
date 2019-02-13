<form method="post">
	<div class="form-group mb-3">
		<div class="form-group">
			<input class="form-control ${(textError??)?string('is-invalid', '')}" type="text" name="text" placeholder="Enter your comment" value="${(comment.text)!''}"/>
			<#if textError??>
				<div class="invalid-feedback">${textError}</div>
			</#if>
		</div>
	</div>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	<button class="btn btn-primary" type="submit" value="edit" name="button">
		<#if isCommentEdit>
			edit
		<#else>
			add
		</#if>
	<#if isCommentEdit>
		<button type="button" class="btn btn-primary ml-2" data-toggle="modal" data-target="#exampleModal">delete</button>
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Delete comment</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">Are you sure you want to delete this comment?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
						<button type="submit" name="button" value="delete" class="btn btn-primary">delete</button>
					</div>
				</div>
			</div>
		</div>
	</#if>
</form>