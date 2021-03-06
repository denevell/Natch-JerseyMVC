
package org.denevell.natch.jerseymvc;

import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/** 
 * Reads the war's manifest from META-INF/MANIFEST.MF on application startup.
 */
@WebListener
public class ManifestVarsListener implements ServletContextListener {

    public static Attributes sMainManifestAttributes;

    /**
     * Read the manifest from /META-INF/MANIFEST.MF
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
       try {
         InputStream inputStream = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");
         Manifest manifest = new Manifest(inputStream);
         sMainManifestAttributes = manifest.getMainAttributes();
       } catch (Exception e) {
         throw new RuntimeException(e);
       } 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       sMainManifestAttributes = null;
    }

    /**
     * Generic querying of the manifest.
     * @return The result, as run through String.trim()
     */
    public static String getValue(String name) {
       return sMainManifestAttributes.getValue(name).trim();
    }

}
