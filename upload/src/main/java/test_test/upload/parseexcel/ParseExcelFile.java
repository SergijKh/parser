package test_test.upload.parseexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import test_test.upload.database.service.LookUpService;
import test_test.upload.database.service.TableService;
import test_test.upload.model.form.Field;
import test_test.upload.model.form.Table;
import test_test.upload.model.map.conversion.Conversion;
import test_test.upload.model.map.conversion.ConversionField;
import test_test.upload.model.map.conversion.FieldFormat;
import test_test.upload.model.map.substitute.Substitute;
import test_test.upload.model.map.substitute.Value;
import test_test.upload.model.parse.MapSource;
import test_test.upload.model.parse.Schema;
import test_test.upload.model.parse.Source;
import test_test.upload.model.staticvariablesxmlfile.StaticVariablesXML;
import test_test.upload.parsexml.IParse;
import test_test.upload.parsexml.ParseXml;
import test_test.upload.result.write.WriteResultInFileExcel;

@Component("parseExcel")
public class ParseExcelFile implements IParseExcel {
	private static final Logger logger1 = Logger.getLogger(ParseExcelFile.class);
	private static final String TABLE_FIELD = "table.field";
	private static final String UPDATE = "U";
	@Autowired
	ConversionField conversionField;
	@Autowired
	LookUpService lookUpService;
	@Autowired
	TableService tableService;
	@Autowired
	WriteResultInFileExcel writeResultInFileExcel;

	public ParseExcelFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parseExcelFile(IParse parseXml) {
		Document doc = (Document) parseXml.initDomParser();
		 if (doc!=null){
		List<Source> listSource = parseXml.getSource(doc);
		List<MapSource> mapSourceList = parseXml.getMapSource(doc);
		List<Schema> shemaList = parseXml.getSxema(doc);
		MapSource mapSource = null;
		Schema schema = null;
		Source source = null;
		List<Table> listTable = null;
		for (int sour = 0; sour < listSource.size(); sour++) {
			source = listSource.get(sour);
			for (int map = 0; map < mapSourceList.size(); map++) {
				if (source.getId().equals(mapSourceList.get(map).getIdSource())) {
					mapSource = mapSourceList.get(map); // map source equals id
				}
				for (int sch = 0; sch < mapSourceList.size(); sch++) {
					if ((mapSourceList.get(map).getIdSchema()).equals(shemaList.get(sch).getId())) {
						schema = shemaList.get(sch); // schema id equals map
														// source schema id
					}
				}
			}

			Sheet sheet = null;
			try {
				sheet = readFromExcel(source.getPathInputFile()).getSheetAt(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger1.error(e);
			}

			ArrayList<String> listHeader = (ArrayList<String>) getHeaderList(sheet, source.getHeaderRows());
			Row rowData = null;
			Map<String, Integer> listCellNameField = writeResultInFileExcel.addtHeaderList(schema, source);// list  number  cell header field table
			HashMap<String, String> hashMapRowAndheader = new HashMap<String, String>();// where key  header value data  row

			for (int i = source.getFieldNameRowNumer() + 1; i < sheet.getLastRowNum() + 1; i++) {
				rowData = sheet.getRow(i);
				getOneRow(rowData, listHeader, hashMapRowAndheader);// hash map  key field  in  database  value database
				conversionField.allMapConversion(hashMapRowAndheader, mapSource.getConversion().getFormat()); // conversion field trim,  left,right,
				List<Map<String, String>> listLookUp = mapSource.getConversion().getLookUp();
				lookUpUpdateContextVariable(hashMapRowAndheader, listLookUp);
				substitudeValueData(mapSource.getConversion().getListSubstitute(), hashMapRowAndheader, lookUpService);
				if (source.getField_names_format().equals(TABLE_FIELD)) {
					listTable = getListTable(schema, hashMapRowAndheader, source.getUpdate_mode_field_name());
					logger1.info("add");

					try {
						addTableINBase(listTable, listCellNameField, source, i, readFromExcel(source.getPathOutFile()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		}
      }
	}

	/**
	 * update context variable
	 * 
	 * @param hashMapRowAndheader
	 *            map field and data
	 * @param listLookUp
	 *            list lookup in map
	 */
	private void lookUpUpdateContextVariable(HashMap<String, String> hashMapRowAndheader,
			List<Map<String, String>> listLookUp) {
		String value = "";// the value of the in lookup
		for (int listM = 0; listM < listLookUp.size(); listM++) { // lookup
																	// table
			HashMap<String, String> mapLookUp = (HashMap<String, String>) listLookUp.get(listM);
			value = lookUpService.seletGetFieldbyID(mapLookUp);
			hashMapRowAndheader.put(mapLookUp.get(StaticVariablesXML.UPDATE_INTO_VARIABLE), value);

		}
	}

	/**
	 * add HasHmap header and value
	 * 
	 * @param row
	 *            one row in file
	 * @param header
	 *            list header in file
	 * @param hashmap
	 *            HashMap where key header value one field in row
	 */
	@SuppressWarnings("unchecked")
	public void getOneRow(Row row, List<String> header, HashMap<String, String> hashmap) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy");
		Iterator<Cell> cells = row.iterator();
		int count = 0;
		for (int cn = 0; cn < row.getLastCellNum() - 1; cn++) {
			Cell cell = row.getCell(cn, Row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				hashmap.put(header.get(count++), "");
			} else {
				int cellType = cell.getCellType();

				switch (cellType) {
				case Cell.CELL_TYPE_STRING:
					hashmap.put(header.get(count++), cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:

					if (DateUtil.isCellDateFormatted(cell)) {
						hashmap.put(header.get(count++), "" + dateFormat.format(cell.getDateCellValue()));
					} else {
						hashmap.put(header.get(count++), "" + cell.getNumericCellValue());
					}
					break;
				case Cell.CELL_TYPE_BLANK:
					hashmap.put(header.get(count++), "");
					break;
				default:
					logger1.error("not type field");
					break;
				}
			}
		}

	}

	/**
	 * method return list header
	 * 
	 * @param sheet
	 * @param headerRow
	 *            number row where find header
	 * @return list header in file
	 */
	public List<String> getHeaderList(Sheet sheet, int headerRow) {
		ArrayList<String> listHeader = new ArrayList<String>();
		Row rowHeader = sheet.getRow(headerRow);
		Iterator<Cell> iteratorHeader = rowHeader.iterator();
		while (iteratorHeader.hasNext()) {
			Cell cell = (Cell) iteratorHeader.next();
			int cellType = cell.getCellType();
			switch (cellType) {
			case Cell.CELL_TYPE_STRING:
				listHeader.add(cell.getStringCellValue());
				break;

			}
		}
		return listHeader;

	}

	/**
	 * method return Sheet depending on the extension
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Workbook readFromExcel(String file) throws IOException {
		Workbook excelBook = null;
		if (getExstension(file).equals("xls")) {
			excelBook = new HSSFWorkbook(new FileInputStream(file));
			return excelBook;
		} else if (getExstension(file).equals("xlsx")) {
			excelBook = new XSSFWorkbook(new FileInputStream(file));
			return excelBook;
		} else {
			logger1.error("the file extension is not supported");
			return null;
		}

	}

	/**
	 * method return extension file
	 * 
	 * @param file
	 * @return
	 */
	public String getExstension(String file) {
		Pattern p = Pattern.compile("\\.(\\w+$)");
		Matcher matcher = p.matcher(file);
		matcher.find();
		return matcher.group(1);
	}

	/**
	 * method create list table with value in one row in file when field names
	 * format "table.field"
	 * 
	 * @param hashMapRowAndheader
	 *            HashMap where key name header and value in one row
	 * @return
	 */
	public List<Table> getListTable(Schema schema, HashMap<String, String> hashMapRowAndheader, String updataOrInsert) {
		Table table = null;
		List<Field> listField = null;
		String nameTableShema = null;
		String nameTabledHashMap = null;
		String nameTbFieldHash = null;
		String nameFieldHash = null;
		Pattern p = Pattern.compile("(\\w+)\\.(\\w+$)");
		Set<String> keyNameTableValue = hashMapRowAndheader.keySet();
		Iterator<String> iteratNameT = null;
		List<Table> listNameTable = schema.getListTable();
		for (int tab = 0; tab < listNameTable.size(); tab++) {
			nameTableShema = listNameTable.get(tab).getNameTable();
			table = listNameTable.get(tab);
			listField = table.getListField();
			iteratNameT = keyNameTableValue.iterator();
			while (iteratNameT.hasNext()) {
				nameTbFieldHash = iteratNameT.next();
			}
			iteratNameT = keyNameTableValue.iterator();
			while (iteratNameT.hasNext()) {
				nameTbFieldHash = iteratNameT.next();
				Matcher matcher = p.matcher(nameTbFieldHash);
				matcher.find();
				logger1.info(
						"????????????????????????????????????????????????????????????????///////" + nameTbFieldHash);
				nameTabledHashMap = matcher.group(1);

				if (nameTabledHashMap.equals(nameTableShema)) {
					logger1.info(
							nameTbFieldHash + "--->" + nameTableShema + "  nameTabledHashMap  " + nameTabledHashMap);
					nameFieldHash = matcher.group(2);
					logger1.info(nameTbFieldHash + "--->" + nameTableShema + "  nameTabledHashMap  " + nameTabledHashMap
							+ " " + nameFieldHash);
					if (nameFieldHash.equalsIgnoreCase(updataOrInsert)) {
						if (hashMapRowAndheader.get(nameTbFieldHash).equalsIgnoreCase(UPDATE)) {
							table.setUpdate(true);

						}
					}
					for (int fid = 0; fid < listField.size(); fid++) {
						logger1.info("add" + listField.get(fid).getName() + "\\\\\\\\ " + nameFieldHash);
						if (listField.get(fid).getName().equals(nameFieldHash)) {
							listField.get(fid).setValue(hashMapRowAndheader.get(nameTbFieldHash));
							logger1.info(listField.get(fid).getName() + " value "
									+ hashMapRowAndheader.get(nameTbFieldHash) + "?????????????????????//////");
						}
					}
				}

			}
		}

		return listNameTable;

	}

	/**
	 * method create set name table when field names format "table.field"
	 * 
	 * @param hashMapRowAndheader
	 *            HashMap where key name header and value in one row
	 * @return
	 */
	public Set<String> getSetNameTable(HashMap<String, String> hashMapRowAndheader) {
		Set<String> setNameTable = new HashSet<String>();
		Pattern p = Pattern.compile("(\\w+)\\.(\\w+$)");
		Set<String> keyNameTableValue = hashMapRowAndheader.keySet();
		Iterator<String> iteratNameT = keyNameTableValue.iterator();
		while (iteratNameT.hasNext()) {
			Matcher matcher = p.matcher(iteratNameT.next());
			matcher.find();
			String nameTable = matcher.group(1);
			setNameTable.add(nameTable);
		}
		return setNameTable;
	}

	/**
	 * substitute convert value variable
	 * 
	 * @param listSubstitute
	 * @param hashMapRowAndheader
	 * @param lookUpService
	 */
	@SuppressWarnings("unused")
	public void substitudeValueData(List<Substitute> listSubstitute, Map<String, String> hashMapRowAndheader,
			LookUpService lookUpService) {
		ArrayList<String> listResultConversionSubstitude = new ArrayList<String>();
		Substitute substitude = null;
		String variable = null;
		String variableValue = null;
		Value value = null;
		List<FieldFormat> fieldFormatList = null;
		FieldFormat fildFormat = null;
		String valueFormat = null;
		List<Map<String, String>> listLookUp = null;
		for (int i = 0; i < listSubstitute.size(); i++) {
			substitude = listSubstitute.get(i);
			variable = substitude.getVariable();/// variable
			logger1.info("variablelllllllllllllllllllllllllllllllllllllll" + variable);
			value = substitude.getValue();
			fieldFormatList = value.getFormat();
			listLookUp = value.getLookUp();
			for (int form = 0; form < fieldFormatList.size(); form++) {
				fildFormat = fieldFormatList.get(form);

				if (hashMapRowAndheader.get(fildFormat.getFieldName().trim()) != null) {
					logger1.info(
							"variablelllllllllllllllllllllllllllllllllllllll///////////" + fildFormat.getFieldName());
					valueFormat = conversionField.oneValueConversion(hashMapRowAndheader.get(fildFormat.getFieldName()),
							fildFormat);
				} else {
					for (int listM = 0; listM < listLookUp.size(); listM++) { // lookup
																				// table
						HashMap<String, String> mapLookUp = (HashMap<String, String>) listLookUp.get(listM);
						if (isHaveFiieldNameInLookUp(mapLookUp, fildFormat.getFieldName())) {
							valueFormat = conversionField.oneValueConversion(lookUpService.seletGetFieldbyID(mapLookUp),
									fildFormat);
							logger1.info("valueFormat " + lookUpService.seletGetFieldbyID(mapLookUp));
						}
					}
				}
				listResultConversionSubstitude.add(valueFormat);
			}

			String substitudeTargetValue = getTargetVariable(listResultConversionSubstitude, value.getConstant());

			hashMapRowAndheader.put(variable, substitudeTargetValue);
		}
	}

	public ConversionField getConversionField() {
		return conversionField;
	}

	public void setConversionField(ConversionField conversionField) {
		this.conversionField = conversionField;
	}

	public LookUpService getLookUpService() {
		return lookUpService;
	}

	public void setLookUpService(LookUpService lookUpService) {
		this.lookUpService = lookUpService;
	}

	/**
	 * return true or false if valueformat(trim,right,left mid) equals with
	 * lookUp (LOOKUP_TABLE.RESULT_COL_IN_TABLE)value
	 * 
	 * @param mapLookUp
	 *            lookUp in substitute
	 * @param fieldName
	 *            valueFormat (trim,right,left mid)
	 * @return true or false
	 */
	public boolean isHaveFiieldNameInLookUp(Map<String, String> mapLookUp, String fieldName) {
		String fieldSearch = mapLookUp.get(StaticVariablesXML.LOOKUP_TABLE) + "."
				+ mapLookUp.get(StaticVariablesXML.RESULT_COL_IN_TABLE);
		if (fieldSearch.equals(fieldName)) {
			return true;
		}
		return false;
	}

	/**
	 * contains word using constants
	 * 
	 * @param listResultConversionSubstitude
	 *            list world which need contains constant
	 * @param constant
	 * @return
	 */
	@SuppressWarnings("unused")
	public String getTargetVariable(List<String> listResultConversionSubstitude, Map<String, String> constant) {

		String targetVariable = "";

		Set<String> setConst = constant.keySet();
		Iterator<String> iteratorConst = setConst.iterator();
		int i = 0;

		while (iteratorConst.hasNext()) {

			if (!(i > listResultConversionSubstitude.size() - 1)) {
				for (; i < listResultConversionSubstitude.size(); i++) {
					if (i == 0) {
						targetVariable = listResultConversionSubstitude.get(i);
					} else {
						if (constant.size() > 1) {
							targetVariable = targetVariable + constant.get(iteratorConst.next())
									+ listResultConversionSubstitude.get(i);
						} else {
							targetVariable = targetVariable + constant.get(iteratorConst.next())
									+ listResultConversionSubstitude.get(i);
						}
					}
				}
			} else {
				targetVariable = targetVariable + constant.get(iteratorConst.next());
			}
		}

		return targetVariable;

	}

	/**
	 * add one row excel file in database in
	 * 
	 * @param listTable
	 */
	public void addTableINBase(List<Table> listTable, Map<String, Integer> listCellNumber, Source source, int row,
			Workbook excelBook) {
		Table root = null;
		Table tableSerch = null;
		int count = 0;
		List<Table> tableResult = new ArrayList<Table>();
		for (int r = 0; r < listTable.size(); r++) {
			tableSerch = listTable.get(r);
			if (tableSerch.isRoot()) {
				root = tableSerch;
			}
		}
		if (root != null) {

			addOneRowExcelInDatabase(root, listTable, tableResult, listCellNumber, source, row, excelBook, count);
		}

	}

	/**
	 * search table for foreign key and add data value in table
	 * 
	 * @param tableSerch
	 * @param listTable
	 * @param tableResult
	 */
	public int addOneRowExcelInDatabase(Table tableSerch, List<Table> listTable, List<Table> tableListResult,
			Map<String, Integer> listCellNumber, Source source, int row, Workbook excelBook, int count) {
		List<Field> listField = tableSerch.getListField();
		Field fieldSearchForeigenKey = null;
		for (int f = 0; f < listField.size(); f++) {
			fieldSearchForeigenKey = listField.get(f);
			if (fieldSearchForeigenKey.getNameTableForeignKey() != null) {/// 1
				if (isHaveTableInBase(tableListResult, fieldSearchForeigenKey.getNameTableForeignKey())) {
					logger1.info("tebleResult");
					addValueForeignKeyWithTableInsertBase(tableListResult, fieldSearchForeigenKey); //// 1
				} else {
					Table tableForeign = null;
					logger1.info("tebleResult not have ");
					for (int searchTable = 0; searchTable < listTable.size(); searchTable++) {
						tableForeign = listTable.get(searchTable);
						if (tableForeign.getNameTable()
								.equalsIgnoreCase(fieldSearchForeigenKey.getNameTableForeignKey())) {
							count = addOneRowExcelInDatabase(tableForeign, listTable, tableListResult, listCellNumber,
									source, row, excelBook, count);
							addValueForeignKeyWithTableInsertBase(tableListResult, fieldSearchForeigenKey);
						}
					}
				}
			}
		}
		try {
			if (!(tableSerch.isUpdate())) {
				tableService.addoneRowTable(tableSerch);
				tableService.selectoneRow(tableSerch);
			} else {
				tableService.updateOneRowTable(tableSerch);
			}
			writeResultInFileExcel.addCorrectResult(tableSerch, listCellNumber, source, row, excelBook, count);
			
		} catch (InvalidResultSetAccessException e) {
			writeResultInFileExcel.addErrorResult(tableSerch, listCellNumber, source, row, excelBook, e, count);
			throw new RuntimeException(e);
		} catch (DataAccessException e) {
			writeResultInFileExcel.addErrorResult(tableSerch, listCellNumber, source, row, excelBook, e, count);
			throw new RuntimeException(e);
		}
		count++;
		tableListResult.add(tableSerch);

		return count;

	}

	/**
	 * add value in field foreign key
	 * 
	 * @param tableResult
	 * @param fieldSearchForeigenKey
	 */
	private void addValueForeignKeyWithTableInsertBase(List<Table> tableResult, Field fieldSearchForeigenKey) {
		for (int res = 0; res < tableResult.size(); res++) {/// result table
			if (tableResult.get(res).getNameTable().equalsIgnoreCase(fieldSearchForeigenKey.getNameTableForeignKey())) {
				// tableService.selectoneRow(tableResult.get(res));
				String namePrimaryKey = tableService.getNameFieldPrimaryKay(tableResult.get(res));
				for (int fieldR = 0; fieldR < tableResult.get(res).getListField().size(); fieldR++) {
					if (namePrimaryKey.equalsIgnoreCase(tableResult.get(res).getListField().get(fieldR).getName())) {
						String resultForeignKey = tableResult.get(res).getListField().get(fieldR).getValue();
						logger1.info("resultForeignKey " + resultForeignKey);
						fieldSearchForeigenKey.setValue(resultForeignKey);
					}
				}
			}
		}
	}

	/**
	 * method return true if table in base
	 * 
	 * @param tableListResult
	 *            list table which have in base
	 * @param tableSerch
	 * @return
	 */
	public boolean isHaveTableInBase(List<Table> tableListResult, String nameTableForeignKey) {
		boolean haveTableInBase = false;
		for (int i = 0; i < tableListResult.size(); i++) {
			logger1.info("isHaveTableInBase" + tableListResult.get(i).getNameTable() + " " + nameTableForeignKey);
			if (tableListResult.get(i).getNameTable().equalsIgnoreCase(nameTableForeignKey)) {
				haveTableInBase = true;
			}
		}
		logger1.info("isHaveTableInBase" + haveTableInBase);
		return haveTableInBase;
	}

	public void getForeignKeyInTable(String nameForeignKey, List<Table> listTable, List<Table> tableResult) {

	}
}
