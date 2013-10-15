package fr.altis.dbutils.service.serializer;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import fr.altis.dbutils.model.schema.DBImage;
import fr.altis.dbutils.model.schema.Field;
import fr.altis.dbutils.model.schema.Table;


public class XStreamDBImageSerializerTest
{
    
    private DBImage dbImage;
    private XStreamDBImageSerializer serializer;

    @Before
    public void setUp() throws Exception
    {
        dbImage = new DBImage();
        serializer = new XStreamDBImageSerializer();
        
        List<Field> fields = Arrays.asList(new Field("Field1","NUMBER", 10)
                                         , new Field("Field2","VARCHAR", 255));
        
        Table table1 = new Table("Table1");
        Table table2 = new Table("Table2");
        
        table1.setFields(fields);
        table2.setFields(fields);
        
        List<Table> tables = Arrays.asList(table1, table2);
        
        dbImage.setTables(tables);
    }
    
    @Test
    public void shouldSerializeAndDeserializeTheSameObject() {
        String contentSerialized = serializer.serialize(dbImage);
        DBImage objectDeserialized = serializer.deserialize(contentSerialized);
        
        assertEquals(2, objectDeserialized.getTables().size());
        assertEquals("Table1", objectDeserialized.getTables().get(0).getName());
        assertEquals("Table2", objectDeserialized.getTables().get(1).getName());
        assertEquals("Field2", objectDeserialized.getTables().get(0).getFields().get(1).getName());
    }

}