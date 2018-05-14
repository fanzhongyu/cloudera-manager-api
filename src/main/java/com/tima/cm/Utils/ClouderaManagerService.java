package com.tima.cm.Utils;

import com.cloudera.api.ClouderaManagerClientBuilder;
import com.cloudera.api.DataView;
import com.cloudera.api.model.*;
import com.cloudera.api.v10.ServicesResourceV10;
import com.cloudera.api.v11.RolesResourceV11;
import com.cloudera.api.v18.RootResourceV18;
import com.cloudera.api.v18.ServicesResourceV18;
import com.tima.cm.Bean.Agent;
import com.tima.cm.Bean.Service;
import com.tima.cm.main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ClouderaManagerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClouderaManagerService.class);

    static RootResourceV18 apiRoot;

    static {
        apiRoot = new ClouderaManagerClientBuilder().withHost(main.clouderaConfig.getClouderaUrl()).
                withPort(Integer.valueOf(main.clouderaConfig.getClouderaPort()))
                .withUsernamePassword(main.clouderaConfig.getClouderaUserName(), main.clouderaConfig.getClouderaPassword()).build().getRootV18();
    }

    public static void getAllService(){
        LOGGER.info("开始测试的时间为{},**************开始测试集群服务运行状态**************",Utils.getCurrentTime());
        ApiClusterList apiClusterList = apiRoot.getClustersResource().readClusters(DataView.SUMMARY);
        for(ApiCluster apiCluster:apiClusterList){
            LOGGER.info("集群名称：{}",apiCluster.getDisplayName());
            LOGGER.info("CDH 版本：{}-{}",apiCluster.getVersion(),apiCluster.getFullVersion());
            ServicesResourceV10 servicesResourceV10 = apiRoot.getClustersResource().getServicesResource(apiCluster.getName());
            List<ApiService> apiServices = servicesResourceV10.readServices(DataView.FULL).getServices();
            LOGGER.info("集群总共有：{} 个service 在运行",apiServices.size());
            for(ApiService apiService:apiServices){
                Service service = formatService(apiService);
                LOGGER.info("***********************************");
                LOGGER.info("service 名称 {}",service.getName());
                LOGGER.info("service 类型 {}",service.getType());
                for(Agent agent:service.getAgentList()) {
                    LOGGER.info("节点名称 {}", agent.getName());
                    LOGGER.info("节点状态 {}", agent.getStatus());
                }
                LOGGER.info("***********************************");
            }
        }
        LOGGER.info("结束测试的时间为{},**************结束测试集群服务运行状态**************",Utils.getCurrentTime());
    }

    public static Service formatService(ApiService apiService){
        Service service = new Service();
        List<Agent> agents = new ArrayList<>();
        service.setName(apiService.getName());
        service.setType(apiService.getType());
        for(ApiHealthCheck apiHealthCheck:apiService.getHealthChecks()){
            Agent agent =new Agent();
            agent.setName(apiHealthCheck.getName());
            agent.setStatus(apiHealthCheck.getSummary());
            agents.add(agent);
        }
        service.setAgentList(agents);
        return service;
    }

    public static void getAllServiceRoles(){
        LOGGER.info("开始测试的时间为{},**************开始测试集群各个服务的roles运行状态**************",Utils.getCurrentTime());
        ApiClusterList apiClusterList = apiRoot.getClustersResource().readClusters(DataView.SUMMARY);
        for(ApiCluster apiCluster:apiClusterList){
            LOGGER.info("集群名称：{}",apiCluster.getDisplayName());
            LOGGER.info("CDH 版本：{}-{}",apiCluster.getVersion(),apiCluster.getFullVersion());
            ServicesResourceV18 servicesResourceV18 = apiRoot.getClustersResource().getServicesResource(apiCluster.getName());
            List<ApiService> apiServices = servicesResourceV18.readServices(DataView.FULL).getServices();
            LOGGER.info("集群总共有：{} 个service 在运行",apiServices.size());
            for(ApiService apiService:apiServices){
                RolesResourceV11 rolesResourceV11 = servicesResourceV18.getRolesResource(apiService.getName());
                LOGGER.info("---------------------服务名称是{}---------------------",apiService.getName());
                for(ApiRole apiRole :rolesResourceV11.readRoles()){
                    LOGGER.info("***************************",apiRole.getName());
                    LOGGER.info("role名称 {}",apiRole.getName());
                    LOGGER.info("role类型 {}",apiRole.getType());
                    LOGGER.info("所属集群 {}",apiRole.getServiceRef().getClusterName());
                    LOGGER.info("所属服务 {}",apiRole.getServiceRef().getServiceName());
                    LOGGER.info("主机ID {}",apiRole.getHostRef().getHostId());
                    LOGGER.info("roleUrl {}",apiRole.getRoleUrl());
                    LOGGER.info("role状态 {}",apiRole.getRoleState());
                    LOGGER.info("运行状态总结 {}",apiRole.getHealthSummary());
                    LOGGER.info("entityStatus {}",apiRole.getEntityStatus());
                    LOGGER.info("roleConfigGroupName {}",apiRole.getRoleConfigGroupRef().getRoleConfigGroupName());
                    LOGGER.info("configStalenessStatus {}",apiRole.getConfigStalenessStatus());
                    LOGGER.info("haStatus {}",apiRole.getHaStatus());
                    for(ApiHealthCheck apiHealthCheck:apiRole.getHealthChecks()){
                        LOGGER.info("health check name {}",apiHealthCheck.getName());
                        LOGGER.info("health check summary {}",apiHealthCheck.getSummary());
                        LOGGER.info("health check suppressed {}",apiHealthCheck.getSuppressed());
                    }
                    LOGGER.info("***************************");
                }
                LOGGER.info("--------------------------------------------------------",apiService.getName());
            }
        }
        LOGGER.info("结束测试的时间为{},**************结束测试集群各个服务的roles运行状态**************",Utils.getCurrentTime());
    }

}
