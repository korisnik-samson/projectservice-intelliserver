package com.samson.projectserviceintelliserver.aspects;
import com.samson.projectserviceintelliserver.exceptions.UnauthourizedException;
import com.samson.projectserviceintelliserver.annotations.PreAuthorize;
import com.samson.projectserviceintelliserver.services.DependencyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
@Component
public class PreAuthourizeAspect {
    
    private final DependencyService dependencyService;
    private final HttpServletRequest request;
    private final Pattern hasRolePattern = Pattern.compile("hasRole\\((.*)\\)");
    
    @Autowired
    public PreAuthourizeAspect(DependencyService dependencyService, HttpServletRequest request) {
        this.dependencyService = dependencyService;
        this.request = request;
    }
    
    @Around("@annotation(com.samson.projectserviceintelliserver.annotations.PreAuthorize)")
    public Object preAuthourize(@NonNull ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        PreAuthorize preAuthourizationAnnotation = method.getAnnotation(PreAuthorize.class);
        
        // role extraction from the annotation
        String expression = preAuthourizationAnnotation.role();
        Matcher matcher = hasRolePattern.matcher(expression);
        
        if (matcher.find()) {
            String role = matcher.group(1);
            String username = request.getHeader("username");
            String password = request.getHeader("password");
            
            // logic for verification of the user as an admin from DependencyService
            if (this.dependencyService.verifyRole(username, password, role)) return joinPoint.proceed();
            
            else throw new UnauthourizedException("User does not have the required role: " + role);
            
        } else throw new IllegalArgumentException("Invalid PreAuthorize expression: " + expression);
        
    }
    
}
