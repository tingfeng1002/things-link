# ThingsLink
开源的iot平台

## 功能

- 设备连接并发送数据到ThingsLink，连接协议包括MQTT,HTTP
- ThingsLink 发送数据到设备,支持自定义数据,命令
- 设备协议payload适配，对于原始的二进制报文，提供扩展插件进行内容转换
- 在线调试与设备消息追踪
- 设备数据规则处理，提供消息转发与告警规则
- 数据统计分析,提供消息点等统计展示
- 设备数据的websocket 推送
- 设备数据简单处理，对于json格式的数据，支持简单的计算等功能

## 使用示例图



## 文档

- [thingsLink](https://github.com/tingfeng1002/things-link/blob/main/things-link-docs/home.md) 

## 快速开始

1. 定义模型，模型中约束设备的协议与payload 数据格式
2. 创建设备，自动分发设备连接方式所需的密钥等信息
3. 设备连接，[参考连接文章](https://github.com/tingfeng1002/things-link/blob/main/things-link-docs/transport.md)
4. 在线调试与配置规则

## 参与贡献

[开发文档](https://github.com/tingfeng1002/things-link/blob/main/things-link-docs/develop.md)

## Licenses

This project is released under [Apache 2.0 License](https://github.com/tingfeng1002/things-link/blob/main/LICENSE).