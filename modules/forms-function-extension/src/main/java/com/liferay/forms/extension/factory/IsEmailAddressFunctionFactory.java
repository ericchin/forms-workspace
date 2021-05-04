package com.liferay.forms.extension.factory;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.forms.extension.IsEmailAddressFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Chin
 */
@Component(
	immediate = true,
	property = {
		"name=" + IsEmailAddressFunction.NAME,
		"service.ranking:Integer=" + Integer.MAX_VALUE
	},
	service = DDMExpressionFunctionFactory.class
)
public class IsEmailAddressFunctionFactory implements DDMExpressionFunctionFactory {

	@Override
	public DDMExpressionFunction create() {
		return new IsEmailAddressFunction(_originalIsEmailAddressFunctionFactory);
	}

	@Reference(
		target = "(component.name=" +
			"com.liferay.dynamic.data.mapping.form.evaluator.internal.function.factory.IsEmailAddressFunctionFactory)"
	)
	private DDMExpressionFunctionFactory _originalIsEmailAddressFunctionFactory;

}
