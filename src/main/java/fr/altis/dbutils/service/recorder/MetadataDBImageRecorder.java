package fr.altis.dbutils.service.recorder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.altis.dbutils.model.destination.Destination;
import fr.altis.dbutils.model.schema.DBImage;
import fr.altis.dbutils.model.schema.Field;
import fr.altis.dbutils.model.schema.Sequence;
import fr.altis.dbutils.model.schema.Synonym;
import fr.altis.dbutils.model.schema.Table;

public class MetadataDBImageRecorder implements DBImageRecorder
{
    private final Destination destination;

    private Connection connection;

    public MetadataDBImageRecorder(Destination destination)
    {
        this.destination = destination;
    }

    @Override
    public DBImage buildImage() throws SQLException
    {
        this.connection = destination.getConnection();

        DBImage image = new DBImage();

        image.setTables(getTables());
        image.setSynonyms(getSynonyms());
        image.setSequences(getSequences());

        this.connection.close();

        return image;
    }

    private List<Sequence> getSequences()
    {
        return new ArrayList<Sequence>();
    }

    private List<Synonym> getSynonyms()
    {
        return new ArrayList<Synonym>();
    }

    private List<Table> getTables() throws SQLException
    {
        List<Table> tables = new ArrayList<Table>();

        DatabaseMetaData metadata = connection.getMetaData();
        
        if (metadata != null)
        {
            ResultSet resultSetTables = metadata.getTables("JADEMP", "PUBLIC", null, new String[] { "TABLE" });

            while (resultSetTables.next())
            {
                String tableName = resultSetTables.getString("TABLE_NAME");
                Table table = new Table(tableName);

                table.setFields(getFields(table.getName()));

                tables.add(table);
            }

            resultSetTables.close();
        }

        return tables;
    }

    private List<Field> getFields(String tableName) throws SQLException
    {
        List<Field> fields = new ArrayList<Field>();

        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet resultSetFields = metadata.getColumns("JADEMP", "PUBLIC", tableName, null);
        
        while (resultSetFields.next())
        {
            String fieldName = resultSetFields.getString("COLUMN_NAME");
            String fieldType = resultSetFields.getString("TYPE_NAME");
            String fieldSize = resultSetFields.getString("COLUMN_SIZE");
            
            fields.add(new Field(fieldName, fieldType, Integer.parseInt(fieldSize)));
        }
        
        resultSetFields.close();
        
        return fields;
    }

}
