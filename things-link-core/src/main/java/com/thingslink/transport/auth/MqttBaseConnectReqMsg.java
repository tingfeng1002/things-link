package com.thingslink.transport.auth;

/**
 * mqtt base connect msg
 * @author wang xiao
 * date 2023/1/9
 */
public record MqttBaseConnectReqMsg(String clientId, String username, String password) {

    @Override
    public String clientId() {
        return clientId;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }
}
