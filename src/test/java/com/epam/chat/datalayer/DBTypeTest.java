package com.epam.chat.datalayer;

import com.epam.chat.datalayer.db.MySqlDAOFactory;
import com.epam.chat.datalayer.xml.XMLDAOFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertTrue;

@RunWith(JUnit4.class)
public class DBTypeTest {

    @Test
    public void getTypeByName_whenInputParamIsXML_thenReturnXMLDAOFactory() {
        assertTrue(DBType.getTypeByName("XML").getDAOFactory() instanceof XMLDAOFactory);
    }

    @Test
    public void getTypeByName_whenInputParamIsMYSQL_thenReturnMYSQLDAOFactory() {
        assertTrue(DBType.getTypeByName("MySQL").getDAOFactory() instanceof MySqlDAOFactory);
    }

    @Test(expected = DBTypeException.class)
    public void getTypeByName_whenInputParamIsNull_thenThrowDBTypeException() {
        DBType.getTypeByName(null).getDAOFactory();
    }

    @Test(expected = DBTypeException.class)
    public void getTypeByName_whenInputParamIsUnknownString_thenThrowDBTypeException() {
        DBType.getTypeByName("Unknown").getDAOFactory();
    }


}
