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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Workbook;
import org.seasar.fisshplate.consts.FPConsts;
import org.seasar.fisshplate.context.FPContext;
import org.seasar.fisshplate.exception.FPMergeException;
import org.seasar.fisshplate.util.FileInputStreamUtil;
import org.seasar.fisshplate.util.ImageIOUtil;
import org.seasar.fisshplate.util.StringUtil;
import org.seasar.fisshplate.wrapper.CellWrapper;

/**
 * 画像処理用の要素クラスです
 *
 * @author a-conv
 */
public class Picture extends AbstractCell {

	/**
	 * コンストラクタです。
	 *
	 * @param cell
	 *            テンプレート側のセル
	 */
	public Picture(CellWrapper cell) {
		super(cell);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.seasar.fisshplate.core.TemplateElement#merge(org.seasar.fisshplate.context.FPContext)
	 */
	protected void mergeImpl(FPContext context,Cell out) throws FPMergeException {
		String cellValue = getCellValue().toString();
		Pattern pat = Pattern.compile("^\\s*\\#picture\\((.*)\\s+cell=(.+)\\s*\\s+row=(.+)\\)");
		Matcher mat = pat.matcher(cellValue);
        if(!mat.find()){
            throw new FPMergeException(FPConsts.MESSAGE_ID_PICTURE_MERGE_ERROR,
                    new Object[]{cellValue},
                    cell.getRow());
        }
		String picturePath = mat.group(1);
		String cellRange = mat.group(2);
		String rowRange = mat.group(3);
		int cellRangeIntVal = Integer.parseInt(cellRange);
		int rowRangeIntVal = Integer.parseInt(rowRange);
		if (isWritePicture(picturePath)) {
			writePicture(picturePath, cellRangeIntVal, rowRangeIntVal, context);
		}
	}

	private boolean isWritePicture(String picturePath) {
		if (picturePath.equals("")) {
			return false;
		}
		if (picturePath.length() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 画像用オブジェクトを作成する
	 *
	 * @param width
	 * @param height
	 * @param cellNo
	 * @param rowNo
	 * @param rowRangeIntVal
	 * @param cellRangeIntVal
	 * @return
	 */
	private ClientAnchor createAnchor(int width, int height, int cellNo, int rowNo, int cellRangeIntVal, int rowRangeIntVal) {
		ClientAnchor anchor = cell.getCreationHelper().createClientAnchor();
		// TODO サイズを指定が利かないので最大値で初期化
		anchor.setDx1(0);
		anchor.setDx2(0);
		anchor.setDy1(0);
		anchor.setDy2(255);

		int fromCellNo = cellNo;
		int toCellNo = cellNo + cellRangeIntVal;
		int fromRowNo = rowNo;
		int toRowNo = rowNo + rowRangeIntVal;

		anchor.setCol1((short) fromCellNo);
		anchor.setCol2((short) toCellNo);
		anchor.setRow1(fromRowNo);
		anchor.setRow2(toRowNo);
		anchor.setAnchorType(2);
		return anchor;
	}

	/**
	 * 画像タイプ取得
	 *
	 * @param suffix
	 * @return
	 * @throws FPMergeException
	 */
	private int setupPictureType(String suffix) throws FPMergeException {
		if (suffix.toLowerCase().equals("jpg")) {
			return Workbook.PICTURE_TYPE_JPEG;
		}
		if (suffix.toLowerCase().equals("png")) {
			return Workbook.PICTURE_TYPE_PNG;
		}
		throw new FPMergeException(FPConsts.MESSAGE_PICTURE_TYPE_INVALID);
	}

	/**
	 * 画像貼り付け
	 *
	 * @param picturepath
	 * @param rowRangeIntVal
	 * @param cellRangeIntVal
	 * @param context
	 * @throws FPMergeException
	 */
	private void writePicture(String picturepath, int cellRangeIntVal, int rowRangeIntVal, FPContext context) throws FPMergeException {

		FileInputStream imgFis = FileInputStreamUtil.createFileInputStream(picturepath);
		BufferedImage img = ImageIOUtil.read(imgFis);
		FileInputStreamUtil.close(imgFis);

		Workbook workbook = cell.getRow().getSheet().getWorkbook().getWorkbook();
		Drawing patriarch = context.getPartriarch();

		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();
		int cellNo = context.getCurrentCellNum();
		int rowNo = context.getCurrentRowNum();
		ClientAnchor anchor = createAnchor(imgWidth, imgHeight, cellNo, rowNo, cellRangeIntVal, rowRangeIntVal);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String suffix = StringUtil.parseSuffix(picturepath);
		ImageIOUtil.write(img, suffix, baos);

		byte[] pictureData = baos.toByteArray();
		int pictureType = setupPictureType(suffix);
		int pictureIndex = workbook.addPicture(pictureData, pictureType);

		patriarch.createPicture(anchor, pictureIndex);

		ImageIOUtil.close(baos);
	}

}
