# MySQL Optimisation

still need to work, leave here

- [MySQL Optimisation](#mysql-optimisation)
  - [Reason: Query lag](#reason-query-lag)
    - [判断查询语句是否需要优化依赖工具](#判断查询语句是否需要优化依赖工具)

## Reason: Query lag

查询过慢的原因

- **有规律**速度过慢
  - 用户访问量激增
  - 解决：**mysql集群**
- **无规律**速度过慢
  - 跟表中数据量变化有关系
  - 解决：**查询语句优化**

### 判断查询语句是否需要优化依赖工具

1. 慢查询记录
2. explain执行计划
3. 相关日志文件