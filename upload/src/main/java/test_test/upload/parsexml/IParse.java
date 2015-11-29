package test_test.upload.parsexml;

import java.util.List;

import org.w3c.dom.Document;

import test_test.upload.model.parse.MapSource;
import test_test.upload.model.parse.Schema;
import test_test.upload.model.parse.Source;

/**
 * 
 * @author Sergey
 * parse  file  xml and return main block (Schema,Source,MapSource)
 */
public interface IParse {
	/**
	 * 
	 * @return list Schema
	 *  @see Schema
	 */
	public List<Schema> getSxema(Document doc);
	/**
	 * 
	 * @return list Source
	 * @see Source
	 */
	public List <Source> getSource(Document doc);
	/**
	 * 
	 * @return list MapSource
	 * @see MapSource
	 */
	public List <MapSource> getMapSource(Document doc);
	public Document initDomParser();

}
