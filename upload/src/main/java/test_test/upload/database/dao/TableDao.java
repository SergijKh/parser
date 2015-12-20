package test_test.upload.database.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import test_test.upload.database.convert.ConvertDataRDBMSFieldType;
import test_test.upload.database.convert.IConvertDataRDBMSFieldType;
import test_test.upload.model.form.Table;
@Repository
public class TableDao implements ITableDao {
	private static final Logger logger1 = Logger
			.getLogger(TableDao.class);
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
@Qualifier("ConvertDataFieldTypeMysql")
IConvertDataRDBMSFieldType convert;
	
@Transactional
@Override
	public int addoneRowTable(Table table) {
		
	String sql = "insert into "+ table.getNameTable().toLowerCase() + " (";
	StringBuilder stringSqNameField  = new StringBuilder();
	StringBuilder stringSqValue  = new StringBuilder(" values(");
	StringBuilder stringSql  = new StringBuilder();
	 for (int i = 0 ; i < table.getListField().size(); i ++){
		 if (i==table.getListField().size()-1){
			 stringSqNameField.append(table.getListField().get(i).getName()+")");
			 stringSql.append("?)");
			 logger1.info("name field "+ table.getListField().get(i).getName());
		 }else {
			 logger1.info("name field "+ table.getListField().get(i).getName());
			 stringSqNameField.append(table.getListField().get(i).getName()+",");
			 stringSql.append("?, ");
		 }
	 }
	 sql  = sql +stringSqNameField.toString()+stringSqValue.toString()+stringSql.toString();
	
	Object [] sd = convert.convertDataTypeInBase(table, jdbcTemplate);
	 logger1.info("sql "+ sql);
	 for (int i = 0; i < sd.length;  i++){
	 logger1.info("Object [] "+ sd[i] );
	 }
	int id = jdbcTemplate.update(sql , sd);
		//jdbcTemplate.;
		return id ;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public void selectOneLastRow(Table table ) {
		String primaryKay = getNameFieldPrimaryKay( table);
		logger1.info("primaryKay ////"+primaryKay);
		String sqlId= "SELECT Max("+primaryKay+") FROM "+ table.getNameTable();
		int id = jdbcTemplate.queryForObject(sqlId, Integer.class);
		logger1.info("id ////"+id);
		String sqlLastRow = "SELECT * from  "+ table.getNameTable()+ " where "+primaryKay +"="+id;
		 List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlLastRow );
		 for (Map<String, Object> row : rows) {
	         for ( int i = 0; i < table.getListField().size(); i++){
	        	 table.getListField().get(i).setValue((String)(""+row.get( table.getListField().get(i).getName())));
	        	
	         }
	        }
	
	}
	
	
	/**
	 * return name field primer key
	 * @param table
	 * @return
	 */
	public  String  getNameFieldPrimaryKay(Table table){
		return 	convert.getNameFielDPrimaryAutoincrement(table, jdbcTemplate);	
	}
 /**
  * return JdbcTemplate
  * @return 
  */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
/**
 * add new JdbcTemplate
 * @param jdbcTemplate
 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
/**
 * retur IConvertDataRDBMSFieldType
 * @return
 */
	public IConvertDataRDBMSFieldType getConvertMysql() {
		return convert;
	}
/**
 * add new IConvertDataRDBMSFieldType
 * @param convertMysql
 */
	public void setConvertMysql(IConvertDataRDBMSFieldType convertMysql) {
		this.convert = convertMysql;
	}

}
