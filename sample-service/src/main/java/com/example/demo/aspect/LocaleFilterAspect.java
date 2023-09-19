package com.example.demo.aspect;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.SampleEntity;

import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class LocaleFilterAspect {

	@PersistenceContext
	private EntityManager entityManager;

	@Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")
	void isRepository() {
	}

	@Around("execution(public * *(..)) && isRepository()")
	public Object repositoryMethods(ProceedingJoinPoint pjp) throws Throwable {
		String language = LocaleContextHolder.getLocale().getLanguage();
		log.info("setting language to {}", language);
		entityManager.unwrap(Session.class).enableFilter(SampleEntity.FILTER_NAME).setParameter(SampleEntity.PARAM_NAME, language);
		return pjp.proceed();
	}
}