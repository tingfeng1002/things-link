package com.thingslink.transport.mqtt;

/**
 * mqtt transport properties
 * @author wang xiao
 * date 2022/12/16
 */
public class MqttTransportProperties {


    /**
     * mqtt host
     */
    private String host;


    /**
     * mqtt port
     */
    private Integer port;


    /**
     * netty 内存 探测 level
     */
    private String leakDetectorLevel;


    /**
     * boss 线程数
     */
    private Integer bossGroupThreadCount;


    /**
     * work 线程数
     */
    private Integer workerGroupThreadCount;


    /**
     *   keepAlive
     */
    private boolean keepAlive;


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
}
