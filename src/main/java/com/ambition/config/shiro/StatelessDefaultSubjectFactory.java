package com.ambition.config.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * <pre>
 *
 *
 * @title: StatelessDefaultSubjectFactory
 * @description:
 * @author: wuys
 * @datetime: 2019-11-27 9:56
 * </pre>
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
	@Override
	public Subject createSubject(SubjectContext context) {
		//不创建session
		context.setSessionCreationEnabled(false);
		return super.createSubject(context);
	}
}
