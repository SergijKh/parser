package test_test.upload.database.convert;

import org.springframework.jdbc.core.JdbcTemplate;

import test_test.upload.model.form.Table;

public interface IConvertDataRDBMSFieldType {
	/**
	 *  convert Data type in table
	 * @param table
	 * @param jdbcTemplate
	 * @return
	 */
	public Object[] convertDataTypeInBase(Table table , JdbcTemplate jdbcTemplate);
	/**
	 * method return name field  primary key
	 * @param table table in base
	 * @param jdbcTemplate
	 * @return
	 */
	 public String getNameFielDPrimaryAutoincrement(Table table, JdbcTemplate jdbcTemplate);
}
