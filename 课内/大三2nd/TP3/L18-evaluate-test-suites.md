# System Scale Testing

* [System Scale Testing](#system-scale-testing)
* [Useful Metrics](#useful-metrics)
* [Test Suite Run Time](#test-suite-run-time)
* [测试覆盖率：Test Code Coverage](#测试覆盖率test-code-coverage)
* [Limits of test code coverage](#limits-of-test-code-coverage)

![](/static/2021-01-19-22-20-35.png)

* 上图中间点为optimal point，tradeoff
  * develop team need to compromise two factors
* 往右，efficiency妥协，需要做更多的test case
* 往左，优化test suite，减少test case，从而减少了effectiveness（preventing defect introduction的能力）。最后达到最高效率

:orange:effectiveness

* effectiveness concerns about the ability of preventing the defects being introduced to the application
* highly effective test suite introduce probablity of defect being introduced into the system
  * detective is low as there are high probability of the defect would be caused one or more test cases to fail

:orange: efficiency

* the cost to maintain the test suite
* any defect prvented by the test suite, the few lines of code need to maintain the test suite the more efficient to test, (need low effort to maintain)

:orange:实践中要tradeoff测试覆盖率以便增加缺陷发现率

* 最小化对于每个缺陷需要的测试开销

# Useful Metrics

计算Efficiency & Effectiveness的度量标准

![](/static/2021-01-19-22-36-55.png)

* post release reported defects
  * measuring the **effectiveness**
  * app that low user-reported defects
* Test suite execution time
  * measure the code base efficiency
* failed tests per defect
* test suite LoC
  * larger code base, the less efficient

# Test Suite Run Time

![](/static/2021-01-19-22-40-45.png)

重要的efficiency metric

* 单元测试- 速度快
  * 持续集成，构建平均速度 < 10min

如果测试很慢（尤其是单元测试），可能有如下原因

* IO事件，限制利用test double

# 测试覆盖率：Test Code Coverage

![](/static/2021-01-19-22-45-43.png)

* 每个不同part在执行期间的表现
* lines of code executed in total during the test suite

除了LoC的测量，也可以换成statements，Expressions的测量

* reasonable to evaluate more than once

The number of unique lines of application code executed, divided by the total lines of code in the application.

# Limits of test code coverage

![](/static/2021-01-19-23-01-40.png)

* 注意测试覆盖率不包含defect检测，