package fr.altis.dbutils.service.recorder;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import fr.altis.dbutils.model.destination.Destination;
import fr.altis.dbutils.model.destination.DriverManagerDestination;
import fr.altis.dbutils.model.schema.DBImage;
import fr.altis.dbutils.model.schema.Table;

public class MetadataDBImageRecorderTest
{

    private Destination destination;

    private DBImageRecorder dbImageService;

    @Before
    public void setUp() throws Exception
    {
        destination = new DriverManagerDestination("jdbc:h2:~/jademp", "sa", "");
        dbImageService = new MetadataDBImageRecorder(destination);
    }

    @Test
    public void shouldGetTables() throws SQLException
    {
        DBImage dbImage = dbImageService.buildImage();
        assertTrue(dbImage.getTables().size() > 0);
    }

    @Test
    public void shouldGetTablesWithFields() throws SQLException
    {
        DBImage dbImage = dbImageService.buildImage();
        for (Table table : dbImage.getTables())
        {	System.out.println(table.getName());
            assertTrue(table.getFields().size() > 0);
        }
    }

}
