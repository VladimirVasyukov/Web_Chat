package com.epam.chat.datalayer;

import com.epam.chat.datalayer.db.MySqlDAOFactory;
import com.epam.chat.datalayer.xml.XMLDAOFactory;

/**
 * Type of db
 */
public enum DBType {

    XML {
        @Override
        public DAOFactory getDAOFactory() {
            return new XMLDAOFactory();
        }
    },

    MYSQL {
        @Override
        public DAOFactory getDAOFactory() {
            return new MySqlDAOFactory();
        }
    };

    /**
     * Get db type by name
     *
     * @param dbType type founded by name
     * @return database type
     */
    public static DBType getTypeByName(String dbType) {
        try {
            return DBType.valueOf(dbType.toUpperCase());
        } catch (Exception e) {
            throw new DBTypeException();
        }
    }


    public abstract DAOFactory getDAOFactory();

}
