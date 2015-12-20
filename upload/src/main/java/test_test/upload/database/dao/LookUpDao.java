package test_test.upload.database.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import test_test.upload.model.form.Field;
import test_test.upload.model.form.Table;
import test_test.upload.model.staticvariablesxmlfile.StaticVariablesXML;
@Repository
public class LookUpDao implements ILookUpDao {
	
  @Autowired
  JdbcTemplate jdbcTemplate;
	
  
  @Override
	@Transactional
	public String  seletGetFieldbyID(Map<String, String> mapLookUpValue) {	
      String sql  = "Select "+ mapLookUpValue.get(StaticVariablesXML.RESULT_COL_IN_TABLE) +" from "+ mapLookUpValue.get( StaticVariablesXML.LOOKUP_TABLE) +" where "+mapLookUpValue.get(StaticVariablesXML.LOOKUP_COL_IN_TABLE ) + "="+ mapLookUpValue.get(StaticVariablesXML.LOOKUP_VARIABLE) ;
      String value = ""+(String)jdbcTemplate.queryForObject(sql, String.class);
	 return  value;
	}	
}
