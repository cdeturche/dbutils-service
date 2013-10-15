package fr.altis.dbutils.service.comparator;

import java.util.ArrayList;
import java.util.List;

import fr.altis.dbutils.model.check.Check;
import fr.altis.dbutils.model.check.Difference;
import fr.altis.dbutils.model.schema.DBImage;

public class DefaultDBImageComparator implements DBImageComparator
{

	@Override
    public List<Difference> compare(DBImage referenceDBImage, DBImage testedDBImage, List<Check> checks)
    {
        List<Difference> differences = new ArrayList<Difference>();
        
        for(Check check : checks) {
            differences.addAll(check.check());
        }
        
        return differences;
    }

}
