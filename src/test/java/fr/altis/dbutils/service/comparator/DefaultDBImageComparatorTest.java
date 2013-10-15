package fr.altis.dbutils.service.comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import fr.altis.dbutils.model.check.Check;
import fr.altis.dbutils.model.check.CheckFactory;
import fr.altis.dbutils.model.check.Difference;
import fr.altis.dbutils.model.schema.DBImage;
import fr.altis.dbutils.model.schema.Field;
import fr.altis.dbutils.model.schema.Table;


public class DefaultDBImageComparatorTest
{
    
    private DBImage referenceDBImage;
    private DBImage testedDBImage;
    private DefaultDBImageComparator comparator = new DefaultDBImageComparator();
    private List<Field> fields;
    private Table table1;
    private Table table2;
    private CheckFactory checkFactory;

    @Before
    public void setUp() throws Exception
    {
        referenceDBImage = new DBImage();
        testedDBImage = new DBImage();
        
        fields = Arrays.asList(new Field("Field1","NUMBER", 10)
        , new Field("Field2","VARCHAR", 255));

        table1 = new Table("Table1");
        table2 = new Table("Table2");
        
        table1.setFields(fields);
        table2.setFields(fields);
        
        referenceDBImage.setTables(Arrays.asList(table1, table2));
        testedDBImage.setTables(Arrays.asList(table1));
        
        checkFactory = new CheckFactory(referenceDBImage, testedDBImage);
    }
    
    @Test
    public void shouldReturnNoDifferencesIfNoChecks() {
        List<Check> checks = new ArrayList<Check>();
        List<Difference> differences = comparator.compare(referenceDBImage, testedDBImage, checks);
        assertEquals(0, differences.size());
    }
    
    @Test
    public void shouldReturnOneDifferenceIfCheckMissingTables() {
        List<Check> checks = Arrays.asList(checkFactory.getCheck(Check.MISSING_TABLES));
        List<Difference> differences = comparator.compare(referenceDBImage, testedDBImage, checks);
        
        assertEquals(1, differences.size());
        assertEquals("Table2", differences.get(0).getObject());
        assertEquals(Check.MISSING_TABLES, differences.get(0).getCheckName());
    }
    
    @Test
    public void shouldReturnOneDifferenceIfCheckNoLongerExistsTables() {
        referenceDBImage.setTables(Arrays.asList(table2));
        testedDBImage.setTables(Arrays.asList(table1, table2));
        
        List<Check> checks = Arrays.asList(checkFactory.getCheck(Check.NO_LONGER_EXISTS_TABLES));
        List<Difference> differences = comparator.compare(referenceDBImage, testedDBImage, checks);
        
        assertEquals(1, differences.size());
        assertEquals("Table1", differences.get(0).getObject());
        assertEquals(Check.NO_LONGER_EXISTS_TABLES, differences.get(0).getCheckName());
    }
    
    @Test
    public void shouldReturnOneDifferenceIfCheckMissingFields() {
        Table table2WithMissingField = new Table("Table2");
        table2WithMissingField.setFields(Arrays.asList(new Field("Field1","NUMBER", 10)));
        
        testedDBImage.setTables(Arrays.asList(table1, table2WithMissingField));
        
        List<Check> checks = Arrays.asList(checkFactory.getCheck(Check.MISSING_FIELDS));
        List<Difference> differences = comparator.compare(referenceDBImage, testedDBImage, checks);
        
        assertEquals(1, differences.size());
        assertEquals("Table2/Field2", differences.get(0).getObject());
        assertEquals(Check.MISSING_FIELDS, differences.get(0).getCheckName());
    }
    
    @Test
    public void shouldReturnOneDifferenceIfCheckNoLongerExistsFields() {
        Table table2WithMissingField = new Table("Table2");
        table2WithMissingField.setFields(Arrays.asList(new Field("Field1","NUMBER", 10)));
        
        referenceDBImage.setTables(Arrays.asList(table1, table2WithMissingField));
        testedDBImage.setTables(Arrays.asList(table1, table2));
        
        List<Check> checks = Arrays.asList(checkFactory.getCheck(Check.NO_LONGER_EXISTS_FIELDS));
        List<Difference> differences = comparator.compare(referenceDBImage, testedDBImage, checks);
        
        assertEquals(1, differences.size());
        assertEquals("Table2/Field2", differences.get(0).getObject());
        assertEquals(Check.NO_LONGER_EXISTS_FIELDS, differences.get(0).getCheckName());
    }
    
    @Test
    public void shouldReturnOneDifferenceIfCheckDifferentTypesFields() {
        Table table2WithDifferentField = new Table("Table2");
        
        table2WithDifferentField.setFields(Arrays.asList(new Field("Field1","NUMBER", 10)
                                                     , new Field("Field2","VARCHAR", 254)));
        
        referenceDBImage.setTables(Arrays.asList(table1, table2));
        testedDBImage.setTables(Arrays.asList(table1, table2WithDifferentField));
        
        List<Check> checks = Arrays.asList(checkFactory.getCheck(Check.DIFFERENT_TYPES_FIELDS));
        List<Difference> differences = comparator.compare(referenceDBImage, testedDBImage, checks);
        
        assertEquals(1, differences.size());
        assertEquals("Table2/Field2 - VARCHAR/255 - VARCHAR/254", differences.get(0).getObject());
        assertEquals(Check.DIFFERENT_TYPES_FIELDS, differences.get(0).getCheckName());
    
    }

}