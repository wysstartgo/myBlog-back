package com.ambition.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * <pre>
 *
 *
 * @title: ValidatorConfig
 * @description:
 * @company:
 * @author: wuys
 * @datetime: 2019-08-23 11:21
 * </pre>
 */
@Configuration
public class ValidatorConfig {

	@Bean
	public Validator validator(){
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
		return validatorFactory.getValidator();
	}
}
