
package org.apache.log4j;


/**
 * LogConfigurator adapter for log4j to java logger.
 * It provides log4j BasicConfigurator methods used in JAS
 * @author Heinz Kredel.
 */
public class BasicConfigurator {


    /**
     * configure logging.
     */
    public static void configure() {
        try {
            java.util.logging.LogManager.getLogManager().readConfiguration();
        } catch ( java.io.IOException e ) {
            e.printStackTrace();
            //System.out.println("BasicConfigurator.configure(): " + e);
        }
    }

}