package com.thingslink.transport.mqtt;

import org.springframework.beans.factory.annotation.Value;

/**
 * mqtt transport properties
 *
 * @author wang xiao
 * date 2022/12/16
 */
public class MqttTransportProperties {

    /**
     * mqtt host
     */
    @Value("${thingsLink.transport.mqtt.host}")
    private String host;


    /**
     * mqtt port
     */
    @Value("${thingsLink.transport.mqtt.port}")
    private Integer port;


    /**
     * netty 内存 探测 level
     */
    @Value("${thingsLink.transport.mqtt.leak_detector_level}")
    private String leakDetectorLevel;


    /**
     * boss 线程数
     */
    @Value("${thingsLink.transport.mqtt.boss_group_thread_count}")
    private Integer bossGroupThreadCount;


    /**
     * work 线程数
     */
    @Value("${thingsLink.transport.mqtt.worker_group_thread_count}")
    private Integer workerGroupThreadCount;


    /**
     * keepAlive
     */
    @Value("${thingsLink.transport.mqtt.keep_alive}")
    private boolean keepAlive;


    /**
     * maxPayloadSize
     */
    @Value("${thingsLink.transport.mqtt.max_payload_size}")
    private Integer maxPayloadSize;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getLeakDetectorLevel() {
        return leakDetectorLevel;
    }

    public void setLeakDetectorLevel(String leakDetectorLevel) {
        this.leakDetectorLevel = leakDetectorLevel;
    }

    public Integer getBossGroupThreadCount() {
        return bossGroupThreadCount;
    }

    public void setBossGroupThreadCount(Integer bossGroupThreadCount) {
        this.bossGroupThreadCount = bossGroupThreadCount;
    }

    public Integer getWorkerGroupThreadCount() {
        return workerGroupThreadCount;
    }

    public void setWorkerGroupThreadCount(Integer workerGroupThreadCount) {
        this.workerGroupThreadCount = workerGroupThreadCount;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Integer getMaxPayloadSize() {
        return maxPayloadSize;
    }

    public void setMaxPayloadSize(Integer maxPayloadSize) {
        this.maxPayloadSize = maxPayloadSize;
    }
}
