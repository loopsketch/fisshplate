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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.seasar.fisshplate.consts.FPConsts;
import org.seasar.fisshplate.context.FPContext;
import org.seasar.fisshplate.core.parser.handler.CellParserHandler;
import org.seasar.fisshplate.exception.FPMergeException;
import org.seasar.fisshplate.wrapper.CellWrapper;
import org.seasar.fisshplate.wrapper.RowWrapper;

/**
 * 行要素クラスです。行の中にあるセルの情報を保持します。
 *
 * @author rokugen
 *
 */
public class RowElement implements TemplateElement {
	private List cellElementList = new ArrayList();
	private short rowHeight;
	private Root root;


	/**
	 * コンストラクタです。テンプレート側の行オブジェクトを受け取り、その行内のセル情報を解析して保持します。
	 *
	 * @param templateSheet
	 *            テンプレート側のシート
	 * @param templateRow
	 *            テンプレート側の行オブジェクト
	 * @param root
	 *            自分自身が属してるルート要素クラス
	 * @param cellParserHandler
	 *             セルを解析するクラス
	 */
	public RowElement(RowWrapper templateRow, Root root, CellParserHandler cellParserHandler) {
		this.root = root;
		if (templateRow.isNullRow()) {
			this.rowHeight = templateRow.getSheet().getSheet().getDefaultRowHeight();
			cellElementList.add(new NullElement());
			return;
		}
		Row hssfRow = templateRow.getRow();
		this.rowHeight = hssfRow.getHeight();
		for (int i = 0; i < templateRow.getCellCount(); i++) {
			CellWrapper templateCell = templateRow.getCell(i);
			TemplateElement element = cellParserHandler.getElement(templateCell);
			cellElementList.add(element);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.seasar.fisshplate.core.TemplateElement#merge(org.seasar.fisshplate.context.FPContext)
	 */
	public void merge(FPContext context) throws FPMergeException {
		// ヘッダ・フッタ制御
		if (context.shouldHeaderOut()) {
			context.setShouldHeaderOut(false);
			root.getPageHeader().merge(context);
		}
		context.setShouldFooterOut(true);

		Row outRow = context.createCurrentRow();
		outRow.setHeight(rowHeight);
		Map data = context.getData();
		data.put(FPConsts.ROW_NUMBER_NAME, new Integer(context.getCurrentRowNum() + 1));
		for (int i = 0; i < cellElementList.size(); i++) {
			TemplateElement elem = (TemplateElement) cellElementList.get(i);
			elem.merge(context);
		}
		context.nextRow();
	}

	public short getRowHeight(){
	    return rowHeight;
	}

	/**
	 * 行に含まれるセルのリストを戻します。
	 * @return セルのリスト
	 */
	public List getCellElementList(){
		return cellElementList;
	}

}
