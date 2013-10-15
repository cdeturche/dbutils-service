package fr.altis.dbutils.service.recorder;

import java.sql.SQLException;

import fr.altis.dbutils.model.schema.DBImage;

public interface DBImageRecorder
{
    /**
     * Builds a DBImage
     * @return DBImage
     */
    DBImage buildImage() throws SQLException;

}
