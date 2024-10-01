package Baloot.Listeners;


import Baloot.Api.APIFactory;
import Baloot.Api.APIImpl;
import Baloot.Domain.User;
import Baloot.Server.Initializer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class InitDataListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //Notification that the servlet context is about to be shut down.
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("Reading data from Baloot server");
        Initializer initializer = new Initializer(APIFactory.getInstance());
        initializer.initServerData();
        System.out.println("Done reading data from Baloot server");
    }
}
