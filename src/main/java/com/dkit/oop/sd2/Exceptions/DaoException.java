package com.dkit.oop.sd2.Exceptions;

import java.sql.SQLException;

public class DaoException extends SQLException
{
    public DaoException()
    {
        // not used
    }

    public DaoException(String aMessage)
    {
        super(aMessage);
    }
}
