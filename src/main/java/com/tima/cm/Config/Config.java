package com.tima.cm.Config;

import com.tima.cm.Bean.ClouderaConfig;
import com.tima.cm.Exception.PropertiesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

public class Config {
    private final static Logger LOGGER = LoggerFactory.getLogger(Config.class);

    public static final String CONFIGFILE = "config.properties";

    public static String getThisPath() {
        URL url = Config.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (filePath.endsWith(".jar")) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        File file = new File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }

    public static Properties getConfig(String configFileName) throws PropertiesException {

        File file = new File(configFileName);
        File localFile = new File(getThisPath() + File.separator + configFileName);

        if (!localFile.exists()) {
            LOGGER.info("the local file --" + localFile.getPath() + "-- is not exist!!");
            LOGGER.error("No config.properties. Please enter right config.properties");
            throw new PropertiesException("No " + configFileName + " find, Please enter right path with config.properties.");
        }

        Properties pro = new Properties();
        FileInputStream in = null;
        try {
            if (file.exists()) {
                in = new FileInputStream(file);
            } else {
                in = new FileInputStream(localFile);
            }
            pro.load(in);
            in.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return pro;
    }

    public static String findProValue(Properties pro, String findName) {
        if (findName == null) {
            return "";
        } else if (pro.getProperty(findName) == null) {
            return findName;
        } else {
            return pro.getProperty(findName);
        }
    }

    public static ClouderaConfig getClouderaConfig() throws PropertiesException {
        Properties properties = getConfig(CONFIGFILE);
        ClouderaConfig clouderaConfig = new ClouderaConfig();
        clouderaConfig.setClouderaUrl(findProValue(properties,"cloudera.manager.url"));
        clouderaConfig.setClouderaPort(findProValue(properties,"cloudera.manager.port"));
        clouderaConfig.setClouderaUserName(findProValue(properties,"cloudera.manager.username"));
        clouderaConfig.setClouderaPassword(findProValue(properties,"cloudera.manager.password"));
        return clouderaConfig;
    }

}
