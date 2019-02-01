<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        isMainPage = springMacroRequestContext.requestUri?contains("/main")
		isRegistrationPage = springMacroRequestContext.requestUri?contains("/registration")
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isAdmin = user.isAdmin()
        isActive = user.isActive()
        currentUserId = user.getId()
    >
<#else>
    <#assign
        isRegistrationPage = springMacroRequestContext.requestUri?contains("/registration")
        mail = "unknovn@what"
        name = "Gest"
        isAdmin = false
        isActive = false
        currentUserId = -1
    >
</#if>
