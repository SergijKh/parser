package test_test.upload.parse;

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
	 * @return Schema 
	 */
	public  Schema getSxema();
	/**
	 * 
	 * @return Source
	 */
	public  Source getSource();
	/**
	 * 
	 * @return MapSource
	 */
	public MapSource getMapSource();

}
