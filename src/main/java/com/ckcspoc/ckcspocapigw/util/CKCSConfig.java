package com.ckcspoc.ckcspocapigw.util;


public class CKCSConfig {

    public static String getAccessKeyByEnv(String env){
        return (env.equals(CKCSConstants.PROD_ENV))?CKCSConstants.PROD_ENV_ACCESS_KEY:CKCSConstants.DEV_ENV_ACCESS_KEY;
    }

    public static String getEnvironmentIdByEnv(String env){
        return (env.equals(CKCSConstants.PROD_ENV))?CKCSConstants.PROD_ENV_ID:CKCSConstants.DEV_ENV_ID;
    }

}
