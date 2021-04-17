package com.liferay.forms.extension;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eric Chin
 */
@Component(
	immediate = true,
	property = "name=" + ArrayContainsFunction.NAME,
	service = DDMExpressionFunctionFactory.class
)
public class ArrayContainsFunctionFactory implements DDMExpressionFunctionFactory {

	@Override
	public DDMExpressionFunction create() {
		return _ARRAY_CONTAINS_FUNCTION;
	}

	private static final ArrayContainsFunction _ARRAY_CONTAINS_FUNCTION = new ArrayContainsFunction();

}
