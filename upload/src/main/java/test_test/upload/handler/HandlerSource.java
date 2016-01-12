package test_test.upload.handler;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import test_test.upload.model.parse.Source;

public class HandlerSource extends DefaultHandler {
	public static final String FORMAT = "format";
	public static final String HEADER_ROWS = "header_rows";
	public static final String FIELD_NAMES_ROW_NUMER = "field_names_row_numer";
	public static final String FIELD_NAMES_FORMAT = "field_names_format";
	public static final String UPDATE_MODE_FIELD_NAME = "update_mode_field_name";
	public static final String PATH = "path";
	public static final String ERROR_COLUM = "error_column";

	private Source newSources = null;
	private String thisElement = "";
	private String inputFormat = "";

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		thisElement = qName;
		if (thisElement.equals("source")) {
			newSources = new Source();
			int length = atts.getLength();
			for (int i = 0; i < length; i++) {
				String id = atts.getValue(i);
				newSources.setId(id);
			}
		}
		if (thisElement.equals("infile")) {
			int leng = atts.getLength();
			for (int i = 0; i < leng; i++) {
				if (atts.getQName(i).equals(FORMAT)) {
					inputFormat = atts.getValue(i);
					newSources.setInputFormat(inputFormat);
				}
				if (atts.getQName(i).equals(HEADER_ROWS)) {
					inputFormat = atts.getValue(i);
					newSources.setHeaderRows(Integer.parseInt(inputFormat));
				}
				if (atts.getQName(i).equals(FIELD_NAMES_ROW_NUMER)) {
					inputFormat = atts.getValue(i);
					newSources.setFieldNameRowNumer(Integer
							.parseInt(inputFormat));
				}
				if (atts.getQName(i).equals(UPDATE_MODE_FIELD_NAME)) {
					inputFormat = atts.getValue(i);
					newSources.setUpdate_mode_field_name(inputFormat);
				}
				if (atts.getQName(i).equals(FIELD_NAMES_FORMAT)) {
					inputFormat = atts.getValue(i);
					newSources.setField_names_format(inputFormat);
				}
				if (atts.getQName(i).equals(PATH)) {
					inputFormat = atts.getValue(i);
					newSources.setPathInputFile(inputFormat);
				}
			}
		}
			if (thisElement.equals("outfile")) {
				int lengOut = atts.getLength();
				for (int i = 0; i < lengOut; i++) {
					if (atts.getQName(i).equals(FORMAT)) {
						inputFormat = atts.getValue(i);
						newSources.setAutputFormat(inputFormat);
					}
					if (atts.getQName(i).equals(ERROR_COLUM)) {
						inputFormat = atts.getValue(i);
						newSources.setError_column(inputFormat);
					}
					
					if (atts.getQName(i).equals(PATH)) {
						inputFormat = atts.getValue(i);
						newSources.setPathOutFile(inputFormat);
					}
				}
			}
		}

	

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		thisElement = "";
	}

	/**
	 * @return the newSources
	 */
	public Source getNewSources() {
		return newSources;
	}

	/**
	 * @param newSources the newSources to set
	 */
	public void setNewSources(Source newSources) {
		this.newSources = newSources;
	}

}
