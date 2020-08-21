# Revision

Part 3: Path, Reflection, Annotation, JDK14 new traits

![](/static/2020-08-16-18-27-19.png)

- [Revision](#revision)
  - [Variable args](#variable-args)
  - [Path](#path)
  - [ClassLoader](#classloader)
    - [Parents Delegate](#parents-delegate)
  - [Reflection](#reflection)
    - [cons](#cons)
    - [how to get Class](#how-to-get-class)
    - [create instance](#create-instance)
      - [newInstance()](#newinstance)
      - [I/O & Properties](#io--properties)
        - [ResourceBundle: getBundle()](#resourcebundle-getbundle)
      - [static block & Class.forName() bottom](#static-block--classforname-bottom)
    - [Field](#field)
      - [getName()](#getname)
      - [getType() & getModifiers()](#gettype--getmodifiers)
      - [access objects' fields: set & get](#access-objects-fields-set--get)
    - [Method(*)](#method)
      - [getDeclaredMethods() & getMethods()](#getdeclaredmethods--getmethods)
      - [call the methods accords to reflection](#call-the-methods-accords-to-reflection)
      - [Constructors](#constructors)
    - [get Parent & Interface](#get-parent--interface)
    - [Decompile](#decompile)
  - [Annotation](#annotation)
    - [use](#use)
    - [Fields](#fields)
    - [Reflection of Annotation](#reflection-of-annotation)

## Variable args

可变长度参数

* syntax - `type...`
  * 如`int...args`
* 参数个数`0~n`
* 可变长度参数只能有一个，位置为最后一个

## Path

🍊 解决之前PropertiesI/O流读入数据的String路径固定写法

* IDEA默认当前路径为project根，固定String路径写法**移植性差**
  * `FileReader reader = new FileReader(String path)`

🍬 使用`Path`通用方式，只适用于在**类路径下的文件**，直接获得绝对路径

* <font color="red">适用多系统</font>
* <font color="blue">在`src`下的都是类路径下</font>
* `Thread.currentThread()`获取当前线程对象
* `getContextClassLoader()`线程对象的方法，**获取当前线程的类加载器对象**
* `getResource()`类加载器对象的方法，**当前线程的类加载器默认从类的【根路径】下加载(获取)资源**

```java
String path = Thread.currentThread().getContextClassLoader().getResource("filename").getPath();
```

## ClassLoader

🍊 类加载器(JDK自带)

> 专门负责加载类的命令/工具

* 启动类加载器（父加载器）
* 扩展类加载器（母加载器）
* 应用类加载器

🍊 例如`String s = "abc"`

> 代码在开始执行之前，通过类加载器加载所需要类至JVM中
>
> 以上类加载器会找`String.class`文件，找到就加载

* 首先通过**启动类加载器**加载
  * 专门加载`...\java\jdk1.xxx\jre\lib\rt.jar`
  * `rt.jar`有JDK最核心的类库
* <font color="red">如果启动类加载器未能加载到，</font>**则扩展类加载器加载**
  * 专门加载`...\java\jdk1.xxx\jre\lib\ext\*.jar`
* <font color="red">如果扩展类加载器未能加载到，</font>**则应用类加载器加载**
  * 专门加载`classpath`中的jar包(class文件)

### Parents Delegate

java为保证类加载安全，使用双亲委派机制

* 优先从启动类加载器中加载 - 父
* 父无法加载到，则扩展类加载器加载 - 母
* 如都未加载到，则通过应用加载器加载

## Reflection

🍊 反射机制

* 通过反射机制可以操作字节码文件`.class`
* `java.lang.reflect.*`

🍊 相关类

* `java.lang.Class` - 整个字节码文件，代表一个类
* `java.lang.reflect.Method` - 字节码中**方法**字节码
* `java.lang.reflect.Constructor` - 字节码中**构造方法**字节码
* `java.lang.reflect.Field` - 字节码中**属性(成员变量:包括静态变量和实例变量)**字节码

### cons

反射两个缺点

![](/static/2020-08-17-00-57-43.png)

> 最佳方案是保守地使用反射——仅在它可以真正增加灵活性的地方 ——记录其在目标类中的使用

### how to get Class

![](/static/2020-08-15-15-32-13.png)

🍊 获取类字节码`java.lang.Class`的方法

![](/static/2020-08-15-15-06-22.png)

```java
/*
静态方法
参数: String，完整类名(带包名)
需要处理受控异常
*/
Class c1 = Class.forName("java.lang.String");// c1代表java/lang下String.class文件类型
Class c2 = Class.forName("java.util.Date");
```

---

`Class c = object.getClass()`, 此时c就代表类字节码文件，代表一个类

![](/static/2020-08-15-15-18-34.png)

---

`xxx.class` java中任何一种类型，包括基本数据类型都有`.class`属性

```java
/* 字节码文件 */
Class z = String.class;
Class k = Date.class;
Class f = int.class;
```

### create instance

🍊 通过反射实例化对象优点

* <font color="red">可不修改源码情况下，复用多次，解耦</font>
* 符合OCP开闭原则

#### newInstance()

🍊 通过反射机制实例化对象

![](/static/2020-08-15-19-28-52.png)

* `class.newInstance()`
  * 返回泛型`T`，JDK9过时
  * <font color="red">调用无参构造函数</font>，如果原类定义了其他有参函数，无无参函数，可能抛出异常

#### I/O & Properties

🍊 `Properties`是`Map`集合，线程安全，实现`HashTable`接口

* 配置文件`classinfo.properties`中写入`className=完整类名`
* <font color="red">可不修改源码情况下，复用多次</font>

```java
FileReader reader = new FileReader(".../classinfo.properties"); // file exclusive chara input stream
Properties pro = new Properties(); // map collection
pro.load(reader); // load the data to the collection

reader.close();

String className = pro.getProperty("className"); // get the value accords to the key

Class c = Class.forname(className);
c.newInstance();
```

🍬 采用通用路径改进

```java
String path = Thread.currentThread().getContextClassLoader().getResource("classinfo2.properties").getPath();
FileReader reader = new FileReader(path);
Properties pro = new Properties();
pro.load(reader);
reader.close();

pro.getProperties("className");
```

##### ResourceBundle: getBundle()

🍊 资源绑定器

* `java.util`包提供资源绑定器，便于获取**属性配置文件**`xx.properties`中的内容
* 属性配置文件需要放在类路径`src`下(推荐)
  * 不然需要补全相对路径
* <font color="red">省去`Properties pro = new Properties()`</font>

```java
/*
- 只能绑定 xxx.properties 文件，放在类路径下
- 文件扩展名去掉
*/
ResourceBundle bundle = ResourceBundle.getBundle("classinfo2");
String className = bundle.getString("classNmae");
```

#### static block & Class.forName() bottom

![](/static/2020-08-15-20-15-45.png)

* 静态代码块在类加载时执行，并只执行一次

🍊 `Class.forName()`底层？

* <font color="blue">会导致类加载</font>
* 如只想要某类的静态代码块执行，可采用本方法

### Field

🍬 获取的整个Field对象： 修饰符 + 类型 + 标识符

#### getName()

🍊 字段/属性/成员反射

![](/static/2020-08-16-15-38-24.png)

* `Class c = Class.forname(classname)`获取类
* `Field[] fields = c.getFields()`获取成员，返回`Field`数组
  * 只能获取公开`public`修饰符的属性
* `Field f = fields[0]`取出特点属性
* `String name = f.getName()`获取属性名字(标识符)

---

![](/static/2020-08-16-15-42-42.png)

```java
Class c = Class.forName(classname);
Field[] fields = c.getDeclaredFields(); // return all the declared fields
for (Field f: fields){
  // get the name of field
  System.out.println(field.getName());
}
```

#### getType() & getModifiers()

`field.getType()`

![](/static/2020-08-16-15-51-06.png)

* 返回`Class<?>`
* 推荐写法 - `field.getType().getSimpleName()`

`field.getModifiers()`

![](/static/2020-08-16-15-53-07.png)

* 返回`int`
* `Modifier.toString(int i)`传入修饰符代表的整型，转换成字符串
* 推荐写法 - `Modifier.toString(c.getModifiers())`

#### access objects' fields: set & get

如何通过反射机制访问java对象的属性？

* 给属性赋值set?
* 获取属性的值get？

🍊 设置属性(<font color="red">注意不能直接访问私有属性，可以打破封装【不推荐，设置完毕后外部也可以访问】</font>)

* 打破封装 `field.setAccessible(true)`

![](/static/2020-08-16-16-50-56.png)

```java
Class student = Class.forName(...);
Object obj = student.newInstance();

Field number = student.getDeclaredField("number");
number.set(obj, 222); // set the value of number field
```

🍊 获取属性(<font color="red">注意不能直接访问私有属性，可以打破封装【不推荐，设置完毕后外部也可以访问】</font>)

![](/static/2020-08-16-17-01-24.png)

```java
Class student = Class.forName(...);
Object obj = student.newInstance();

Field number = student.getDeclaredField("number");
number.get(obj); // get the number field of obj object
```

### Method(*)

#### getDeclaredMethods() & getMethods()

🍊 获取所有方法(包括私有)

![](/static/2020-08-16-17-16-22.png)
![](/static/2020-08-16-17-16-00.png)

* `getDeclaredMethods()`返回`Method[]`，所有方法
* `getMethods()`返回`Method[]`，public方法

🍊 获取方法信息

* `Modifier.toString(m.getModifiers())`修饰符列表(多个)，`int`
* `m.getReturnType().getSimpleName()`方法返回值，`Class<?>`
* `m.getName()`方法名，`String`
* `m.getParameterTypes()`参数列表(多个)，`Class[]`
  * 遍历`Class[] parameters`，`para.getSimpleName()`

```java
Class c = Class.forName(...);
Method[] methods = c.getDeclaredMethods();
for(Method m: methods){
  System.out.println(Modifier.toString(m.getModifiers()));// 修饰符列表
  System.out.println(m.getReturnType().getSimpleName());// 方法返回值类型
  System.out.println(m.getName());// 方法名
  System.out.println(m.getParameterTypes());// 参数列表(可能多个)

}
```

#### call the methods accords to reflection

🍊 重点：通过反射调用方法

![](/static/2020-08-16-18-14-21.png)
![](/static/2020-08-16-18-16-49.png)

* 获取特定方法`getDeclaredMethod​(String name, Class<?>... parameterTypes)`，返回`Method`
* 调用方法`m.invoke()`返回方法值`Object`，传实参。`对象，参数...`

```java
Class c = Class.forName(...);
Object obj = c.newInstance();
Method m = c.getDeclaredMethod("login",String.class,String.class);// 获取login方法
```

#### Constructors

🍊 获取构造方法

![](/static/2020-08-16-18-22-56.png)
![](/static/2020-08-16-18-23-05.png)

* 已知`class.newInstance()`调用无参构造
* `getConstructors()`所有public构造函数，返回`Constructor<?>[]`
* `getDeclaredConstructors()`所有构造函数，返回`Constructor<?>[]`

🍊 构造方法信息

* 修饰符`Modifier.toString(con.getModifiers())`
* 名称`class.getSimpleName()`
* 参数`cons.getParameterTypes()`返回`Class[]`
  * 遍历，调用`type.getSimpleName()`

🍊 通过反射调用无参构造方法

* `class.newInstance()`

🍊 通过反射调用有参构造方法

* 先获取有参构造方法，**指定传参类型**

```java
class.getDeclaredConstructor(int.class, String.class,...);
```

* 获取实例对象`obj = class.newInstance()`，并传实参

```java
Object obj = class.newInstance(110,"abc");
```

### get Parent & Interface

🍊 给定一个类，如何获取父类？

![](/static/2020-08-16-19-18-36.png)

```java
/*
假设获取String父类
*/
Class strClass = Class.forName("java.lang.String");
Class parent =  strClass.getSuperClass();
String name = parent.getSimpleName();
String name = parent.getName();
```

🍊 获取类的实现的所有接口？

![](/static/2020-08-16-19-24-15.png)

```java
/*
假设获取String接口
*/
Class strClass = Class.forName("java.lang.String");
Class[] interfaces = strClass.getInterfaces();
for(Class in: interfaces){
  System.out.println(in.getSimpleName());
  System.out.println(in.getName());
}
```

### Decompile

🍊 反编译

![](/static/2020-08-16-16-05-24.png)

## Annotation

🍬 元注解

![](/static/2020-08-16-22-45-07.png)

* 标注注解类型的注解
* `@Target`【接收value数组ElementType枚举属性，属性可并排，免写value】
  * 用来标注，某个注解可以出现的位置
  * `@Target(ElementType.METHOD)`被标注的注解只能出现在方法上
  * `@Target(ElementType.TYPE)`被标注的注解只能出现在类上
  * ![](/static/2020-08-17-01-03-32.png)
* `@Retention`
  * 标注，某个注解最终保存的位置
  * `@Retention(RetentionPolicy.SOURCE)`该注解只保留在java源文件中
  * `@Retention(RetentionPolicy.CLASS)`该注解只保留在class文件中
  * `@Retention(RetentionPolicy.RUNTIME)`该注解只保留在class文件中并且可以被反射机制触发
  * ![](/static/2020-08-17-01-10-08.png)

🍊 其他元注解

* `@Documented`
  * 跟 Javadoc 的作用是差不多的，其实它存在的好处是开发人员可以定制Javadoc 不
支持的文档属性，并在开发中应用
  * ![](/static/2020-08-17-01-13-00.png)
* `@Inherited`
  * 默认父类中的 Annotation 并不会被继承到子类中
  * <font color="red">加上后，您定义的Annotation类型在被继承后仍可以保留至子类中</font>
  * ![](/static/2020-08-17-01-15-50.png)

🍊 注解

* 引用类型
* 编译后也为`.class`文件
* 自定义注解类语法
  * `[修饰符列表] @interface 注解类型名{}`
* 使用方法
  * `@注解类型名`
  * 可用于类，属性，方法，变量，注解类型上

🍊 JDK内置注解`java.lang`

* `@Deprecated`
  * 标注过时方法，编译时提示信息
* `@Override`
  * 只能注解方法，给编译器参考，会进行编译检查。如果方法名不属于父类重写，编译器报错
* `@SuppressWarnings`，需要1个额外参数
  * 它对编译器说明某个方法中**若有警告信息，则加以抑制**，不用在编译完成后出现警告
  * ![](/static/2020-08-17-01-01-26.png)
  * ![](/static/2020-08-17-01-07-26.png)

### use

注解作用

![](/static/2020-08-17-00-59-26.png)

### Fields

🍊 注解中可以定义属性

![](/static/2020-08-16-23-07-53.png)

🍬 如果一个注解中有属性，必须给属性赋值（除非有`default`默认值）

![](/static/2020-08-16-23-09-15.png)
![](/static/2020-08-16-23-11-15.png)
![](/static/2020-08-16-23-17-54.png)
![](/static/2020-08-16-23-24-20.png)

* `@Annotation(field1=xxx, field2=xxx)`
  * 如为数组，`@Annotation(field={xxx,xxx})`
  * 如数组中只有一个元素，则大括号可省略
* 如果属性有默认值，则可无视
* <font color="red">属性名只有`value`时，可以直接写值，而不用写`value=xxx`</font>
* <font color="blue">属性类型只能为，【基本类型，Class，String，枚举】及其数组形式</font>

---

### Reflection of Annotation

🍬 <font color="red">前提是注解类的元注解声明，该注解保存在class文件中，且能响应反射</font>

* `@Retention(RetentionPolicy.RUNTIME)`

🍊 反射获取注解 & 获取注解属性

![](/static/2020-08-17-00-03-15.png)
![](/static/2020-08-17-00-03-37.png)

```java
Class c = Class.forName(...);
c.isAnnotationPresent(MyAnnotation.class);//判断c类上是否有MyAnnotation注解
MyAnnotation my = c.getAnnotation(MyAnnotation.class);//注解对象
String a = myAnnotation.value();// 获取value属性

```

* `isAnnotationPresent​(Class<? extends Annotation> annotationClass)`
* `getAnnotation​(Class<A> annotationClass)`

---

🍊 获取某类方法上的完整的注解信息

![](/static/2020-08-17-00-08-23.png)

```java
Class c = Class.forName(...);
Method m = c.getDeclaredMethod("doSome");//获取该类doSome方法

//判断该方法是否被MyAnnotation注解
if(m.isAnnotationPresent(MyAnnotation.class)){
  MyAnnotation my = m.getAnnotation(MyAnnotation.class);//获取注解对象
  my.username();//注解的username属性
  my.password();//注解password属性
}
```