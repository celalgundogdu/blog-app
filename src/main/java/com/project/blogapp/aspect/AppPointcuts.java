package com.project.blogapp.aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppPointcuts {

    @Pointcut("execution(* com.project.blogapp.service.*.*(..))")
    public void servicePointcut() {}

    @Pointcut("execution(* com.project.blogapp.service.PostService.searchPosts*(*))")
    public void searchPostsPointcut() {}
}
