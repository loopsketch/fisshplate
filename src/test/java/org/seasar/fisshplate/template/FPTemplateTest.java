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
package org.seasar.fisshplate.template;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.seasar.fisshplate.context.FPContext;
import org.seasar.fisshplate.core.element.TemplateElement;
import org.seasar.fisshplate.core.parser.FPParser;
import org.seasar.fisshplate.core.parser.RowParser;
import org.seasar.fisshplate.exception.FPException;
import org.seasar.fisshplate.exception.FPMergeException;
import org.seasar.fisshplate.exception.FPParseException;
import org.seasar.fisshplate.wrapper.CellWrapper;

public class FPTemplateTest extends TestCase {
    private FPTemplate template;

    public FPTemplateTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void test行の要素がリストの場合() throws Exception  {
        InputStream is = getClass().getResourceAsStream("/FPTemplateTest.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            Map map = new HashMap();
            map.put("title", "タイトルである");
            List aList = new ArrayList();
            aList.add(new A("1行目",10,new Date()));
            aList.add(new A("2行目",20,new Date()));
            aList.add(new A("3行目",30,new Date()));
            aList.add(new A("4行目",10,new Date()));
            aList.add(new A("5行目",20,new Date()));
            aList.add(new A("6行目",30,new Date()));
            map.put("b", aList);

            wb = template.process(is,map);
        } catch (FPParseException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out.xls");
        wb.write(fos);
        fos.close();

    }

    public void test行の要素がリストの場合_1件だけ() throws Exception  {
        InputStream is = getClass().getResourceAsStream("/FPTemplateTest.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            Map map = new HashMap();
            map.put("title", "タイトルである");
            List aList = new ArrayList();
            aList.add(new A("1行目",10,new Date()));
            map.put("b", aList);

            wb = template.process(is,map);
        } catch (FPParseException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out_1.xls");
        wb.write(fos);
        fos.close();

    }

    public void test行の要素が配列の場合() throws Exception  {
        InputStream is = getClass().getResourceAsStream("/FPTemplateTest.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            Map map = new HashMap();
            map.put("title", "配列のテストである");
            A[] aList = new A[]{
            new A("1行目",10,new Date()),
            new A("2行目",20,new Date()),
            new A("3行目",30,new Date()),
            new A("4行目",10,new Date())
            };
            map.put("b", aList);

            wb = template.process(is, map);
        } catch (FPParseException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out_array.xls");
        wb.write(fos);

    }

    public void testループのネスト() throws Exception  {
        InputStream is = getClass().getResourceAsStream("/FPTemplateTest_nestedLoop.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            Object[] parentList = new Object[]{
                    new String[]{"子供1","子供2","子供3","子供4"},
                    new String[]{"子供5","子供6","子供7","子供8"},
                    new String[]{"子供9","子供10","子供11","子供12"}
            };
            Map map = new HashMap();
            map.put("parentList", parentList);

                wb = template.process(is,map);
        } catch (FPException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out_nestedLoop.xls");
        wb.write(fos);

    }

    public void test最後のヘッダフッタ制御のテスト_ぴったり収まっちゃう場合() throws Exception{
        InputStream is = getClass().getResourceAsStream("/FPTemplateTest_lastPageHandling.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            Map map = new HashMap();
            map.put("title", "タイトルである");
            List aList = new ArrayList();
            aList.add(new A("1行目",10,new Date()));
            aList.add(new A("2行目",20,new Date()));
            aList.add(new A("3行目",30,new Date()));
            aList.add(new A("4行目",10,new Date()));
            aList.add(new A("5行目",20,new Date()));
            aList.add(new A("6行目",30,new Date()));
            aList.add(new A("7行目",10,new Date()));
            aList.add(new A("8行目",20,new Date()));
            aList.add(new A("9行目",30,new Date()));
            map.put("b", aList);

            wb = template.process(is,map);
        } catch (FPException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out_lastPageHandling.xls");
        wb.write(fos);

    }

    public void test最後のヘッダフッタ制御のテスト_あまる場合() throws Exception{
        InputStream is = getClass().getResourceAsStream("/FPTemplateTest_lastPageHandling2.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            Map map = new HashMap();
            map.put("title", "タイトルである");
            List aList = new ArrayList();
            aList.add(new A("1行目",10,new Date()));
            aList.add(new A("2行目",20,new Date()));
            aList.add(new A("3行目",30,new Date()));
            aList.add(new A("4行目",10,new Date()));
            aList.add(new A("5行目",20,new Date()));
            aList.add(new A("6行目",30,new Date()));
            aList.add(new A("7行目",10,new Date()));
            aList.add(new A("8行目",20,new Date()));
            aList.add(new A("9行目",30,new Date()));
            map.put("b", aList);

            wb = template.process(is,map);
        } catch (FPException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out_lastPageHandling2.xls");
        wb.write(fos);

    }

    public void test空行指定テスト() throws Exception{
        InputStream is = getClass().getResourceAsStream("/FPTemplateTest_iteratorMax.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            Map map = new HashMap();
            map.put("title", "タイトルである");
            List aList = new ArrayList();
            aList.add(new A("1行目",10,new Date()));
            aList.add(new A("2行目",20,new Date()));
            aList.add(new A("3行目",30,new Date()));
            aList.add(new A("4行目",10,new Date()));
            aList.add(new A("5行目",20,new Date()));
            aList.add(new A("6行目",30,new Date()));
            map.put("b", aList);

            wb = template.process(is,map);
        } catch (FPException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out_iteratorMax.xls");
        wb.write(fos);
    }

    public void test空行指定テスト_改ページ対応() throws Exception{
        InputStream is = getClass().getResourceAsStream("/FPTemplateTest_iteratorMax_pageBreak.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            Map map = new HashMap();
            map.put("title", "タイトルである");
            List aList = new ArrayList();
            aList.add(new A("1行目",10,new Date()));
            aList.add(new A("2行目",20,new Date()));
            aList.add(new A("3行目",30,new Date()));
            aList.add(new A("4行目",10,new Date()));
            aList.add(new A("5行目",20,new Date()));
            aList.add(new A("6行目",30,new Date()));
            aList.add(new A("7行目",10,new Date()));
            aList.add(new A("8行目",20,new Date()));
            aList.add(new A("9行目",30,new Date()));
            aList.add(new A("10行目",10,new Date()));
            aList.add(new A("11行目",20,new Date()));
            aList.add(new A("12行目",30,new Date()));
            map.put("b", aList);

            wb = template.process(is,map);
        } catch (FPException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out_iteratorMax_pageBreak.xls");
        wb.write(fos);
    }

    public void test独自パーサ適用例() throws Exception  {
        InputStream is = getClass().getResourceAsStream("/FPTemplateTest.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            template.addRowParser(new RowParser(){
                public boolean process(CellWrapper cell, FPParser parser)	throws FPParseException {
                    String value =cell.getStringValue();
                    if(!"あれやこれや".equals(value)){
                        return false;
                    }
                    TemplateElement elem = new Areya(cell);
                    parser.addTemplateElement(elem);
                    return true;
                }});
            Map map = new HashMap();
            map.put("title", "タイトルである");
            List aList = new ArrayList();
            aList.add(new A("1行目",10,new Date()));
            aList.add(new A("2行目",20,new Date()));
            aList.add(new A("3行目",30,new Date()));
            aList.add(new A("4行目",10,new Date()));
            aList.add(new A("5行目",20,new Date()));
            aList.add(new A("6行目",30,new Date()));
            map.put("b", aList);

            wb = template.process(is,map);
        } catch (FPParseException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out_customparser.xls");
        wb.write(fos);
        fos.close();

    }

    public void test一行だけの場合のエラーテスト()throws Exception{
        InputStream is = getClass().getResourceAsStream("/onlyOneRowErrorTest.xls");
        Workbook wb;
        try {
            template = new FPTemplate();
            Map map = new HashMap();
            map.put("title", "タイトルである");
            List aList = new ArrayList();
            aList.add(new A("1行目",10,new Date()));
            aList.add(new A("2行目",20,new Date()));
            aList.add(new A("3行目",30,new Date()));
            aList.add(new A("4行目",10,new Date()));
            aList.add(new A("5行目",20,new Date()));
            aList.add(new A("6行目",30,new Date()));
            map.put("b", aList);

            wb = template.process(is,map);
        } catch (FPException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }finally{
            is.close();
        }

        FileOutputStream fos = new FileOutputStream("target/out_onlyOneRowErrorTest.xls");
        wb.write(fos);
    }

    public void testカメラがある場合のテスト() throws Exception{
        InputStream is = getClass().getResourceAsStream("/withCameraTest.xls");
        WorkbookFactory.create(is);
    }

    private class Areya implements TemplateElement{
        private CellWrapper originalCell;

        public Areya(CellWrapper cell){
            originalCell = cell;
        }

        public void merge(FPContext context) throws FPMergeException {
            Cell currentCell = context.getCurrentCell();
            currentCell.setCellStyle(originalCell.getCell().getCellStyle());
            currentCell.setCellValue(currentCell.getSheet().getWorkbook().getCreationHelper().createRichTextString("独自タグテストです"));
            context.nextRow();
        }

    }


    public class A{
        private String name;
        private int num;
        private Date date;
        A(String name, int num, Date date){
            this.name = name;
            this.num = num;
            this.date = date;
        }
        public Date getDate() {
            return date;
        }
        public void setDate(Date date) {
            this.date = date;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getNum() {
            return num;
        }
        public void setNum(int num) {
            this.num = num;
        }


    }

}
