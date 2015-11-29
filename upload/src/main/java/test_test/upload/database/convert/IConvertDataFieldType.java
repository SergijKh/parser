package test_test.upload.database.convert;

import org.springframework.jdbc.core.JdbcTemplate;

import test_test.upload.model.form.Table;

public interface IConvertDataFieldType {
	
	public void convertDataTypeInBase(Table table , JdbcTemplate jdbcTemplate);

}
