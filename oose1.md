# 目标

- 生成CFG & 理解如何通过CFG得出程序的麦凯布循环复杂度 V(G) = E-N+2

- 理解软件度量如何实现 & 如何改进软件质量

- ``ckmetrics.jar``该依赖包用于JAVA类的CK度量.
  
  - 通过jar包中的 ``Apache Byte Code Engineering Library (BCEL)``API进行类分析

---

## 将项目打包成jar

- export project

- as runnable jar

---

## AE1

### Task

1. ``Controller.java``类的``initialiseVehicle``方法的绘制控制流图CFG.

2. 假定目标针对项目``VehicleControlSystem``
   - **提高``封装性，模块性``，减少``复杂度``**

   - 找出**3个相关metrics指标**可以用于测量该类的质量因素.

3. 找出``VehicleControlSystem``项目中**最需要被重组的类，**
   - 以达到**提高``封装性，模块性``，减少``复杂度``**
  
   - **用max 200字** 支持观点

4. **实现&提出4个因素达成以上类的重组**，以提高``Task 2``中提到的3个CKmetrics指标
   - 200字讨论如何通过重构**某类，提高``封装性，模块性``，减少``复杂度``**

5. 为重构后的新项目绘制新**类图**
   - 200字讨论实现后的新项目与原项目**任何结构上的差异**

---

## 提交

- ``pdf``格式
  
  - top包括``name & guid``
  
  - 文件包括``Task 1-5 solutions`` & ``相关截图`` & ``类图``

- 项目``VehicleControlSystem``**源码**, 包括重组后的实现类

- ``jar``
  
  - 实现重组后的新项目``jar``包
