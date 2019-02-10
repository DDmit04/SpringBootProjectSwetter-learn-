<form method="post">
	<div class="form-group mb-3">
		<div class="form-group">
			<input class="form-control" type="text" name="text" placeholder="Enter your comment"/>
		</div>
	</div>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	<button class="btn btn-primary" type="submit">add</button>
</form>