# Revision

Part 3: Path, Reflection, Annotation, JDK14 new traits

![](/static/2020-08-16-18-27-19.png)

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