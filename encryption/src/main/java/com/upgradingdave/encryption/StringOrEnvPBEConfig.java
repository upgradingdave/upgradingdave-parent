package com.upgradingdave.encryption;

import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

/**
 * Provides ability to set password using either default or using Environment variables or JVM System variables.
 */
public class StringOrEnvPBEConfig extends EnvironmentStringPBEConfig {

    private final static String PASSWORD="fudhs$ST29gjSF$%@WSDE$";

    public StringOrEnvPBEConfig(String environmentVarName) {

        super();

        if(environmentVarName!=null && environmentVarName.length() >0){

            String value = System.getenv(environmentVarName);

            if(value!= null && environmentVarName.length() > 0) {
                setPasswordEnvName(environmentVarName);
            } else {
                setPassword(PASSWORD);
            }
        }
    }
}
