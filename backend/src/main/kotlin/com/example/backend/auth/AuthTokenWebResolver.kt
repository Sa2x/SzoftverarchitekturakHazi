package com.example.backend.auth

import com.example.backend.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebArgumentResolver.UNRESOLVED
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

class AuthTokenWebResolver : HandlerMethodArgumentResolver {

    @Autowired
    lateinit var authTokenHandler: AuthTokenHandler

    // to register Auth annotation
    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return methodParameter.getParameterAnnotation(Auth::class.java) != null
    }

    override fun resolveArgument(parameter: MethodParameter,
                                 mavContainer: ModelAndViewContainer?,
                                 webRequest: NativeWebRequest,
                                 binderFactory: WebDataBinderFactory?): Any? {
        if (parameter.parameterType == User::class.java) {
          // looking for the auth token in the headers
            var authToken = webRequest.getHeader("Bearer")

          // looking for the auth token in the cookies
            if (authToken == null) {
                throw AuthException("No token found")
            }

            return authTokenHandler.getUserFromToken(authToken)
        }

        return UNRESOLVED
    }
}