package com.liferay.forms.extension;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Eric Chin
 */
public class MatchFunction implements DDMExpressionFunction.Function2<String, String, Boolean> {

	public static final String NAME = "match";

	public MatchFunction(DDMExpressionFunctionFactory originalDDMExpressionFunctionFactory) {
		_originalDDMExpressionFunctionFactory = originalDDMExpressionFunctionFactory;
	}

	@Override
	public Boolean apply(String value, String regex) {
		DDMExpressionFunction.Function2<String, String, Boolean> originalDDMExpressionFunction =
			(Function2<String, String, Boolean>) _originalDDMExpressionFunctionFactory.create();

		boolean matches = originalDDMExpressionFunction.apply(value, regex);

		if (matches) {
			return true;
		}

		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(value);

			int sbLength = (jsonArray.length() * 2) + 2;

			StringBundler sb = new StringBundler(sbLength);

			sb.append("\\[\"");

			for (int i = 0; i < jsonArray.length(); i++) {
				sb.append(regex);
				sb.append("\",\"");

				if (i == jsonArray.length() - 1) {
					sb.setIndex(sb.index() - 1);
				}
			}

			sb.append("\"]");

			return _matches(sb.toString(), value);
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

	private boolean _matches(String regex, String value) {
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(value);

		return matcher.matches();
	}

	private final DDMExpressionFunctionFactory _originalDDMExpressionFunctionFactory;

	private static final Log _log = LogFactoryUtil.getLog(MatchFunction.class);

}
