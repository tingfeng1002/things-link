# ThingsLink设计文档

## 程序结构说明

- config: 配置文件信息
- things-link-docs: 文档说明 相当于wiki
- things-link-application: 程序启动引导类，提供web api
- things-link-core: 核心模块,主要提供抽象类与接口
- things-link-cache: 缓存模块，针对多种缓存的实现，默认是内存缓存
- things-link-queue: 消息队列模块，默认是内存消息队列
- things-link-transport: 设备连接层，提供设备连接能力
- things-link-ui: 前端界面（暂时没人做 本人前端垃圾）
- things-link-ws: websocket 推送
- things-link-rule: 规则配置

## 程序端口
- MQTT:1883
- HTTP: 8888 
