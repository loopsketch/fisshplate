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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.seasar.fisshplate.util.FPPoiUtil;

/**
 * Cellのラッパークラスです。
 * @author rokugen
 */
public class CellWrapper {
    private Cell cell;
    private RowWrapper rowWrapper;

    public CellWrapper(Cell cell, RowWrapper row){
        this.rowWrapper = row;
        this.cell = cell;

    }

	public CreationHelper getCreationHelper() {
		return getRow().getSheet().getWorkbook().getCreationHelper();
	}

	public Cell getCell(){
        return cell;
    }

    public RowWrapper getRow(){
        return rowWrapper;
    }

    public boolean isNullCell() {
        return cell == null;
    }

    public int getCellIndex(){
        if(isNullCell()){
            return -1;
        }
        return cell.getColumnIndex();
    }

    public String getStringValue(){
        return FPPoiUtil.getStringValue(cell);
    }

    public Object getObjectValue() {
        return FPPoiUtil.getCellValueAsObject(cell);
    }

}