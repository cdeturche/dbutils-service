package fr.altis.dbutils.service.serializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import fr.altis.dbutils.model.schema.DBImage;
import fr.altis.dbutils.model.schema.Field;
import fr.altis.dbutils.model.schema.Table;

public class XStreamDBImageSerializer implements DBImageSerializer {

	private final XStream xstream;

	public XStreamDBImageSerializer() {
		xstream = new XStream(new DomDriver());

		xstream.alias("field", Field.class);
		xstream.alias("table", Table.class);
		xstream.alias("dbImage", DBImage.class);
	}

	@Override
	public DBImage deserialize(String content) {
		return (DBImage) xstream.fromXML(content);
	}

	@Override
	public String serialize(DBImage dbImage) {
		return xstream.toXML(dbImage);
	}

}
