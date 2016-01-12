package test_test.upload.database.convert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import test_test.upload.model.form.Field;
import test_test.upload.model.form.Table;

@Component
@Qualifier("ConvertDataFieldTypeMysql")
public class ConvertDataRDBMSFieldType implements IConvertDataRDBMSFieldType {
	private static final Logger logger1 = Logger
			.getLogger(ConvertDataRDBMSFieldType.class);
	@Autowired
	@Qualifier("TranslatorObjectMysql")
	TranslatorObjectMysql translatorMysql;

	/**
	 * empty constructor
	 */

	public ConvertDataRDBMSFieldType() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object[] convertDataTypeInBase(Table table, JdbcTemplate jdbcTemplate) {
		Object[] object = null;
		Field field = null;
		String keyF = null;// key name field database in map
		String typeField = null; // type field in database
		Object typeObject = null; // object field translator
		DataSource data = jdbcTemplate.getDataSource();
		DatabaseMetaData metaData = null;
		ArrayList<String> listTablist = null;
		Map<String, Map<String, String>> mapTable = null;
		try {
			metaData = data.getConnection().getMetaData();
			listTablist = getTablesMetadata(metaData);
			mapTable = getColumnsMetadata(listTablist, metaData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger1.error(e);
		}
		List<Field> listField = table.getListField();
		logger1.info("listField............"+(listField.size()));
		Map<String, String> mapField = (mapTable.get((table.getNameTable()).toLowerCase()));
		Set<String> setKeyField = mapField.keySet();
     	Iterator<String> iteratorKey = null ;
		object = new Object[listField.size()];
		for (int i = 0; i < listField.size(); i++) {
			field = listField.get(i);
			iteratorKey = setKeyField.iterator();
			logger1.info("field value  //////////////////"+field.getValue()+" field name   //////////////////"+field.getName());
			while (iteratorKey.hasNext()) {
				keyF = iteratorKey.next();
				logger1.info(field.getName()+ "keyF //////////////////"+keyF);
				if (field.getName().equalsIgnoreCase(keyF)) {
					
					typeField = mapField.get(keyF);
					logger1.info(keyF + "  typeField "+typeField+" "+field.getValue()+"/////////////////////////////");
					typeObject = translatorMysql.translator(typeField,
							field.getValue());
					logger1.info("type Object "+typeObject);
					object[i] = typeObject;
				}
			}
			
		}
		return object;
	}

	
	/**
	 * translator one value in column  
	 * @param nameTable name table 
	 * @param nameColumn  name column in database
	 * @param value value in database
	 * @return object value field
	 */
	 public Object  getTypeOneFieldIntable(String nameTable,String nameColumn, String value){
		
		 
		 
		 return null;
		 
	 }
	/**
	 * method return type field
	 * 
	 * @param tables
	 *            list tables in databases
	 * @param metaData
	 *            DatabaseMetaData
	 * @return Map where key name table and value is Map where key name column
	 *         and value type name;
	 * @throws SQLException
	 */
	public Map<String, Map<String, String>> getColumnsMetadata(
			ArrayList<String> tables, DatabaseMetaData metaData)
			throws SQLException {
		ResultSet rs = null;
		Map<String, Map<String, String>> listMapTable = new HashMap<String, Map<String, String>>();
		HashMap<String, String> hashMapFields = null;
		// Print the columns properties of the actual table
		for (String actualTable : tables) {
			hashMapFields = new HashMap<String, String>();
			rs = metaData.getColumns(null, null, actualTable, null);
			logger1.info(actualTable.toUpperCase());
			while (rs.next()) {
				hashMapFields.put(rs.getString("COLUMN_NAME"),
						rs.getString("TYPE_NAME"));
				
			}
			logger1.info("actualTable "+ actualTable);
			listMapTable.put(actualTable, hashMapFields);
		}
		return listMapTable;

	}

	/**
	 * method return list name table in database
	 * 
	 * @param metaData
	 * @return list table in database
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getTablesMetadata(DatabaseMetaData metaData)
			throws SQLException {
		String table[] = { "TABLE" };
		ResultSet rs = null;
		ArrayList<String> tables = null;
		// receive the Type of the object in a String array.
		rs = metaData.getTables(null, null, null, table);
		tables = new ArrayList<String>();
		while (rs.next()) {
			tables.add(rs.getString("TABLE_NAME"));
		}
		return tables;

	}

	/**
	 * method return name field  primary key
	 * @param table table in base
	 * @param jdbcTemplate
	 * @return
	 */
	@Override
	 public String getNameFielDPrimaryAutoincrement (Table table, JdbcTemplate jdbcTemplate){
		 String pkey = null;
		   DataSource data = jdbcTemplate.getDataSource();
			DatabaseMetaData metaData = null;
			ArrayList<String> listTablist = null;
			ResultSet rs = null;
			
			try {
				metaData = data.getConnection().getMetaData();
				
				listTablist = getTablesMetadata(metaData);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger1.error(e);
			}
			for (int t = 0 ; t < listTablist.size(); t++){
				
				if (table.getNameTable().equalsIgnoreCase(listTablist.get(t))){
					try {
						 logger1.info("////////////////////////////////"+ table.getNameTable());
						rs = metaData.getPrimaryKeys( "" , "" , listTablist.get(t) );
						 while( rs.next( ) ) 
							{    
							  pkey = rs.getString("COLUMN_NAME");
							  logger1.info("primary key = " + pkey + " table.getNameTable() "+ table.getNameTable() );
							}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
			}
		 
		 
		 return pkey;
	 }

	public Object[]  convertDataTypeUpdateBase(Table table, JdbcTemplate jdbcTemplate, String primaryKay){
		Object[] object = null;
		Object  primary = null;
		Field field = null;
		String keyF = null;// key name field database in map
		String typeField = null; // type field in database
		Object typeObject = null; // object field translator
		DataSource data = jdbcTemplate.getDataSource();
		DatabaseMetaData metaData = null;
		ArrayList<String> listTablist = null;
		Map<String, Map<String, String>> mapTable = null;
		try {
			metaData = data.getConnection().getMetaData();
			listTablist = getTablesMetadata(metaData);
			mapTable = getColumnsMetadata(listTablist, metaData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger1.error(e);
		}
		List<Field> listField = table.getListField();
		logger1.info("listField............"+(listField.size()));
		Map<String, String> mapField = (mapTable.get((table.getNameTable()).toLowerCase()));
		Set<String> setKeyField = mapField.keySet();
     	Iterator<String> iteratorKey = null ;
		object = new Object[listField.size()];
		 int count = 0 ;
		for (int i = 0; i < listField.size(); i++) {
			field = listField.get(i);
			iteratorKey = setKeyField.iterator();
			logger1.info("field value  //////////////////"+field.getValue()+" field name   //////////////////"+field.getName());
			while (iteratorKey.hasNext()) {
				keyF = iteratorKey.next();
				logger1.info(field.getName()+"keyF //////////////////"+keyF);
				if (field.getName().equalsIgnoreCase(keyF)) {
					
					typeField = mapField.get(keyF);
					logger1.info(keyF + "  typeField "+typeField);
					typeObject = translatorMysql.translator(typeField,
							field.getValue());
					logger1.info("type Object "+typeObject);
					if (field.getName().equals(primaryKay)){
						primary = typeObject;
					}else{
					  object[count++] = typeObject;
					}
				}
			}
			
		}
		  object[object.length-1] = primary;
		return object;
		
	}
	
}
