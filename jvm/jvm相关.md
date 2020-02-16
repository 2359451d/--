# :bento:JVM学习

.java 是高级语言源代码
.class 是编译后的字节码

- JVM用于运行class文件翻译成机器语言，指令码

- 不同系统JDK，JVM有自己的规范
  - Groovy&Scala&Kotlin都是基于JVM的语言可以运行在JVM上
  - :lemon:原理:   **需要实现编译器，通过该编译器将java源文件编译成JVM能识别的字节码class文件**

---

## :bento:类加载器

![](/static/2020-02-16-10-30-43.png)

- 类加载器子系统通过字节流读取class文件

:lemon:加载流程
![](/static/2020-02-16-10-31-40.png)

:lemon:类加载生命周期
![](/static/2020-02-16-11-12-53.png)
