package guru.springframework.di.services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class LifeCycleDemoBean implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware {

	public LifeCycleDemoBean() {
		System.out.println("## I'm in the LifeCycleBean Constructor");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("## ApplicationContext has been set");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("## BeanFactory has been set");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("## My Bean name is " + name);
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("## The LifeCycleBean has been terminated");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("## The LifeCycleBean has its properties set");
	}
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("## The PostConstruct annotated method has been called");
	}
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("## The PreDestroy annotated method has been called");
	}

	public void beforeInit(){
        System.out.println("## - Before Init - Called by Bean Post Processor");
    }

    public void afterInit(){
        System.out.println("## - After init called by Bean Post Processor");
    }
}
