<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
    isMessagesPage = springMacroRequestContext.requestUri?contains("/allMessages")
    isRegistrationPage = springMacroRequestContext.requestUri?contains("/registration")
    isComment = springMacroRequestContext.requestUri?contains("/comments")
>

<#if known>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isAdmin = user.isAdmin()
        isActive = user.isActive()
        currentUserId = user.getId()
        gest = false
        mail = user.getEmail()
    >
<#else>
    <#assign
		
        mail = "unknovn@what"
        name = "Gest"
        isAdmin = false
        isActive = false
        currentUserId = -1
        isCurrentUser = false
        isSubscriber = false
        gest = true
    >
</#if>
