package com.hegongshan.easy.orm.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static boolean isNull(String str) {
		return str == null;
	}

	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

	public static boolean isEmpty(String str) {
		Objects.requireNonNull(str);
		return str.trim().isEmpty();
	}

	public static boolean isNotEmpty(String str) {
		Objects.requireNonNull(str);
		return !isEmpty(str);
	}

	public static String camelCaseToUnderline(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new RuntimeException(str + " is empty");
		}
		
		String regex = "[A-Z]";
		String first = str.substring(0, 1);
		if(first.matches(regex))
			str = firstToLowerCase(str);
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuilder sb = new StringBuilder(str);
		int i = 0;
		while (matcher.find()) {
			sb.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
			i++;
		}
		return sb.toString();
	}

	public static String underLineToCamelCase(String str) {
		return underLineToCamelCase(str, false);
	}

	public static String underLineToCamelCase(String str, boolean firstToUpperCase) {
		if (isEmpty(str))
			return "";
		StringBuilder sb = new StringBuilder();
		String[] sarr = str.split("_");

		if (firstToUpperCase)
			sb.append(firstToUpperCase(sarr[0]));
		else
			sb.append(sarr[0]);

		for (int i = 1; i < sarr.length; i++) {
			sb.append(firstToUpperCase(sarr[i]));
		}
		return sb.toString();
	}

	public static String firstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String firstToLowerCase(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
}
