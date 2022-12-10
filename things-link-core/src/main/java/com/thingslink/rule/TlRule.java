package com.thingslink.rule;


import com.thingslink.Things;

/**
 * 规则
 * @author wang xiao
 * date 2022/12/8
 */
public interface TlRule extends Things {


    /**
     * 规则处理动作
     */
    default void doAction() {
        if (match()) {
          process();
        }
    }

    /**
     * 规则是否 匹配
     * @return boolean
     */
    boolean match();


    /**
     * 业务处理
     */
    void process();


}
