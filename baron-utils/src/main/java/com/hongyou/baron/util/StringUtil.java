/**
 *     Copyright 2014, Chengyou Software Development Studio.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hongyou.baron.util;

import java.text.MessageFormat;

/**
 * 字符串处理类
 *
 * @author Berlin
 */
public final class StringUtil {

	/**
	 * 私有构造函数
	 */
	private StringUtil() {}

	/**
	 * @param pattern 格式化的字符串
	 * @param args 格式化参数
	 */
	public static String format(final String pattern, final Object... args) {
		return MessageFormat.format(pattern, args);
	}
}
