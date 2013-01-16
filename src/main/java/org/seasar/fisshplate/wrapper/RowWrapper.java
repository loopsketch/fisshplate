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

import org.apache.poi.ss.usermodel.Row;


/**
 * Rowのラッパークラスです。
 * @author rokugen
 */
public class RowWrapper {
    private Row row;
    private SheetWrapper sheetWrapper;
    private List cellList = new ArrayList();

    public RowWrapper(Row row, SheetWrapper sheet){
        this.sheetWrapper = sheet;
        this.row = row;
        if(row != null){
            addCellsToList(row);
        }
    }

    private void addCellsToList(Row row){
        for(int i=0; i < row.getLastCellNum(); i++){
            cellList.add(new CellWrapper(row.getCell(i),this));
        }
    }

    public boolean isNullRow(){
        return row == null;
    }

    public Row getRow(){
        return row;
    }

    public SheetWrapper getSheet(){
        return sheetWrapper;
    }

    public CellWrapper getCell(int index){
        if(index + 1 > cellList.size()){
            return null;
        }
        return (CellWrapper) cellList.get(index);
    }

    public int getCellCount() {
        return cellList.size();
    }


}
