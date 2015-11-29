package test_test.upload.parseexcel;




import java.io.File;
import java.util.List;

import org.w3c.dom.Document;

import test_test.upload.model.parse.Source;
import test_test.upload.parsexml.IParse;

public class ParseExcelFile implements IParseExcel {

	@Override
	public void parseExcelFile(IParse parseXml) {
	     Document doc = (Document) parseXml.initDomParser();
	    List <Source> source  =  parseXml.getSource(doc);
	    for(int sour = 0; sour < source.size(); sour++){
		    File fileXml = new File (source.get(sour).getPathInputFile());
		
		
	    }

	}

}
