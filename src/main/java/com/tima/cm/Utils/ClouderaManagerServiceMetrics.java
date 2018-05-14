package com.tima.cm.Utils;

import com.alibaba.fastjson.JSON;
import com.cloudera.api.ClouderaManagerClientBuilder;
import com.cloudera.api.model.ApiTimeSeries;
import com.cloudera.api.model.ApiTimeSeriesData;
import com.cloudera.api.model.ApiTimeSeriesResponse;
import com.cloudera.api.model.ApiTimeSeriesResponseList;
import com.cloudera.api.v18.RootResourceV18;
import com.cloudera.api.v6.TimeSeriesResourceV6;
import com.tima.cm.Bean.Data;
import com.tima.cm.Bean.Metric;
import com.tima.cm.main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ClouderaManagerServiceMetrics {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClouderaManagerServiceMetrics.class);

    static RootResourceV18 apiRoot;

    static {
        apiRoot = new ClouderaManagerClientBuilder().withHost(main.clouderaConfig.getClouderaUrl()).
                withPort(Integer.valueOf(main.clouderaConfig.getClouderaPort()))
                .withUsernamePassword(main.clouderaConfig.getClouderaUserName(), main.clouderaConfig.getClouderaPassword()).build().getRootV18();
    }

    public static List<Metric> formatApiTimeSeriesResponse(List<ApiTimeSeriesResponse> apiTimeSeriesResponseList){
        List<Metric> metrics = new ArrayList<>();
        for(ApiTimeSeriesResponse apiTimeSeriesResponse:apiTimeSeriesResponseList) {
            List<Data> dataList = new ArrayList<>();
            List<ApiTimeSeries> apiTimeSeriesList = apiTimeSeriesResponse.getTimeSeries();
            for (ApiTimeSeries apiTimeSeries : apiTimeSeriesList) {
                Metric metric = new Metric();
                metric.setMetricName(apiTimeSeries.getMetadata().getMetricName());
                metric.setEntityName(apiTimeSeries.getMetadata().getEntityName());
                metric.setStartTime(apiTimeSeries.getMetadata().getStartTime().toString());
                metric.setEndTime(apiTimeSeries.getMetadata().getEndTime().toString());
                for (ApiTimeSeriesData apiTimeSeriesData : apiTimeSeries.getData()) {
                    Data data = new Data();
                    data.setTimestamp(apiTimeSeriesData.getTimestamp().toString());
                    data.setType(apiTimeSeriesData.getType());
                    data.setValue(apiTimeSeriesData.getValue());
                    dataList.add(data);
                }
                metric.setData(dataList);
                metrics.add(metric);
            }
        }
        return metrics;
    }

    public static List<Metric> getServiceMetrics(String query,String startTime , String endTime){
        TimeSeriesResourceV6 timeSeriesResourceV6 = apiRoot.getTimeSeriesResource();
        String[] params = new String []{query,startTime,endTime};
        LOGGER.info("query sql is {} ,startTime is {} ,endTime is now",params);

        LOGGER.info("开始测试的时间为{},**************开始查询某个服务点位状态**************",Utils.getCurrentTime());
        ApiTimeSeriesResponseList response = timeSeriesResourceV6.queryTimeSeries(query,startTime,endTime);
        List<ApiTimeSeriesResponse> apiTimeSeriesResponseList = response.getResponses();
        List<Metric> metrics = formatApiTimeSeriesResponse(apiTimeSeriesResponseList);
        LOGGER.info("查询时间序列点位:{}", JSON.toJSON(metrics));
        LOGGER.info("结束测试的时间为{},**************结束查询某个服务点位状态**************",Utils.getCurrentTime());
        return metrics;
    }

}
