package com.thingslink.transport.service;

import com.thingslink.transport.limit.InetAddressLimitStats;
import com.thingslink.transport.limit.TransportLimitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * transport limit service implementation
 * todo : map 什么时候清空了？
 * @author wang xiao
 * date 2022/12/29
 */
@Service
public class DefaultTransportLimitService implements TransportLimitService {


    @Value("${thingsLink.transport.limit.enabled:false}")
    private boolean limitEnabled;

    @Value("${thingsLink.transport.limit.timeout:60000}")
    private long limitTimeout;

    @Value("${thingsLink.transport.limit.error_counter:10}")
    private long  maxErrorCount;

    private final Map<InetAddress, InetAddressLimitStats> addressLimitStatsMap = new ConcurrentHashMap<>();

    @Override
    public boolean checkAddress(InetSocketAddress address) {
        if (!limitEnabled){
            return true;
        }
        var inetAddressLimitStats = addressLimitStatsMap.computeIfAbsent(address.getAddress(), inetAddress -> new InetAddressLimitStats());
        return !inetAddressLimitStats.isBlocked() && (inetAddressLimitStats.getLastActivityTs()+ limitTimeout < System.currentTimeMillis());
    }

    @Override
    public void onAuthSuccess(InetSocketAddress address) {
        if (!limitEnabled) {
            return;
        }
        var inetAddressLimitStats = addressLimitStatsMap.computeIfAbsent(address.getAddress(), a -> new InetAddressLimitStats());
        inetAddressLimitStats.getLock().lock();
        try {
            inetAddressLimitStats.setLastActivityTs(System.currentTimeMillis());
            inetAddressLimitStats.setFailureCount(0);
            if (inetAddressLimitStats.isBlocked()) {
                inetAddressLimitStats.setBlocked(false);
            }
        } finally {
            inetAddressLimitStats.getLock().unlock();
        }
    }

    @Override
    public void onAuthFailure(InetSocketAddress address) {
        if (!limitEnabled) {
            return;
        }
        var inetAddressLimitStats = addressLimitStatsMap.computeIfAbsent(address.getAddress(), a -> new InetAddressLimitStats());
        inetAddressLimitStats.getLock().lock();
        try {
            inetAddressLimitStats.setLastActivityTs(System.currentTimeMillis());
            int failureCount = inetAddressLimitStats.getFailureCount() + 1;
            inetAddressLimitStats.setFailureCount(failureCount);
            if (failureCount >= maxErrorCount) {
                inetAddressLimitStats.setBlocked(true);
            }
        } finally {
            inetAddressLimitStats.getLock().unlock();
        }
    }
}
