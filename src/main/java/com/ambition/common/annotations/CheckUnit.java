//package com.ambition.common.annotations;
//
//import com.ambition.common.validator.MedicineValidator;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.*;
//
///**
// * <pre>
// *
// *
// * @title: CheckUnit
// * @description: 校验参数的正负
// * @company:
// * @author: wuys
// * @datetime: 2019-08-23 11:09
// * </pre>
// */
//@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER,ElementType.ANNOTATION_TYPE,ElementType.TYPE_USE})
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = MedicineValidator.class)
//@Documented
//@Repeatable(CheckUnit.List.class)
//public @interface CheckUnit {
//
//	String wrapUnitTransform() default "wrapUnitTransform";
//
//	String minUnitTransform() default "minUnitTransform";
//
//	String message() default "the min unit value max then the wrap unit value!";
//
//	Class<?>[] groups() default {};
//
//	Class<? extends Payload>[] payload() default {};
//
//	@Target({ElementType.TYPE,ElementType.FIELD,ElementType.PARAMETER,ElementType.ANNOTATION_TYPE})
//	@Retention(RetentionPolicy.RUNTIME)
//	@Documented
//	@interface List{
//		CheckUnit[] value();
//	}
//}
