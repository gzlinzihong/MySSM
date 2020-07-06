package edu.gdpu.myssm.springmvc.core;

import edu.gdpu.myssm.spring.annotation.AutoWired;
import edu.gdpu.myssm.spring.annotation.Component;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.*;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月06日 13:06:17
 */
@Component
public class InterceptorRegistry {

    private ServletContext context;

    private List<Registry> interceptors = new ArrayList<>();

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public Registry addInterceptor(Interceptor interceptor){
        Registry registry = new Registry(interceptor);
        interceptors.add(registry);
        return registry;
    }


    protected void doRegister(){
        int i = 0;
        Filter filter ;
        FilterRegistration.Dynamic dynamic;
        for(Registry registry:interceptors){
            filter = InterceptorFactory.getInterceptor(registry.getInterceptor(),registry.getExclude());
            dynamic = context.addFilter("interceptor"+i, filter);
            i++;
            dynamic.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,registry.getInclude());
        }
    }

    public class Registry{
        private String[] include;
        private String[] exclude;
        private Interceptor interceptor;

        public Registry(Interceptor interceptor) {
            this.interceptor = interceptor;
        }

        public Registry addPath(String... include){
            this.include = include;
            return this;
        }

        public void addExclude(String... exclude){
            this.exclude = exclude;
        }

        protected String[] getInclude() {
            return include;
        }

        protected String[] getExclude() {
            return exclude;
        }

        protected Interceptor getInterceptor() {
            return interceptor;
        }
    }
}
