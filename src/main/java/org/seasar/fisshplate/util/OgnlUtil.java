/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.fisshplate.util;

import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

public class OgnlUtil {
	private OgnlUtil(){}
	
	public static final Object getValue(String expression, Map data){
		try {
			return Ognl.getValue(expression, data);
		} catch (OgnlException e) {
			throw new RuntimeException(e);
		}
	}

}
