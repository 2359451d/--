# Xaml Basics

[参考](https://docs.microsoft.com/zh-cn/xamarin/xamarin-forms/xaml/xaml-basics/)

* [Xaml Basics](#xaml-basics)
* [入门](#入门)
  * [文件解析](#文件解析)
    * [Page Content](#page-content)
  * [页面导航](#页面导航)
* [重要语法](#重要语法)
  * [设置属性](#设置属性)
  * [附加属性：Attached Attribute](#附加属性attached-attribute)
  * [跨平台属性](#跨平台属性)
* [Xaml标记扩展](#xaml标记扩展)
  * [x:Static标记扩展](#xstatic标记扩展)
  * [其他标记](#其他标记)
  * [约束表达式标记扩展: ConstraintExpression Markup Extension](#约束表达式标记扩展-constraintexpression-markup-extension)
* [数据绑定](#数据绑定)
  * [V-V绑定](#v-v绑定)
  * [绑定模式](#绑定模式)
  * [ListView:集合绑定](#listview集合绑定)
  * [值转换器绑定:Value Converters](#值转换器绑定value-converters)
* [MVVM模式](#mvvm模式)
  * [交互型MVVM：属性更新](#交互型mvvm属性更新)
  * [ViewModel进行命令](#viewmodel进行命令)
    * [命令调用异步方法](#命令调用异步方法)
  * [导航菜单实现](#导航菜单实现)
* [XAML控件](#xaml控件)
* [XAML标记扩展](#xaml标记扩展-1)
  * [x:Static](#xstatic)
  * [x:Reference](#xreference)
  * [x:Type](#xtype)

# 入门

> XAML 非常适合用于常见 MVVM (ViewModel) 应用程序体系结构： XAML 定义通过基于 XAML 的数据绑定链接到 ViewModel 代码的视图

重点语法

* 属性元素
* 附加属性
* 标记扩展

## 文件解析

![](/static/2021-01-09-18-12-50.png)

* `xmlns`命名空间
  * 声明引用
* `x:Class`命名空间
  * `x`用于xaml自身固有的元素&属性
  * 如x:class="XamlSamples.Mainpage"允许访问该命名空间内的MainPage class，意味着从XamlSamples中的contentpage中派生出Mainpage页
* `local`命名空间，允许访问.NET Standard库项目中其他类

```c#
using Xamarin.Forms;

namespace XamlSamples
{
    public partial class MainPage : ContentPage
    {
        public MainPage()
        {
            InitializeComponent();
        }
    }
}
```

* `partial`意味着MainPage还需要有另外的partial class**部分类**
  * 主要应用于搭建框架，实现使用模板生成的与用户自定义的分开
  * 部分类可以同时编译

### Page Content

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="XamlSamples.HelloXamlPage"
             Title="Hello XAML Page">
    <ContentPage.Content>

        <Label Text="Hello, XAML!"
               VerticalOptions="Center"
               HorizontalTextAlignment="Center"
               Rotation="-15"
               IsVisible="true"
               FontSize="Large"
               FontAttributes="Bold"
               TextColor="Blue" />

    </ContentPage.Content>
</ContentPage>
```

* `ContentPage.Content`属性元素标签 property element tag
* `Title`set on the root tag

## 页面导航

page navigation

```c#
public App()
{
 //根页面mainpage，通过button导航至helloxamlpage
    InitializeComponent();
    MainPage = new NavigationPage(new MainPage());
}
```

```c#
public MainPage()
{
    InitializeComponent();

// 给mainpage添加button，event handler
    Button button = new Button
    {
        Text = "Navigate!",
        HorizontalOptions = LayoutOptions.Center,
        VerticalOptions = LayoutOptions.Center
    };

// clicked事件，lambda
    button.Clicked += async (sender, args) =>
    {
        // 导航至helloxamlpage
        await Navigation.PushAsync(new HelloXamlPage());
    };
    //contentpage.content属性重置
    Content = button;
}
```

# 重要语法

## 设置属性

不同方式

1. Property element方式
   1. 适用于value过于冗长时

```xaml
<Label Text="Hello, XAML!"
       VerticalOptions="Center"
       FontAttributes="Bold"
       FontSize="Large">
<!-- 打开标签，定义属性
    该种方式下，label是object element，前面的属性成为 property attributes
    TextColor是 property （XML）element
 -->
    <Label.TextColor>
        Aqua
    </Label.TextColor>
</Label>

```

## 附加属性：Attached Attribute

如 `Grid.Column`,`Grid.Row`

* Grid并没有定义这些属性，但是定义了4个可**绑定属性**`RowProperty, ColumnProperty, RowSpanProperty, and ColumnSpanProperty`
  * 即，附加属性，用Grid类定义，附加在的子类（即Grid中存放的组件）中

`AbsoluteLayout`定义2个附加属性

* `LayoutBounds` & `LayoutFlags`
  * 如果有过多重复嵌套，后期可以优化（在code-behind file中编写组件）

## 跨平台属性

`OnPlatform`

```c#
// code-behind
if (Device.RuntimePlatform == Device.iOS)
{
    Padding = new Thickness(0, 20, 0, 0);
}
```

或xaml实现

* 使用`OnPlatForm`泛型类
  * 需要制定**泛型参数 **`TypeArguments`
  * 如`Padding`属性的类型 - `Thickness`
* `OnPlatForm`泛型类有属性
  * `Platforms`  an `IList` of `On` objects

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="...">

    <ContentPage.Padding>
        <OnPlatform x:TypeArguments="Thickness">
            <On Platform="iOS" Value="0, 20, 0, 0" />
            <On Platform="Android, UWP" Value="0, 0, 0, 0" />
        </OnPlatform>
    </ContentPage.Padding>
  ...
</ContentPage>
```

或写为以下形式

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="...">

    <ContentPage.Padding>
        <OnPlatform x:TypeArguments="Thickness">
            <On Platform="iOS">
                <On.Value>
                    0, 20, 0, 0
                </On.Value>
            </On>
        </OnPlatform>
    </ContentPage.Padding>
  ...
</ContentPage>

```

# Xaml标记扩展

用于data binding，对象共享，参考常量

**资源字典- resource dictionary**

* string-object键值对
  * **可在页面中使用资源字典**，需要在`Resources`属性元素(定义在`VisualElement`)中包括ResourceDictioanry标签
  * 每个types存入必须有 `x:Key`，值必须初始化
* 引用使用 - `{StaticResource}`引用
  * 用于区分动态资源

> 因为Resource定义在VisualElement中,所以大多元素都可以单独定义其专属ResourceDictionary

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="XamlSamples.SharedResourcesPage"
             Title="Shared Resources Page">

    <ContentPage.Resources>
        <ResourceDictionary>
        <!-- 设定类型&键值对 -->
            <LayoutOptions x:Key="horzOptions"
                           Alignment="Center" />

            <LayoutOptions x:Key="vertOptions"
                           Alignment="Center"
                           Expands="True" />
        </ResourceDictionary>
    </ContentPage.Resources>

    <!-- {StaticResource进行引用} -->
    <Button Text="Do this!"
        HorizontalOptions="{StaticResource horzOptions}"
        VerticalOptions="{StaticResource vertOptions}"
        BorderWidth="3"
        Rotation="-15"
        TextColor="Red"
        FontSize="24" />
</ContentPage>
```

**常见数据类型定义**

* `x:Double`
* `x:Int32`
* `Color`
  * `x:Key="textColor"`

```xaml
<ContentPage.Resources>
    <ResourceDictionary>
         <x:Double x:Key="borderWidth">
            3
         </x:Double>

<!-- 单行 -->
        <x:Double x:Key="rotationAngle">-15</x:Double>

        <Color x:Key="textColor">Red</Color>

        <x:Double x:Key="fontSize">24</x:Double>
        
        <OnPlatform x:Key="textColor"
            x:TypeArguments="Color">
            <On Platform="iOS" Value="Red" />
            <On Platform="Android" Value="Aqua" />
            <On Platform="UWP" Value="#80FF80" />
        </OnPlatform>

    </ResourceDictionary>
</ContentPage.Resources>

<!-- referenced -->
<Button Text="Do this!"
        HorizontalOptions="{StaticResource horzOptions}"
        VerticalOptions="{StaticResource vertOptions}"
        BorderWidth="{StaticResource borderWidth}"
        Rotation="{StaticResource rotationAngle}"
        TextColor="{StaticResource textColor}"
        FontSize="{StaticResource fontSize}" />
```

```xaml
<!-- Stacklayout专用resource -->
<StackLayout>
    <StackLayout.Resources>
        <ResourceDictionary>
            <Color x:Key="textColor">Blue</Color>
        </ResourceDictionary>
    </StackLayout.Resources>
    ...
</StackLayout>
```

## x:Static标记扩展

注意区分`StaticResource`(用于返回资源字典中对象,string-object) & `x:Static`

`x:Static`用于访问

* public static field
  * code-behind提供
* public static property
* public constant field
* 枚举类成员

```xaml
<!-- 显式参考枚举成员 & 静态字段 -->
<Label Text="Hello, XAML!"
       VerticalOptions="{x:Static LayoutOptions.Start}"
       HorizontalTextAlignment="{x:Static TextAlignment.Center}"
       TextColor="{x:Static Color.Aqua}" />
```

包含一些静态字段的静态类例子,可以供多个页面参考使用

* 在xaml中引用,只需要**指明file位置**
  * **xml命名空间声明**
* 如引用本地类`AppConstants`, 使用`xmlns:local`,local前缀
  * `xmlns:local="clr-namespace:XamlSamples"`冒号后为**NET命名空间**,即C#中namespace或using定义
* [使用其他程序集请参考](https://docs.microsoft.com/en-gb/xamarin/xamarin-forms/xaml/xaml-basics/xaml-markup-extensions#feedback)
  * `xmlns:sys="clr-namespace:System;assembly=netstandard"`

```c#
using System;
using Xamarin.Forms;

namespace XamlSamples
{
    static class AppConstants
    {
        // 静态字段
        public static readonly Thickness PagePadding;

        public static readonly Font TitleFont;

        public static readonly Color BackgroundColor = Color.Aqua;

        public static readonly Color ForegroundColor = Color.Brown;

        static AppConstants()
        {
            switch (Device.RuntimePlatform)
            {
                case Device.iOS:
                    PagePadding = new Thickness(5, 20, 5, 0);
                    TitleFont = Font.SystemFontOfSize(35, FontAttributes.Bold);
                    break;

                case Device.Android:
                    PagePadding = new Thickness(5, 0, 5, 0);
                    TitleFont = Font.SystemFontOfSize(40, FontAttributes.Bold);
                    break;

                case Device.UWP:
                    PagePadding = new Thickness(5, 0, 5, 0);
                    TitleFont = Font.SystemFontOfSize(50, FontAttributes.Bold);
                    break;
            }
        }
    }
}
```

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
            <!-- 本地类 -->
             xmlns:local="clr-namespace:XamlSamples"
             <!-- 系统类,程序集指定 -->
             xmlns:sys="clr-namespace:System;assembly=netstandard"
             x:Class="XamlSamples.StaticConstantsPage"
             Title="Static Constants Page"
             Padding="{x:Static local:AppConstants.PagePadding}">

    <StackLayout>
       <Label Text="Hello, XAML!"
              TextColor="{x:Static local:AppConstants.BackgroundColor}"
              BackgroundColor="{x:Static local:AppConstants.ForegroundColor}"
              Font="{x:Static local:AppConstants.TitleFont}"
              HorizontalOptions="Center" />

<!-- 引用sys中Math.PI,Math.E, 最后scale100 -->
      <BoxView WidthRequest="{x:Static sys:Math.PI}"
               HeightRequest="{x:Static sys:Math.E}"
               Color="{x:Static local:AppConstants.ForegroundColor}"
               HorizontalOptions="Center"
               VerticalOptions="CenterAndExpand"
               Scale="100" />
    </StackLayout>
</ContentPage>
```

## 其他标记

* `{x:Null}`
  * 如某属性默认非null值,可以设为null
* `{x:Type someClass}`
  * 如某属性为`Type`,可以分配给一个`Type`对象
* `x:Array`定义数组
  * 有`Type`属性,需要指明数组类型
* `Binding`数据绑定用
* `RelativeSource`相对数据绑定?

## 约束表达式标记扩展: ConstraintExpression Markup Extension

标记扩展可以有属性

* 用`,`隔开
* `ConstraintExpression`用于相对布局类中,`RelativeLayout`
  * 约束表达允许使用`Factor`*其他view的属性来设置一个view的位置/大小

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="XamlSamples.RelativeLayoutPage"
             Title="RelativeLayout Page">

    <RelativeLayout>

        <!-- Upper left -->
        <BoxView Color="Red"
                 RelativeLayout.XConstraint=
                     "{ConstraintExpression Type=Constant,
                                            Constant=0}"
                 RelativeLayout.YConstraint=
                     "{ConstraintExpression Type=Constant,
                                            Constant=0}" />
        <!-- Upper right -->
        <BoxView Color="Green"
                 RelativeLayout.XConstraint=
                     "{ConstraintExpression Type=RelativeToParent,
                                            Property=Width,
                                            Factor=1,
                                            Constant=-40}"
                 RelativeLayout.YConstraint=
                     "{ConstraintExpression Type=Constant,
                                            Constant=0}" />
        <!-- Lower left -->
        <BoxView Color="Blue"
                 RelativeLayout.XConstraint=
                     "{ConstraintExpression Type=Constant,
                                            Constant=0}"
                 RelativeLayout.YConstraint=
                     "{ConstraintExpression Type=RelativeToParent,
                                            Property=Height,
                                            Factor=1,
                                            Constant=-40}" />
        <!-- Lower right -->
        <BoxView Color="Yellow"
                 RelativeLayout.XConstraint=
                     "{ConstraintExpression Type=RelativeToParent,
                                            Property=Width,
                                            Factor=1,
                                            Constant=-40}"
                 RelativeLayout.YConstraint=
                     "{ConstraintExpression Type=RelativeToParent,
                                            Property=Height,
                                            Factor=1,
                                            Constant=-40}" />

        <!-- Centered and 1/3 width and height of parent -->
        <BoxView x:Name="oneThird"
                 Color="Red"
                 RelativeLayout.XConstraint=
                     "{ConstraintExpression Type=RelativeToParent,
                                            Property=Width,
                                            Factor=0.33}"
                 RelativeLayout.YConstraint=
                     "{ConstraintExpression Type=RelativeToParent,
                                            Property=Height,
                                            Factor=0.33}"
                 RelativeLayout.WidthConstraint=
                     "{ConstraintExpression Type=RelativeToParent,
                                            Property=Width,
                                            Factor=0.33}"
                 RelativeLayout.HeightConstraint=
                     "{ConstraintExpression Type=RelativeToParent,
                                            Property=Height,
                                            Factor=0.33}"  />

        <!-- 1/3 width and height of previous -->
        <BoxView Color="Blue"
                 RelativeLayout.XConstraint=
                     "{ConstraintExpression Type=RelativeToView,
                                            ElementName=oneThird,
                                            Property=X}"
                 RelativeLayout.YConstraint=
                     "{ConstraintExpression Type=RelativeToView,
                                            ElementName=oneThird,
                                            Property=Y}"
                 RelativeLayout.WidthConstraint=
                     "{ConstraintExpression Type=RelativeToView,
                                            ElementName=oneThird,
                                            Property=Width,
                                            Factor=0.33}"
                 RelativeLayout.HeightConstraint=
                     "{ConstraintExpression Type=RelativeToView,
                                            ElementName=oneThird,
                                            Property=Height,
                                            Factor=0.33}"  />
    </RelativeLayout>
</ContentPage>

```

# 数据绑定

xaml也提供绑定,`{Binding}`forms中最重要的扩展标记之一

两个基本步骤

* 目标对象的`BindingContext`绑定到源对象
* `SetBinding`方法,通常与`Binding`类一起使用,
  * 必须在目标对象上调用,建立源属性与目标对象的属性之间的绑定
  * **xaml中使用`{Binding}`扩展标记,代替`SetBinding`的调用 & Binding类**

## V-V绑定

view-2-view binding

可以绑定同一页面的2个view的属性

* 此例, 使用`x:Reference`**扩展标记**设定绑定目标的`BindingContext`属性
  * `x:Reference`扩展标记有属性`Name`,指定引用的view
  * 属性`ContentProperty`也可以引用name
  * <font color="red">因为作用一样,所以不用显式写明</font>

```xaml
BindingContext="{x:Reference Name=slider}"
…
BindingContext="{x:Reference slider}"
```

> x:Reference的ContentProperty/Name用于指定绑定数据源的引用名，可以省略
类似的，**Binding扩展标记用Path指定数据源引用名 {Binding Path=...}**,,如果引用名是第一个属性则可省

```xaml
<!-- 注意forms绑定不会提供隐式类型转,
如果要演示非string对象,
需要提供type converter
或使用StringFormat -->
Text="{Binding Value, StringFormat='The angle is {0:F0} degrees'}"
```

---

:orange: 下例, slider变化,两个label view, 一个旋转,一个显示value

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="XamlSamples.SliderBindingsPage"
             Title="Slider Bindings Page">

    <StackLayout>
    <!-- BindingContext属性绑定数据源 -->
        <Label Text="ROTATION"
               BindingContext="{x:Reference Name=slider}"
               Rotation="{Binding Path=Value}"
               FontAttributes="Bold"
               FontSize="Large"
               HorizontalOptions="Center"
               VerticalOptions="CenterAndExpand" />

<!-- 别名 -->
        <Slider x:Name="slider"
                Maximum="360"
                VerticalOptions="CenterAndExpand" />

    <!-- BindingContext属性绑定数据源 -->
        <Label BindingContext="{x:Reference slider}"
               Text="{Binding Value, StringFormat='The angle is {0:F0} degrees'}"
               FontAttributes="Bold"
               FontSize="Large"
               HorizontalOptions="Center"
               VerticalOptions="CenterAndExpand" />
    </StackLayout>
</ContentPage>
```

## 绑定模式

> 通常单个view的多个属性都可绑数据,**然而每个view只能有一个BindingContext(指定一个相同数据源)**,如何绑定不同数据源?

使用`Mode`属性解决,

* 值为`BindingMode`枚举类成员
  * `Default`
  * `OneWay` source-target
  * `OneWayToSource` target-source
  * `TwoWay` 双向传值
  * `OneTime` source-target,仅当`BindingContext`改变时数据流动

:orange:例子: **4个slider** view控制label的 scale,rotate,rotatex,rotatey属性(注意一个labelview只能绑1个源)

![](/static/2021-01-10-00-37-04.png)

* 考虑为4个slider绑定源(利用`BindingContext`&`Mode`),设置绑定模式,控制数据流向
  * `OneWayToSource`,绑源为label,数据从target(slider流向label)

## ListView:集合绑定

templated listview例子

* `ListView` defines an `ItemsSource` property of type `IEnumerable`
  * **有`ItemsSource`属性,指定数据源(`IEnumerable<T>`枚举集合)**
    * 注意可以写为property element/attribute
  * <font color="red">如需要运行时改变,`ItemsSource`属性使用`ObservableCollection`(实现`INotifyCollectionChanged`接口)</font>
    * ListView为item本身注册CollectionChanged事件
    * 如果item的属性runtime改变,需要改写`PropertyChanged`事件
* 可以使用模板,被每个item利用
  * 并且,为模板设定数据绑定

```xaml
//引用本地类 XamlSamples,其中有NamedColor静态类,提供静态枚举集合成员
xmlns:local="clr-namespace:XamlSamples;assembly=XamlSamples"

<!-- property attribute示例 -->
<ListView ItemsSource="{x:Static local:NamedColor.All}" />

```

> ListView的DataTemplate需要利用ItemTplate属性（元素标签）
，且设为DataTemplate，引用ViewCell。最后设定ViewCell.View属性定义每个Cell的布局(View属性可以省略,因为View是ViewCell的内容属性)

![](/static/2021-01-10-00-59-39.png)

## 值转换器绑定:Value Converters

原颜色是double类型，转换成16进制值，不能单纯使用stringformat实现，（因为只支持int）

* 所以需要使用doubletoint**值转换器(又称绑定转换器)**,先转换成int
* 通过`{Binding ..., Converter=..., ConverterParameter}`指定转换器 & 参数

:orange:下例为 `IValueConverter`接口的实现类,方法

* `Convert`
  * source-target
* `ConvertBack`
  * target-source
  * `OneWayToSource`或`TwoWay`模式

```c#
using System;
using System.Globalization;
using Xamarin.Forms;

namespace XamlSamples
{
    class DoubleToIntConverter : IValueConverter
    {
        public object Convert(object value, Type targetType,
                              object parameter, CultureInfo culture)
        {
            double multiplier;

// 检查parameter是否为合法double
            if (!Double.TryParse(parameter as string, out multiplier))
                multiplier = 1;

            return (int)Math.Round(multiplier * (double)value);
        }

        public object ConvertBack(object value, Type targetType,
                                  object parameter, CultureInfo culture)
        {
            double divider;

            if (!Double.TryParse(parameter as string, out divider))
                divider = 1;

            return ((double)(int)value) / divider;
        }
    }
}
```

```xaml
<local:DoubleToIntConverter x:Key="intConverter" />
<Label Text="{Binding Color.R,
        Converter={StaticResource intConverter},
        ConverterParameter=255,
        <!-- stringformat转换器将int转为16进制 -->
       StringFormat='R={0:X2}'}" />

```

* 本地类提供的转换器定义在page的资源字典中,供多绑定/引用

# MVVM模式

> view和viewmodel通常通过数据绑定连接在一起
>
> View的BindingContext通常设为ViewModel的实例（作为绑定数据源）
>
> View的属性通常值绑定ViewModel的属性(通过数据绑定,取源对象的数据刷新目标对象的属性值)

`xmlns:sys="clr-namespace:System;assembly=netstandard"`

`<StackLayout BindingContext="{x:Static sys:DateTime.Now}" …>`

* 定义xml命名空间，使xaml文件能供引用其他程序集中的class
  * 此例为System命名空间定义了一个xmlns:sys声明
* <font color="red">注意`xmlns:sys`,`xmlns:local`都需要通过`{x:Static}`</font>
  * 扩展标记引用，因为要引用这些本地类（或其他程序集）中的静态/常量/枚举字段

:orange:为元素设置`BindingContext`属性,绑定数据源

![](/static/2021-01-10-01-48-21.png)

* 子类会继承该属性的值
* 如果有的binding没有指明`Path`属性,可能表示绑定的就是源本身,而不是源的属性
* <font color="red">该例不涉及数据动态变化,数据只渲染一次(initialised)</font>

## 交互型MVVM：属性更新

MVVM通常用于基于model的交互型view的two way数据绑定

实现`INotifyPropertyChanged`接口，触发事件`OnPropertyChanged`

* 需要运用属性改变事件处理器`PropertyChangedEventHandler`，来触发`OnPropertyChanged`方法

:orange:绑定`BindingContext`

* 资源中绑定`ContentPage.BindingContext`
  * 可以设置ViewModel的字段初始值
* 在code-behind文件中初始化，绑定
* 为特定View的属性`BindingContext`绑定
  * 可写为属性元素/或propertyattribute

:orange:如果view为双向绑定

* 则其的值可以通过viewModel进行初始化
  * 如slider的value不初始化为0

## ViewModel进行命令

有时View需要包含会触发ViewModel中事件的按钮组件,

* 但是VM不能包含按钮的 `Clicked`事件处理器handler，因为会将VM关联到特定的UI模式
* 如果允许ViewModel独立于特定view但仍允许VM调用方法
  * 使用`command`&`commandParameter`参数
    * Command 类型为 `System.Windows.Input.ICommand`
    * CommandParameter 类型为 Object
  * 以下控件支持
    * `Button`
    * `MenuItem`
    * `ToolbarItem`
    * `SearchBar`
      * `SearchCommand`
      * `SearchCommandParameter`
    * `ListView`
      * `RefresshCommand`
      * `ICommand`
    * `TextCell/ImageCell`
    * `TapGestureRecognizer`

command需要实现`ICommand`接口，可以在VM中定义相关字段（定义一个命令）

* `void Execute(object arg)`
  * 特定view绑定command，每当用户点击view，将其传递给execute方法且传入commandParameter
* `bool CanExecute(object arg)`
  * 用于view点击可能无效的情况下，这种情况下view应该禁用自身
  * `canExecute`在`Command`属性第一次被设定&**`canExecuteChanged`事件触发时**调用
  * <font color="red">如`canExecute`返回`false`，view会禁用其自身，且不会调用`Execute`方法</font>
* `event EventHandler CanExecuteChanged`
  * <font color="blue">`ChangeCanExecute`方法可以强制Command对象触发`CanExecuteChanged`事件，（间接调用`canExecute`方法，决定view自身是否被禁用）</font>

> VM中定义command字段，类型为ICommand，最后在构造函数中初始化操作，实现该接口定义的方法
>
> VM类中定义属性字段，写好字段setter，getter。如果需要运行时修改属性值，需要定义PropertyChanged事件处理器进行属性更新

```c#
namespace XamlSamples
{

/* 
  InputString setter更新期间，会检测是否禁用控件，

  （通过对Delete命令的changecanExecute方法， ((Command)DeleteCharCommand).ChangeCanExecute();

  强制触发canExecuteChanged事件，间接强制调用canExecute方法进行InputString的检测，如果为空则禁用该view）
 */

  // 下类实现INotifyPropertyChanged类，属性改变触发PropertyChanged方法
    class KeypadViewModel : INotifyPropertyChanged
    {
        string inputString = "";
        string displayText = "";
        char[] specialChars = { '*', '#' };

    // 事件处理器，任意属性改变时会触发该事件，进行处理（属性更新）
        public event PropertyChangedEventHandler PropertyChanged;

        // Constructor
        public KeypadViewModel()
        {
          /* 
          add，delete命令触发inputstringsetter更新，然后通过onPropertyChanged更新该属性，格式化后更新给DisplayText（也是通过属性改变事件处理器更新，将前端的值进行更新）
           */

          //add命令初始化，lambda写法（key为CommandParameter）
          //利用button向InputString属性字段添加char，最后通过inputstring的setter格式化成phone number 将值赋给DisplayText属性
            AddCharCommand = new Command<string>((key) =>
                {
                    // Add the key to the input string.
                    InputString += key;
                });
          //命令初始化
            DeleteCharCommand = new Command(() =>
                {
                  //默认删除输入的string中的最后一位
                    // Strip a character from the input string.
                    InputString = InputString.Substring(0, InputString.Length - 1);
                },
                () =>
                {
                  //canExecute()事件，决定view是否被禁用
                    // Return true if there's something to delete.
                    return InputString.Length > 0;
                });
        }

        //字段 - 属性
        // Public properties
        public string InputString
        {
            protected set
            {
                if (inputString != value)
                {
                    inputString = value;
                    OnPropertyChanged("InputString");
                    DisplayText = FormatText(inputString);

                    // Perhaps the delete button must be enabled/disabled.
                    //如果没有内容删除时，禁用deleteChar命令（绑定至回退button上）
                    //changecanexectue事件调用时，触发canExecute方法
                    ((Command)DeleteCharCommand).ChangeCanExecute();
                }
            }

            get { return inputString; }
        }

        public string DisplayText
        {
            protected set
            {
                if (displayText != value)
                {
                    displayText = value;
                    //要改属性一定调用事件处理器，进行更新
                    OnPropertyChanged("DisplayText");
                }
            }
            get { return displayText; }
        }

        // ICommand implementations
        //在构造函数中初始化的命令
        //假设每个button绑定该命令，每个button作为参数传入，供辩别
        public ICommand AddCharCommand { protected set; get; }

        public ICommand DeleteCharCommand { protected set; get; }

        string FormatText(string str)
        {
            bool hasNonNumbers = str.IndexOfAny(specialChars) != -1;
            string formatted = str;

            if (hasNonNumbers || str.Length < 4 || str.Length > 10)
            {
            }
            else if (str.Length < 8)
            {
                formatted = String.Format("{0}-{1}",
                                          str.Substring(0, 3),
                                          str.Substring(3));
            }
            else
            {
                formatted = String.Format("({0}) {1}-{2}",
                                          str.Substring(0, 3),
                                          str.Substring(3, 3),
                                          str.Substring(6));
            }
            return formatted;
        }

      //属性改变事件处理器，任意属性改变时出发事件
        protected void OnPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
```

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             xmlns:local="clr-namespace:XamlSamples;assembly=XamlSamples"
             x:Class="XamlSamples.KeypadPage"
             Title="Keypad Page">

    <Grid HorizontalOptions="Center"
          VerticalOptions="Center">
          <!-- 绑定Vm -->
        <Grid.BindingContext>
            <local:KeypadViewModel />
        </Grid.BindingContext>

        <Grid.RowDefinitions>
            <RowDefinition Height="Auto" />
            <RowDefinition Height="Auto" />
            <RowDefinition Height="Auto" />
            <RowDefinition Height="Auto" />
            <RowDefinition Height="Auto" />
        </Grid.RowDefinitions>

        <Grid.ColumnDefinitions>
            <ColumnDefinition Width="80" />
            <ColumnDefinition Width="80" />
            <ColumnDefinition Width="80" />
        </Grid.ColumnDefinitions>

        <!-- Internal Grid for top row of items -->
        <Grid Grid.Row="0" Grid.Column="0" Grid.ColumnSpan="3">
            <Grid.ColumnDefinitions>
                <ColumnDefinition Width="*" />
                <ColumnDefinition Width="Auto" />
            </Grid.ColumnDefinitions>

            <Frame Grid.Column="0"
                   OutlineColor="Accent">
                   <!-- 绑定VM显示的字段， source->target更新label -->
                <Label Text="{Binding DisplayText}" />
            </Frame>

            <Button Text="&#x21E6;"
            <!-- 绑定命令 -->
                    Command="{Binding DeleteCharCommand}"
                    Grid.Column="1"
                    BorderWidth="0" />
        </Grid>

        <!-- 每个数字键绑定命令 -->
        <Button Text="1"
                Command="{Binding AddCharCommand}"
                CommandParameter="1"
                Grid.Row="1" Grid.Column="0" />

        <Button Text="2"
                Command="{Binding AddCharCommand}"
                CommandParameter="2"
                Grid.Row="1" Grid.Column="1" />

        <Button Text="3"
                Command="{Binding AddCharCommand}"
                CommandParameter="3"
                Grid.Row="1" Grid.Column="2" />

        <Button Text="4"
                Command="{Binding AddCharCommand}"
                CommandParameter="4"
                Grid.Row="2" Grid.Column="0" />

        <Button Text="5"
                Command="{Binding AddCharCommand}"
                CommandParameter="5"
                Grid.Row="2" Grid.Column="1" />

        <Button Text="6"
                Command="{Binding AddCharCommand}"
                CommandParameter="6"
                Grid.Row="2" Grid.Column="2" />

        <Button Text="7"
                Command="{Binding AddCharCommand}"
                CommandParameter="7"
                Grid.Row="3" Grid.Column="0" />

        <Button Text="8"
                Command="{Binding AddCharCommand}"
                CommandParameter="8"
                Grid.Row="3" Grid.Column="1" />

        <Button Text="9"
                Command="{Binding AddCharCommand}"
                CommandParameter="9"
                Grid.Row="3" Grid.Column="2" />

        <Button Text="*"
                Command="{Binding AddCharCommand}"
                CommandParameter="*"
                Grid.Row="4" Grid.Column="0" />

        <Button Text="0"
                Command="{Binding AddCharCommand}"
                CommandParameter="0"
                Grid.Row="4" Grid.Column="1" />

        <Button Text="#"
                Command="{Binding AddCharCommand}"
                CommandParameter="#"
                Grid.Row="4" Grid.Column="2" />
    </Grid>
</ContentPage>
```

### 命令调用异步方法

命令也可以调用异步方法

* 为`Execute`或`CanExecute`方法定义时加上`async/await`关键字

`DownloadCommand = new Command (async () => await DownloadAsync ());`

```c#
// 异步方法应为Task（并发执行）
async Task DownloadAsync ()
{
    await Task.Run (() => Download ());
}

void Download ()
{
    ...
}
```

## 导航菜单实现

```c#
public class PageDataViewModel
{
    public PageDataViewModel(Type type, string title, string description)
    {
      // 以下字段为每个示例页的type，title，description
        Type = type;
        Title = title;
        Description = description;
    }

    public Type Type { private set; get; }
    public string Title { private set; get; }
    public string Description { private set; get; }

// All静态属性，所有页面的集合，默认构造
    static PageDataViewModel()
    {
        All = new List<PageDataViewModel>
        {
            // Part 1. Getting Started with XAML
            new PageDataViewModel(typeof(HelloXamlPage), "Hello, XAML",
                                  "Display a Label with many properties set"),

            new PageDataViewModel(typeof(XamlPlusCodePage), "XAML + Code",
                                  "Interact with a Slider and Button"),

            // Part 2. Essential XAML Syntax
            new PageDataViewModel(typeof(GridDemoPage), "Grid Demo",
                                  "Explore XAML syntax with the Grid"),

            new PageDataViewModel(typeof(AbsoluteDemoPage), "Absolute Demo",
                                  "Explore XAML syntax with AbsoluteLayout"),

            // Part 3. XAML Markup Extensions
            new PageDataViewModel(typeof(SharedResourcesPage), "Shared Resources",
                                  "Using resource dictionaries to share resources"),

            new PageDataViewModel(typeof(StaticConstantsPage), "Static Constants",
                                  "Using the x:Static markup extensions"),

            new PageDataViewModel(typeof(RelativeLayoutPage), "Relative Layout",
                                  "Explore XAML markup extensions"),

            // Part 4. Data Binding Basics
            new PageDataViewModel(typeof(SliderBindingsPage), "Slider Bindings",
                                  "Bind properties of two views on the page"),

            new PageDataViewModel(typeof(SliderTransformsPage), "Slider Transforms",
                                  "Use Sliders with reverse bindings"),

            new PageDataViewModel(typeof(ListViewDemoPage), "ListView Demo",
                                  "Use a ListView with data bindings"),

            // Part 5. From Data Bindings to MVVM
            new PageDataViewModel(typeof(OneShotDateTimePage), "One-Shot DateTime",
                                  "Obtain the current DateTime and display it"),

            new PageDataViewModel(typeof(ClockPage), "Clock",
                                  "Dynamically display the current time"),

            new PageDataViewModel(typeof(HslColorScrollPage), "HSL Color Scroll",
                                  "Use a view model to select HSL colors"),

            new PageDataViewModel(typeof(KeypadPage), "Keypad",
                                  "Use a view model for numeric keypad logic")
        };
    }

// All静态属性，所有页面的集合，用于绑定给ListBox的ItemsSource属性，作为来源集合
// TextCell绑定Title Description
    public static IList<PageDataViewModel> All { private set; get; }
}
```

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             xmlns:local="clr-namespace:XamlSamples"
             x:Class="XamlSamples.MainPage"
             Padding="5, 0"
             Title="XAML Samples">
<!-- 绑定静态字段All -->
    <ListView ItemsSource="{x:Static local:PageDataViewModel.All}"
    //选中任意Item，调用OnListViewItemSeleted方法
              ItemSelected="OnListViewItemSelected">
        <ListView.ItemTemplate>
            <DataTemplate>
                <TextCell Text="{Binding Title}"
                          Detail="{Binding Description}" />
            </DataTemplate>
        </ListView.ItemTemplate>
    </ListView>
</ContentPage>
```

```c#
// code-behind
//当选中LIstView中item时调用
private async void OnListViewItemSelected(object sender, SelectedItemChangedEventArgs args)
{
  // 将前端的SeletedItem重置
    (sender as ListView).SelectedItem = null;

// 处理传过来的SeletedItem
    if (args.SelectedItem != null)
    {
      // 选中的Item每个都是一个VM实例
        PageDataViewModel pageData = args.SelectedItem as PageDataViewModel;
        // 实例化所选页面（根据其Type），进行重导航到该page
        Page page = (Page)Activator.CreateInstance(pageData.Type);
        await Navigation.PushAsync(page);
    }
}
```

# XAML控件

views又称controls控件，widgets组件

[详情参考](https://docs.microsoft.com/zh-cn/xamarin/xamarin-forms/xaml/xaml-controls)

# XAML标记扩展

标记扩展，允许从text以外的源设置元素属性

```xaml
<!-- 以下color属性，可能更愿意从资源字典中读值，
或从已创建的VM类静态属性值？
或从page中另一个view的属性读color类型
或结合色调，饱和度，发光值构造设置color
 -->
<BoxView Color="Blue" />
<BoxView Color="#FF0080" />

<!-- 大括号中任何设置都为XAML标记扩展，不过也可以不使用大括号定义扩展 -->
<BoxView Color="{StaticResource themeColor}" />

```

## x:Static

由`StaticExtension`类支持，

* 定义string类型的`Member`属性
  * 用于设置为静态字段，枚举成员的名称等

:orange:使用方式

* 先定义一个具有常量，静态变量的类

```c#
static class AppConstants
{
    public static double NormalFontSize = 18;
}
```

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             xmlns:sys="clr-namespace:System;assembly=netstandard"
             xmlns:local="clr-namespace:MarkupExtensions"
             x:Class="MarkupExtensions.StaticDemoPage"
             Title="x:Static Demo">
    <StackLayout Margin="10, 0">
        <Label Text="Label No. 1">
            <Label.FontSize>
               <!-- 引用local命名空间中定义的静态字段，不使用大括号定义（详细） -->
                <x:StaticExtension Member="local:AppConstants.NormalFontSize" />

                <!-- 允许简写成Static -->
                <x:Static Member="local:AppConstants.NormalFontSize" />
            </Label.FontSize>
        </Label>
        ···
    </StackLayout>
</ContentPage>
```

```xaml
<!-- 使用大括号形式 -->
<Label Text="Label No. 4"
       FontSize="{x:Static Member=local:AppConstants.NormalFontSize}" />

<!-- 因为StaticExtension类具有ContentProperty属性，而Member为默认content属性，可以省去 -->
<Label Text="Label No. 5"
       FontSize="{x:Static local:AppConstants.NormalFontSize}" />
```

:orange:.NET System命名空间使用

`xmlns:sys="clr-namespace:System;assembly=netstandard"`

```xaml
<Label Text="&#x03C0; &#x00D7; E sized text"
       FontSize="{x:Static sys:Math.PI}"
       Scale="{x:Static sys:Math.E}"
       HorizontalOptions="Center" />
```

:orange: `Device.RuntimePlatform`使用

```xaml
<Label HorizontalTextAlignment="Center"
       FontSize="{x:Static local:AppConstants.NormalFontSize}">
    <Label.FormattedText>
        <FormattedString>
            <Span Text="Runtime Platform: " />
            <!-- 用于插入新行 -->
            <Span Text="{x:Static sys:Environment.NewLine}" />

            <!-- 显示运行时平台信息值 -->
            <Span Text="{x:Static Device.RuntimePlatform}" />
        </FormattedString>
    </Label.FormattedText>
</Label>
```

## x:Reference

由`ReferenceExtension`类支持，

* string类型`Name`属性
  * 用于设置为page中view，`x:Name`
  * 可省略写法因为Name为默认content property

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="MarkupExtensions.ReferenceDemoPage"
             x:Name="page"
             Title="x:Reference Demo">

    <StackLayout Margin="10, 0">

        <!-- 引用page作为Text的source，格式化 -->
        <Label Text="{Binding Source={x:Reference page},
                              StringFormat='The type of this page is {0}'}"
               FontSize="18"
               VerticalOptions="CenterAndExpand"
               HorizontalTextAlignment="Center" />

        <Slider x:Name="slider"
                Maximum="360"
                VerticalOptions="Center" />

        <!-- 引用slider作为BindingContext数据源 -->
        <Label BindingContext="{x:Reference slider}"
        <!-- 绑定slider的Value -->
               Text="{Binding Value, StringFormat='{0:F0}&#x00B0; rotation'}"
        <!-- 绑定slider的Value -->
               Rotation="{Binding Value}"
               FontSize="24"
               HorizontalOptions="Center"
               VerticalOptions="CenterAndExpand" />
    </StackLayout>
</ContentPage>
```

## x:Type

等效于C#的`typeof`关键字，由`TypeExtension`类支持

* string类型`TypeName`属性，用于设置成类名/结构名
  * 可省，默认content property
* 返回`System.Type`对象
* Xamarin中，由多个view支持 具有`Type`类型参数的属性
  * `Style`的`TargetType`属性
    * `<Style TargetType="{x:Type Border}">`
* <font color="red">`x:TypeArguments`用于指定泛型类中的参数</font>

:orange:使用情况

* `x:Array`标记扩展位置
* 用于创建menu时也适用
  * 每个menu item对应一种特定类型`Type`的对象
  * 当menu item选中时，初始化object

:candy:例子-menu

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             xmlns:local="clr-namespace:MarkupExtensions"
             x:Class="MarkupExtensions.MainPage"
             Title="Markup Extensions"
             Padding="10">
    <TableView Intent="Menu">
        <TableRoot>
            <TableSection>
                <TextCell Text="x:Static Demo"
                          Detail="Access constants or statics"
                          <!-- 绑定命令 -->
                          Command="{Binding NavigateCommand}"
                          <!-- 指定&引用参数类型 & 传入参数给command -->
                          CommandParameter="{x:Type local:StaticDemoPage}" />

                <TextCell Text="x:Reference Demo"
                          Detail="Reference named elements on the page"
                          Command="{Binding NavigateCommand}"
                          CommandParameter="{x:Type local:ReferenceDemoPage}" />

                <TextCell Text="x:Type Demo"
                          Detail="Associate a Button with a Type"
                          Command="{Binding NavigateCommand}"
                          CommandParameter="{x:Type local:TypeDemoPage}" />

                <TextCell Text="x:Array Demo"
                          Detail="Use an array to fill a ListView"
                          Command="{Binding NavigateCommand}"
                          CommandParameter="{x:Type local:ArrayDemoPage}" />
        </TableRoot>
    </TableView>
</ContentPage>
```

```c#
// code-behind
public partial class MainPage : ContentPage
{
    public MainPage()
    {
        InitializeComponent();

        // 初始化command, lambda重写execute方法
        //, xaml中CommandParameter指定&传入了特定类型的Page
        NavigateCommand = new Command<Type>(async (Type pageType) =>
        {
          // 根据xaml中传入的type，实例化Page
            Page page = (Page)Activator.CreateInstance(pageType);
            // 导航至新page
            await Navigation.PushAsync(page);
        });

        // 数据来源绑定为code-behind实例-MainPage
        BindingContext = this;
    }

// 定义ICommand命令字段
    public ICommand NavigateCommand { private set; get; }
}
```

:candy:例子-

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="MarkupExtensions.TypeDemoPage"
             Title="x:Type Demo">

    <StackLayout x:Name="stackLayout"
                 Padding="10, 0">
        <Button Text="Create a Slider"
                HorizontalOptions="Center"
                VerticalOptions="CenterAndExpand"
                Command="{Binding CreateCommand}"
                <!-- 绑定命令，传入slider参数 -->
                CommandParameter="{x:Type Slider}" />
        <Button Text="Create a Stepper"
                HorizontalOptions="Center"
                VerticalOptions="CenterAndExpand"
                Command="{Binding CreateCommand}"
                CommandParameter="{x:Type Stepper}" />
        <Button Text="Create a Switch"
                HorizontalOptions="Center"
                VerticalOptions="CenterAndExpand"
                Command="{Binding CreateCommand}"
                CommandParameter="{x:Type Switch}" />
    </StackLayout>
</ContentPage>
```

```c#
public partial class TypeDemoPage : ContentPage
{
    public TypeDemoPage()
    {
        InitializeComponent();

// 实现Command，execute，
// 参数传入为Type
        CreateCommand = new Command<Type>((Type viewType) =>
        {
          // 根据Type，实例化新view
            View view = (View)Activator.CreateInstance(viewType);
            // 设定垂直位置
            view.VerticalOptions = LayoutOptions.CenterAndExpand;
            // 新view添加至
            stackLayout.Children.Add(view);
        });

// 数据源绑定为code-behind
        BindingContext = this;
    }

// ICommand字段声明
    public ICommand CreateCommand { private set; get; }
}
```