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
package org.seasar.fisshplate.core.element;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.seasar.fisshplate.context.FPContext;
import org.seasar.fisshplate.wrapper.CellWrapper;

/**
 * 値を持つ通常のセルの情報を保持する要素クラスです。
 * @author rokugen
 *
 */
public class GenericCell extends AbstractCell {

	/**
	 * コンストラクタです。
	 * @param cell 保持するテンプレート側のセル
	 */
	public GenericCell(CellWrapper cell) {
		super(cell);
	}

	/* (non-Javadoc)
	 * @see org.seasar.fisshplate.element.TemplateElement#merge(org.seasar.fisshplate.context.FPContext)
	 */
	protected void mergeImpl(FPContext context,Cell out) {
		Cell templateCell = cell.getCell();
		Object cellValue = getCellValue();

		int cellType = templateCell.getCellType();
		if(cellType == Cell.CELL_TYPE_FORMULA){
			out.setCellFormula((String)cellValue);
		}else if(cellType == Cell.CELL_TYPE_ERROR){
			out.setCellErrorValue(((Byte)cellValue).byteValue());
		}else if(cellValue instanceof Date){
			out.setCellValue(((Date)cellValue));
			out.setCellType(Cell.CELL_TYPE_NUMERIC);
		}else if(cellValue instanceof String){
			out.setCellValue(cell.getCreationHelper().createRichTextString((String)cellValue));
			out.setCellType(Cell.CELL_TYPE_STRING);
		}else if(cellValue instanceof Boolean){
			out.setCellValue(((Boolean)cellValue).booleanValue());
			out.setCellType(Cell.CELL_TYPE_BOOLEAN);
		}else if(isNumber(cellValue)){
			out.setCellValue(Double.valueOf(cellValue.toString()).doubleValue());
			out.setCellType(Cell.CELL_TYPE_NUMERIC);
		}
	}

	private boolean isNumber(Object value){
		if(value == null || value instanceof String){
			return false;
		}

		try{
			Double.valueOf(value.toString());
			return true;
		}catch (NumberFormatException e) {
			return false;
		}
	}

}
