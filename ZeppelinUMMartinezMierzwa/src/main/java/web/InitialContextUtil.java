package web;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class InitialContextUtil {
    protected static InitialContext ic;

    public static InitialContext getInstance() {
        if (ic == null) {
            final Properties initialContextProperties = new Properties();
            
            initialContextProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jboss.naming.remote.client.InitialContextFactory");
            initialContextProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8081");
            initialContextProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            initialContextProperties.put("jboss.naming.client.ejb.context", true);
        
            try {
                ic = new InitialContext(initialContextProperties);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return ic;
    }
}