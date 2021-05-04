package com.liferay.forms.extension;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.stream.Stream;

/**
 * @author Eric Chin
 */
public class IsEmailAddressFunction implements DDMExpressionFunction.Function1<String, Boolean> {

	public static final String NAME = "isEmailAddress";

	public IsEmailAddressFunction(DDMExpressionFunctionFactory originalDDMExpressionFunctionFactory) {
		_originalDDMExpressionFunctionFactory = originalDDMExpressionFunctionFactory;
	}

	@Override
	public Boolean apply(String parameter) {
		DDMExpressionFunction.Function1<String, Boolean> originalDDMExpressionFunction =
			(Function1<String, Boolean>) _originalDDMExpressionFunctionFactory.create();

		boolean isEmailAddress = originalDDMExpressionFunction.apply(parameter);

		if (isEmailAddress) {
			return true;
		}

		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(parameter);

			String[] values = JSONUtil.toStringArray(jsonArray);

			return Stream.of(
				values
			).map(
				String::trim
			).allMatch(
				Validator::isEmailAddress
			);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone.getMessage());
			}

			return false;
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

	private final DDMExpressionFunctionFactory _originalDDMExpressionFunctionFactory;

	private static final Log _log = LogFactoryUtil.getLog(IsEmailAddressFunction.class);

}
