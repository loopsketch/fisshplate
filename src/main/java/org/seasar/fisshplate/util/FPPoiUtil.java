package org.seasar.fisshplate.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;


/**
 * POIの操作の便利メソッドを集めたユーティリティクラスです。
 * @author rokugen
 *
 */
public class FPPoiUtil {
    private FPPoiUtil(){}

    /**
     * セルの書式設定に基いてセルの値を戻します。
     * @param cell
     * @return セルの値
     */
    public static Object getCellValueAsObject(Cell cell) {
        if(cell == null){
            return null;
        }
        int cellType = cell.getCellType();
        Object ret = null;

        switch(cellType){
        case Cell.CELL_TYPE_NUMERIC:
            ret = getValueFromNumericCell(cell);
            break;
        case Cell.CELL_TYPE_STRING:
            ret = cell.getRichStringCellValue().getString();
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            ret = Boolean.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_FORMULA:
            ret = cell.getCellFormula();
            break;
        case Cell.CELL_TYPE_ERROR:
            ret = new Byte(cell.getErrorCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            break;
        default:
            return null;
        }

        return ret;
    }

    private static Object getValueFromNumericCell(Cell cell){
        String str = cell.toString();
        if(str.matches("\\d+-.+-\\d+")){
            return cell.getDateCellValue();
        }else{
            return new Double(cell.getNumericCellValue());
        }
    }

    /**
     *文字列を含むセルの値を文字列として戻します。
     *セルの書式が文字列でない場合はnullを戻します。
     * @param cell
     * @return セルの値
     */
    public static String getStringValue(Cell cell){
        if(cell == null){
            return null;
        }
        RichTextString richVal =  cell.getRichStringCellValue();
        if(richVal == null){
            return null;
        }
        return richVal.getString();
    }

}
