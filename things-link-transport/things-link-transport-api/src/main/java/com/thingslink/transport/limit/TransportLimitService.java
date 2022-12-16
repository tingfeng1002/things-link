package com.thingslink.transport.limit;

import java.net.InetSocketAddress;

/**
 * transport limit service
 * 连接 限制
 *
 * @author wang xiao
 * date 2022/12/16
 */
public interface TransportLimitService {

    /**
     * check address
     * @param address address
     * @return Boolean
     */
    boolean checkAddress(InetSocketAddress address);

    /**
     *  auth success  call back
     * @param address address
     */
    void onAuthSuccess(InetSocketAddress address);


    /**
     *  auth fail  call back
     * @param address address
     */
    void onAuthFailure(InetSocketAddress address);
}
