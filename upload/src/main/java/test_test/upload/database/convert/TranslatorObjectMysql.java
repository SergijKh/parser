package test_test.upload.database.convert;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import test_test.upload.parse.TestParseXml;

@Component
@Qualifier("TranslatorObjectMysql")
public class TranslatorObjectMysql implements Itranslator {
	private static final Logger logger1 = Logger
			.getLogger(TranslatorObjectMysql.class);

	/**
	 * empty constructor
	 */
	public TranslatorObjectMysql() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param typeField
	 *            type field
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unused")
	@Override
	public Object translator(String typeField, String value) {
		
		Object typeObject = null;
		if (value == null){
			
		}
		//logger1.info("valuemmmmmmmmmmmmmmmmmm" + (value));
		else if ((value != null)||(!(value.equals(" ")))) {

			if ((typeField.equalsIgnoreCase(TypeSQL.TINYTEXT))
					|| (typeField.equalsIgnoreCase(TypeSQL.TEXT))
					|| (typeField.equalsIgnoreCase(TypeSQL.MEDIUMTEXT))
					|| (typeField.equalsIgnoreCase(TypeSQL.MEDIUMTEXT))
					|| (typeField.equalsIgnoreCase(TypeSQL.LONGTEXT))
					|| (typeField.equalsIgnoreCase(TypeSQL.CHAR))
					|| ((typeField.equalsIgnoreCase(TypeSQL.VARCHAR)))) {
				typeObject = "" + value;
				logger1.info("typeObjectchar " + typeObject);

			}
			if ((typeField.equalsIgnoreCase(TypeSQL.BOOL))
					|| (typeField.equalsIgnoreCase(TypeSQL.BOOLEAN))
					|| (typeField.equalsIgnoreCase(TypeSQL.TINYINT))) {
				typeObject = Boolean.valueOf(value);

			}
			if ((typeField.equalsIgnoreCase(TypeSQL.SMALLINT))
					|| (typeField.equalsIgnoreCase(TypeSQL.MEDIUMINT))
					|| (typeField.equalsIgnoreCase(TypeSQL.INT))
					|| (typeField.equalsIgnoreCase(TypeSQL.INT_UNSIGNED))) {
				logger1.info("value" + value);
				typeObject = Integer.valueOf(value);

			}
			if ((typeField.equalsIgnoreCase(TypeSQL.BIGINT))) {
				typeObject = Long.valueOf(value);

			}
			if ((typeField.equalsIgnoreCase(TypeSQL.FLOAT))) {
				typeObject = Float.valueOf(value);

			}
			if ((typeField.equalsIgnoreCase(TypeSQL.DOUBLE))) {
				typeObject = Double.valueOf(value);

			}
			if ((typeField.equalsIgnoreCase(TypeSQL.DECIMAL))) {
				typeObject = new BigDecimal(value);

			}
			if ((typeField.equalsIgnoreCase(TypeSQL.DATE))) {
				//typeObject = new Date(Long.valueOf(value));
				 SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				 java.util.Date parsedDate;
					try {
						parsedDate = (Date) dateFormat.parse(value);
						
						typeObject = new Timestamp(parsedDate.getTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
					    logger1.info(e);
					}
					

			
			}
			if ((typeField.equalsIgnoreCase(TypeSQL.DATETIME))
					|| (typeField.equalsIgnoreCase(TypeSQL.TIMESTAMP))) {

			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date parsedDate;
				try {
					
					parsedDate = (Date) dateFormat.parse(value);
					  logger1.info("lllllllllllllllllllllllllllll"+ parsedDate.getTime());
					typeObject = new Timestamp(parsedDate.getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
				    logger1.info(e);
				}
				

			}
			if ((typeField.equalsIgnoreCase(TypeSQL.TIME))) {
				typeObject = new Time(Long.valueOf(value));

			}
			if ((typeField.equalsIgnoreCase(TypeSQL.YEAR))) {
				typeObject = new Time(Long.valueOf(value));

			}
		} else {

			typeObject = null;
		}
		return typeObject;
	}

}
