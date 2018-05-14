package com.tima.cm.Utils;

import com.cloudera.api.ClouderaManagerClientBuilder;
import com.cloudera.api.DataView;
import com.cloudera.api.model.ApiCluster;
import com.cloudera.api.model.ApiClusterList;
import com.cloudera.api.v18.RootResourceV18;
import com.tima.cm.main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClouderaManagerCluster {
    static RootResourceV18 apiRoot;

    static {
        apiRoot = new ClouderaManagerClientBuilder().withHost(main.clouderaConfig.getClouderaUrl()).
                withPort(Integer.valueOf(main.clouderaConfig.getClouderaPort()))
                .withUsernamePassword(main.clouderaConfig.getClouderaUserName(), main.clouderaConfig.getClouderaPassword()).build().getRootV18();
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(ClouderaManagerHost.class);


    public static void getAllCluster(){
        LOGGER.info("开始测试的时间为{},**************开始测试获取ClouderaManager集群信息**************",Utils.getCurrentTime());
        ApiClusterList apiClusterList = apiRoot.getClustersResource().readClusters(DataView.FULL);
        LOGGER.info("ClouderaManager 共管理了{}个集群",apiClusterList.getClusters().size());
        for(ApiCluster apiCluster : apiClusterList){
            ApiCluster apiCluster1 = apiRoot.getClustersResource().readCluster(apiCluster.getName());
            LOGGER.info("集群名称 {}",apiCluster1.getName());
            LOGGER.info("集群显示名称 {}",apiCluster1.getDisplayName());
            LOGGER.info("CDH 版本：{}-{}",apiCluster1.getVersion(),apiCluster.getFullVersion());
            LOGGER.info("ClusterUrl {}",apiCluster1.getClusterUrl());
            LOGGER.info("HostUrl {}",apiCluster1.getHostsUrl());
            LOGGER.info("Cluster Uuid {}",apiCluster1.getUuid());
            LOGGER.info("集群运行状态 {}",apiCluster1.getEntityStatus());
        }
        LOGGER.info("结束测试的时间为{},**************结束测试获取ClouderaManager集群信息**************",Utils.getCurrentTime());
    }

}
