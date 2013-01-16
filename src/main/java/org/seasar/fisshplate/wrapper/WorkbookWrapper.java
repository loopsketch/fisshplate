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
package org.seasar.fisshplate.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * HSSFWorkbookのラッパークラスです。
 * @author rokugen
 */
public class WorkbookWrapper {
	private Workbook workbook;
	private List sheetList = new ArrayList();

	public WorkbookWrapper(Workbook workbook){
		this.workbook = workbook;
		for(int i=0; i < workbook.getNumberOfSheets();i++){
			sheetList.add(new SheetWrapper(workbook.getSheetAt(i),this,i));
		}
	}

	public CreationHelper getCreationHelper() {
		return workbook.getCreationHelper();
	}

	public Workbook getWorkbook(){
		return workbook;
	}

	public SheetWrapper getSheetAt(int index){
		return (SheetWrapper) sheetList.get(index);
	}

	public SheetWrapper getSheetByName(String sheetName) {
		for(int i=0; i < sheetList.size(); i++){
			SheetWrapper sheet = (SheetWrapper) sheetList.get(i);
			String name = sheet.getSheetName();
			if(name.equals(sheetName)){
				return sheet;
			}
		}

		return null;
	}

	public int getSheetCount() {
		return sheetList.size();
	}


}
