package com.tima.cm;

import com.tima.cm.Bean.ClouderaConfig;
import com.tima.cm.Config.Config;
import com.tima.cm.Exception.PropertiesException;
import com.tima.cm.Utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class main {
    private final static Logger LOGGER = LoggerFactory.getLogger(main.class);

    public static ClouderaConfig clouderaConfig;

    static {
        try {
            clouderaConfig = Config.getClouderaConfig();
        } catch (PropertiesException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        String[] params = new String[]{clouderaConfig.getClouderaUrl(),clouderaConfig.getClouderaPort(),
                clouderaConfig.getClouderaUserName(),clouderaConfig.getClouderaPassword()};

        LOGGER.info("开始 Cloudera 集群自动化运维管理编程接口测试，" +
                "本次测试的cloudera的地址是{}，端口是{}，用户名{}，密码{}",params);

        ClouderaManagerCluster.getAllCluster();

        ClouderaManagerHost.getAllHost();

        ClouderaManagerService.getAllService();

        ClouderaManagerService.getAllServiceRoles();

        ClouderaManagerServiceMetrics.getServiceMetrics("SELECT health_good_rate * 100 AS \"good health\" WHERE entityName = \"hbase\" AND category = SERVICE"
                ,"2018-05-09","now");

        ClouderaManagerServiceMetrics.getServiceMetrics("SELECT load_1 WHERE entityName = \"8df06e13-a00d-41ef-95eb-2eaada8c321e\" AND category = HOST"
                ,"2018-05-09","now");

        LOGGER.info("结束 Cloudera 集群自动化运维管理编程接口测试");

    }
}
