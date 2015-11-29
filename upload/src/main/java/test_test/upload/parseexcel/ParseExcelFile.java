package test_test.upload.parseexcel;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import test_test.upload.model.map.conversion.ConversionField;
import test_test.upload.model.parse.MapSource;
import test_test.upload.model.parse.Source;
import test_test.upload.parsexml.IParse;
import test_test.upload.parsexml.ParseXml;

@Component
public class ParseExcelFile implements IParseExcel {
	private static final Logger logger1 = Logger.getLogger(ParseExcelFile.class);
	@Autowired
	ConversionField conversionField;
	
	public ParseExcelFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parseExcelFile(IParse parseXml) {
	     Document doc = (Document) parseXml.initDomParser();
	    List <Source> source  =  parseXml.getSource(doc);
	    List<MapSource> mapSourceList = parseXml.getMapSource(doc);
	    MapSource mapSource = null;
	    for(int sour = 0; sour < source.size(); sour++){
              for (int map = 0 ; map <  mapSourceList.size();map++){
            	  if (source.get(sour).getId().equals(mapSourceList.get(map).getIdSource())){
            		  mapSource = mapSourceList.get(map);
            	  }
            	  
              }
               
		    File fileXml = new File (source.get(sour).getPathInputFile());
		    Sheet sheet = null;
			try {
				sheet = readFromExcel(source.get(sour).getPathInputFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger1.error(e);
			}
		    ArrayList<String> listHeader = (ArrayList<String>) getHeaderList( sheet,source.get(sour).getHeaderRows());
		    Row rowData = null;
		    HashMap<String,String> hashMapRowAndheader = new HashMap<String,String>();//where key header value data row
	        	for (int i = source.get(sour).getFieldNameRowNumer()+1; i < sheet.getLastRowNum(); i++){
	              rowData = sheet.getRow(i);
	              getOneRow(rowData, listHeader, hashMapRowAndheader);
	              conversionField.allMapConversion( hashMapRowAndheader, mapSource.getConversion().getFormat());

	        
	        }
		
	    }

	}
	

/**
 * add HasHmap  header  and  value 
 * @param row one row in file 
 * @param header list header in file 
 * @param hashmap HashMap where key header value one field in row 
 */
@SuppressWarnings("unchecked")
public void getOneRow(Row row,List<String> header, HashMap <String,String>hashmap){
	
	  Iterator<Cell> cells =  row.iterator();
	  int count = 0;
     for (int cn = 0; cn < row.getLastCellNum(); cn++){
    	  Cell cell = row.getCell(cn, Row.RETURN_BLANK_AS_NULL);
           if (cell==null){
        	   hashmap.put(header.get(count++),""); 
           }else{
          int cellType = cell.getCellType();
          switch (cellType) {
              case Cell.CELL_TYPE_STRING:
            	  hashmap.put(header.get(count++),cell.getStringCellValue());
                  break;
              case Cell.CELL_TYPE_NUMERIC:
            	  hashmap.put(header.get(count++),""+cell.getNumericCellValue());
                  break;
              case  Cell.CELL_TYPE_BLANK:
            	  hashmap.put(header.get(count++),"");
                  break; 
              default:
                 logger1.error("not type field");
                  break;
          }
      }
     }     
     
}
	/**
	 * method return  list header  
	 * @param sheet
	 * @param headerRow  number row  where find header 
	 * @return list header  in file 
	 */
	public List<String> getHeaderList(Sheet sheet,int headerRow){
		ArrayList <String> listHeader = new ArrayList<String>();
		Row rowHeader = sheet.getRow(headerRow);
		Iterator  iteratorHeader= rowHeader.iterator();
		while(iteratorHeader.hasNext()){
			Cell cell =   (Cell) iteratorHeader.next();
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
 * method return Sheet  depending on the extension
 * @param file
 * @return
 * @throws IOException
 */
	public Sheet readFromExcel(String file) throws IOException{
	
	if (getExstension( file).equals("xls")){
		Workbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
		return myExcelBook.getSheetAt(0);    
	}else if(getExstension( file).equals("xlsx")){
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		return workbook.getSheetAt(0);
	}else{
		logger1.error("the file extension is not supported");
		return null;
	}
	
   }
	
	/**
	 * method return extension file  
	 * @param file
	 * @return
	 */
public   String getExstension(String file){
	Pattern p = Pattern.compile("\\.(\\w+$)");
      Matcher  matcher = p.matcher(file);
        matcher.find();
       return  matcher.group(1);   
}
}