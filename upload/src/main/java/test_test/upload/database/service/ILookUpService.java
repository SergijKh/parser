package test_test.upload.database.service;

import java.util.List;
import java.util.Map;

import test_test.upload.model.form.Table;

public interface ILookUpService {
	  //return value in search lookUp  in add in field 
		/**
		 * 
		 * @param mapLooKUplValue list Map LookUp 
		 * @param hashMapRowAndheader  map data where key field  and value value data in excel 
		 * 
		 */
		String  seletGetFieldbyID(Map<String, String> mapLooKUplValue);
}
