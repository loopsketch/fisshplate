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
package org.seasar.fisshplate.preview;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.seasar.fisshplate.exception.FPException;
import org.seasar.fisshplate.template.FPTemplate;

/**
 * テンプレートファイルと埋め込みデータファイルから出力ファイルを生成するユーティリティクラスです。
 * @author rokugen
 */
public class FPPreviewUtil {
	private FPPreviewUtil(){}

	/**
	 * @param template
	 * @param data
	 * @return
	 * @throws FPException
	 */
	public static final Workbook getWorkbook(Workbook template, Workbook data) throws FPException{
		FPTemplate fptemp = new FPTemplate();
		MapBuilder mb = new MapBuilder();
		Map map = mb.buildMapFrom(data);
		return fptemp.process(template, map);
	}

	/**
	 * @param template
	 * @param data
	 * @return
	 * @throws FPException
	 * @throws IOException
	 */
	public static final Workbook getWorkbook(InputStream template, InputStream data) throws FPException, IOException{
		try {
			Workbook tempWb = WorkbookFactory.create(template);
			Workbook dataWb = WorkbookFactory.create(data);
			return getWorkbook(tempWb, dataWb);
		} catch (InvalidFormatException ex) {
			throw new FPException(ex.getMessage());
		}
	}

}
