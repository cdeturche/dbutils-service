package fr.altis.dbutils.service.comparator;

import java.util.List;

import fr.altis.dbutils.model.check.Check;
import fr.altis.dbutils.model.check.Difference;
import fr.altis.dbutils.model.schema.DBImage;

/**
 * Interface avec les opérations de comparaison
 * @author Kelem
 *
 */
public interface DBImageComparator
{
    
    List<Difference> compare(DBImage referenceDBImage, DBImage testedDBImage, List<Check> checks);

}
