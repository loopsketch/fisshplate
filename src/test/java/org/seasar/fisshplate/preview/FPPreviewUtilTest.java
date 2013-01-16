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

import java.io.FileOutputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author rokugen
 */
public class FPPreviewUtilTest extends TestCase {

	public FPPreviewUtilTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testプレビュー() throws Exception{
		InputStream data = getClass().getResourceAsStream("/MapBuilderTest.xls");
		InputStream template = getClass().getResourceAsStream("/MapBuilderTest_template.xls");

		Workbook out = FPPreviewUtil.getWorkbook(template, data);

		FileOutputStream os = new FileOutputStream("target/FPPreviewTest_stream_out.xls");
		out.write(os);
		data.close();
		template.close();
		os.close();
	}

	public void testプレビュー2() throws Exception{
		Workbook data = WorkbookFactory.create(getClass().getResourceAsStream("/MapBuilderTest.xls"));
		Workbook template = WorkbookFactory.create(getClass().getResourceAsStream("/MapBuilderTest_template.xls"));

		Workbook out = FPPreviewUtil.getWorkbook(template, data);

		FileOutputStream os = new FileOutputStream("target/FPPreviewTest_workbook_out.xls");
		out.write(os);
		os.close();
	}

	public void testサイトサンプル用() throws Exception{
		InputStream data = getClass().getResourceAsStream("/preview_data.xls");
		InputStream template = getClass().getResourceAsStream("/preview_template.xls");

		Workbook out = FPPreviewUtil.getWorkbook(template, data);

		FileOutputStream os = new FileOutputStream("target/preview_out.xls");
		out.write(os);
		data.close();
		template.close();
		os.close();

	}

    public void testピボットテーブル() throws Exception{
        InputStream data = getClass().getResourceAsStream("/pibot_template_data.xls");
        InputStream template = getClass().getResourceAsStream("/pibot_template.xls");

        Workbook out = FPPreviewUtil.getWorkbook(template, data);

        FileOutputStream os = new FileOutputStream("target/pibot_out.xls");
        out.write(os);
        data.close();
        template.close();
        os.close();

    }


}


