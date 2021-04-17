package com.liferay.forms.extension;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Eric Chin
 */
public class ArrayContainsFunction implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

	@Override
	public Boolean apply(Object object1, Object object2) {
		if (object1 == null) {
			return false;
		}

		if ((object1 instanceof String) && (object2 instanceof String)) {
			return apply((String)object1, (String)object2);
		}

		return false;
	}

	public static final String NAME = "arrayContains";

	@Override
	public String getName() {
		return NAME;
	}

	protected Boolean apply(String string1, String string2) {
		if (Validator.isNull(string1) || Validator.isNull(string2)) {
			return false;
		}

		String[] stringArray = StringUtil.split(string1);

		return ArrayUtil.contains(stringArray, string2);
	}

}
