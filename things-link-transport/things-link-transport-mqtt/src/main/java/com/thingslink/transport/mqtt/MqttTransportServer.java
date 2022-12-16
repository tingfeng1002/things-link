package com.thingslink.transport.mqtt;

import com.thingslink.transport.TransportServer;
import com.thingslink.transport.TransportType;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * MQTT transport server
 *
 * @author wang xiao
 * date 2022/12/1
 */
@Service("MqttTransportServer")
@Import(MqttTransportProperties.class)
@ConditionalOnExpression("'${transport.mqtt.enabled}'=='true'")
public class MqttTransportServer implements TransportServer {

    private final Logger logger = LoggerFactory.getLogger(MqttTransportServer.class);


    private MqttTransportProperties mqttTransportProperties;


    private EventLoopGroup bossEventLoopGroup;

    private EventLoopGroup workerEventLoopGroup;

    private Channel serverChannel;

    private MqttTransportContext transportContext;

    @Override
    public String getServerName() {
        return TransportType.MQTT.name();
    }

    @Override
    @PostConstruct
    public void initialize() throws InterruptedException {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(mqttTransportProperties.getLeakDetectorLevel().toUpperCase()));
        logger.info("MQTT transport Server Starting...");
        bossEventLoopGroup = new NioEventLoopGroup(mqttTransportProperties.getBossGroupThreadCount());
        workerEventLoopGroup = new NioEventLoopGroup(mqttTransportProperties.getWorkerGroupThreadCount());
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new MqttTransportServerInitializer(transportContext, mqttTransportProperties.getMaxPayloadSize()))
                .childOption(ChannelOption.SO_KEEPALIVE, mqttTransportProperties.isKeepAlive());
        serverChannel = serverBootstrap.bind(mqttTransportProperties.getHost(), mqttTransportProperties.getPort()).sync().channel();
        logger.info("MQTT transport Server Started!!!!");
    }


    @Override
    @PreDestroy
    public void stop() throws InterruptedException {
        logger.info("MQTT transport Server Stopping...");
        try {
            serverChannel.close().sync();
        } finally {
            workerEventLoopGroup.shutdownGracefully();
            bossEventLoopGroup.shutdownGracefully();
        }
        logger.info("MQTT transport Server Stopped!!!!");
    }


    @Autowired
    public void setMqttTransportProperties(MqttTransportProperties mqttTransportProperties) {
        this.mqttTransportProperties = mqttTransportProperties;
    }


    @Autowired
    public void setTransportContext(MqttTransportContext transportContext) {
        this.transportContext = transportContext;
    }
}
