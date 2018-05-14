package com.tima.cm.Utils;

import com.alibaba.fastjson.JSON;
import com.cloudera.api.ClouderaManagerClientBuilder;
import com.cloudera.api.DataView;
import com.cloudera.api.model.ApiHost;
import com.cloudera.api.model.ApiRoleRef;
import com.cloudera.api.v10.HostsResourceV10;
import com.cloudera.api.v10.RootResourceV10;
import com.cloudera.api.v18.RootResourceV18;
import com.tima.cm.Bean.Host;
import com.tima.cm.main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ClouderaManagerHost {

    static RootResourceV18 apiRoot;

    static {
        apiRoot = new ClouderaManagerClientBuilder().withHost(main.clouderaConfig.getClouderaUrl()).
                withPort(Integer.valueOf(main.clouderaConfig.getClouderaPort()))
                .withUsernamePassword(main.clouderaConfig.getClouderaUserName(), main.clouderaConfig.getClouderaPassword()).build().getRootV18();
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(ClouderaManagerHost.class);

    public static void getAllHost(){
        LOGGER.info("开始测试的时间为{},**************开始测试集群主机运行状态**************",Utils.getCurrentTime());
        HostsResourceV10 hostsResourceV10 = apiRoot.getHostsResource();
        List<ApiHost> hostList = hostsResourceV10.readHosts(DataView.SUMMARY).getHosts();
        LOGGER.info("总共有 {} 台主机组成集群",hostList.size());
        for(ApiHost apiHost:hostList){
            LOGGER.info("---------------------------------------------");
            Host host = formatHost(hostsResourceV10.readHost(apiHost.getHostId()));
            LOGGER.info("主机Id : {}",host.getHostId());
            LOGGER.info("主机名： {}",host.getHostName());
            LOGGER.info("主机IP： {}",host.getIpAddress());
            LOGGER.info("主机线程数：{}",host.getNumCores());
            LOGGER.info("上次上报心跳时间 ：{}",host.getLastHeart());
            LOGGER.info("核心数：{}",host.getNumPhysicalCores());
            LOGGER.info("机架：{}",host.getRack());
            LOGGER.info("内存（G）：{}",host.getTotalPhysMemBytes());
            LOGGER.info("进程：{}", JSON.toJSON(host.getServices()));
            LOGGER.info("---------------------------------------------");
        }
        LOGGER.info("结束测试的时间为{},**************结束测试集群主机运行状态**************",Utils.getCurrentTime());
    }

    public static Host formatHost(ApiHost apiHost){
        Host host = new Host();
        List<String> services = new ArrayList<>();
        host.setHostId(apiHost.getHostId());
        host.setHostName(apiHost.getHostname());
        host.setIpAddress(apiHost.getIpAddress());
        host.setNumCores(apiHost.getNumCores());
        host.setNumPhysicalCores(apiHost.getNumPhysicalCores());
        host.setLastHeart(apiHost.getLastHeartbeat().toString());
        host.setRack(apiHost.getRackId());
        host.setTotalPhysMemBytes(apiHost.getTotalPhysMemBytes()/1073741824);
        for(ApiRoleRef apiRoleRef:apiHost.getRoleRefs()){
            services.add(apiRoleRef.getRoleName());
        }
        host.setServices(services);
        return host;
    }
}
