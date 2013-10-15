package fr.altis.dbutils.service.serializer;

import fr.altis.dbutils.model.schema.DBImage;

public interface DBImageSerializer
{

    DBImage deserialize(String content);
    
    String serialize(DBImage dbImage);
    
}
