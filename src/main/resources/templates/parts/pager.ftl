<#macro pager url messagesPage>

<#if messagesPage.getTotalPages() gt 7>
	<#assign
		totalPages = pages.getTotalPages()
		pageNumber = pages.getNumber() + 1
		head = (pageNumber > 4)?then([1, -1] , [1, 2, 3])
		tale = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages -2, totalPages - 1, totalPages])
		bodyBefore = (pageNumber > 4 && pageNumber > totalPages - 1)?then([pageNumber -2, pageNumber - 1], [])
		bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2], [])
		body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tale 
	>
<#else>
	<#assign
		body = 1..messagesPage.getTotalPages()
	>
</#if>
<div class="container mt-3">
	<div class="row">
		<ul class="pagination col justify-content-center">
			<li class="page-item disabled">
				<a class="page-link" href="#" tabindex="-1">Pages</a>
			</li>
			<#list 1..messagesPage.getTotalPages() as p> 
			<#if p - 1 == messagesPage.getNumber()>
				<li class="page-item active">
					<a class="page-link" href="#" tabindex="-1">${p}</a>
				</li>
			<#elseif p == -1>
				<li class="page-item disabled">
					<a class="page-link" href="#" tabindex="-1">...</a>
				</li>
			<#else>
				<li class="page-item">
					<a class="page-link" href="${url}?page=${p - 1}&size=${messagesPage.getSize()}" tabindex="-1">${p}</a>
				</li>
			</#if>
			</#list>
		</ul>
		
		<ul class="pagination col justify-content-center">
			<li class="page-item disabled">
				<a class="page-link" href="#" tabindex="-1">Elemens on pages</a>
			</li>
			<#list [5, 10, 25, 50] as c> 
			<#if c == messagesPage.getSize()>
				<li class="page-item active">
					<a class="page-link" href="#" tabindex="-1">${c}</a>
				</li>
			<#else>
				<li class="page-item">
					<a class="page-link" href="${url}?page=${messagesPage.getNumber()}&size=${c}" tabindex="-1">${c}</a>
				</li>
			</#if>
			</#list>
		</ul>
	</div>
</div>

</#macro>
