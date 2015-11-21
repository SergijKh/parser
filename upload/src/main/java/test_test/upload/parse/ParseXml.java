package test_test.upload.parse;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import test_test.upload.handler.HandlerSource;
import test_test.upload.model.parse.MapSource;
import test_test.upload.model.parse.Schema;
import test_test.upload.model.parse.Source;
@Component
public class ParseXml implements IParse{

  private File fileXml ;
  private SAXParser parser;

/**
 * empty constructor 
 */
public ParseXml() {
	super();
	
}

	/**
 * @param fileXml
 */
public ParseXml(File fileXml) {
	this.fileXml = fileXml;
	SAXParserFactory factory = SAXParserFactory.newInstance();
	   factory.setValidating(false);
	    try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}

	/**
 * @return the fileXml
 */
public File getFileXml() {
	return fileXml;
}

/**
 * @param fileXml the fileXml to set
 * @throws SAXException 
 * @throws ParserConfigurationException 
 */
public void setFileXml(File fileXml) throws ParserConfigurationException, SAXException {
	this.fileXml = fileXml;
	 
}

	@Override
	public Source getSource() {
		HandlerSource handlerSource = new HandlerSource();
		try {
			parser.parse(fileXml,handlerSource );
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return handlerSource.getNewSources();
	}

	@Override
	public MapSource getMapSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Schema getSxema() {
		// TODO Auto-generated method stub
		return null;
	}

}
