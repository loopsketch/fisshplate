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
import org.apache.poi.ss.usermodel.Sheet;


/**
 * Sheetのラッパークラスです。
 * @author rokugen
 */
public class SheetWrapper {
    private Sheet sheet;
    private WorkbookWrapper workbook;
    private List rowList = new ArrayList();
    private int sheetIndex;

    public SheetWrapper(Sheet sheet, WorkbookWrapper workbook, int sheetIndex){
        this.workbook = workbook;
        this.sheet = sheet;
        this.sheetIndex = sheetIndex;
        for(int i=0; i <= sheet.getLastRowNum(); i++){
            rowList.add(new RowWrapper(sheet.getRow(i),this));
        }
    }

    public Sheet getSheet(){
        return sheet;
    }

    public WorkbookWrapper getWorkbook(){
        return workbook;
    }

    public RowWrapper getRow(int index){
        return (RowWrapper) rowList.get(index);
    }

    public int getRowCount() {
        return rowList.size();
    }
    public int getSheetIndex() {
        return sheetIndex;
    }

    public String getSheetName(){
        return workbook.getWorkbook().getSheetName(sheetIndex);
    }

    public void removeRow(int i){
        RowWrapper row = this.getRow(i);
        sheet.removeRow(row.getRow());
        rowList.remove(i);
    }

    /**
     * データ埋め込みの準備のために、シートを初期化します。
     */
    public void prepareForMerge(){
        removeAllRow();
        removeAllMergedRegion();
    }

    private void removeAllRow(){
        //POIのバグへの対策
        //1行だけのシートだったら繰り返し行もないはずだからクリアする必要はない、はず。
        // 3.2-FINALにて、バグフィックスされたので、削除。
//        if(getRowCount() < 2){
//            return;
//        }
        for(int i=0; i < getRowCount();i++){
            Row row = getRow(i).getRow();
            if(row != null){
                sheet.removeRow(row);
            }
        }
    }

    private void removeAllMergedRegion(){
        for(int i=0; 0 < sheet.getNumMergedRegions();i++){
            sheet.removeMergedRegion(0);
        }
    }

}
