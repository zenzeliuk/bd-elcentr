package ua.com.elcentr;


import ua.com.elcentr.dao.CustomerDAO;

import java.util.logging.Logger;

public class Application {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public static void main(String[] args)  {

        CustomerDAO.save("Test", "Test");

    }
}
