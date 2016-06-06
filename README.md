# android-mvvm
***A MVVM Framework for Android***
###Android上的高效的MVVM框架实现，已经在诸多商业项目中得到大量实践和检验，现已开源 欢迎使用。

###How To Use
工程基于gradle构建，自带一个sample，演示了多数场景下使用mvvm为项目开发带来的效率提升与高度解耦的结构化风格。
请记住，MVVM的核心一半是技术方案（围绕binder的实现），一半是开发规范（规划你的View，ViewModel，Model）。


###什么是MVVM
MVVM是微软设计WPF时提出的一种适用于前端的结构性设计模式，并配合其面向开发者、交互设计师推出的Expression Blend工具将其发挥到极致。
它是MVC的一种衍体，核心是binder，通过配置和约定来将视图与数据、逻辑之间的引用耦合降级为约定耦合，并规范出ViewModel来去除视图与原始数据之间的直接耦合。

###属性绑定中的几个概念
+ ViewObject：绑定关系中的view
+ ViewModel：绑定关系中面向视图的数据模型
+ ViewProperty(简称target)：要绑定的view目标属性
+ DataProperty(简称path)：驱动view属性的数据属性
+ ValueConverter：用来指定转换DataProperty方式

###绑定约定的配置方式
>api形式上提供标注和java代码两种主流方式，标注配置因为java语法限制仅支持单属性绑定

+ 通过注解
```java
@BindProperty(Target = "DataContext", Path = "Personal", ValueConverter = DefaultValueConverter.class)
private View personalInfoControl;
含义：将绑定数据项中的Personal属性通过DefaultValueConverter赋值给personalInfoControl的DataContext属性
```
+ 通过代码配置
```java
var layoutPropMap = OrderDetailLayoutPropertyMapTemplate.createDefault();
layoutPropMap.add(R.id.atom_flight_orderdetail_pricepanel, "PriceInfo');
layoutPropMap.add(R.id.atom_flight_orderdtail_noticebar, "NoticeObject', "DataContext");
layoutPropMap.remove(R.id.atom_flight_tvDebug, "DebugInfo');
VMBinder.getDefault().bindFlatView(orderDetailFragment, dataContext, layoutPropMap, cmdMap);
含义：将绑定数据项dataContext通过映射描述layoutPropMap，绑定到orderDetailFragment上，通过数据项驱动priceinfo、noticebar和debugtextview这三个视图
```
>为什么暂时没有提供xml配置
相比后来google推出的data-binding，我没有提供对等的xml＋表达式配置机制，原因是：
1.出于必要性考虑：xml配置目前相比上两种没有不可替代的好处，更多意义上是一种额外的开发习惯。
2.大量在xml中进行配置，并且还支持表达式的话，意味着会有大量的约定和维护内容（主要在于绑定path表达式的写法约定），在大型项目中、高频迭代项目中这种维护成本是非常高的，google需要像微软那样提供一个强大的Designer来管理和直观的操作这些绑定内容。

###属性处理器
对绑定配置BindDescription/BindProperty属性处理的处理有两种方式
+ 当DataProperty(path)和ViewProperty(target)都声明时，通过反射调用相应的get/set方法赋值
+ 当只声明DataProperty(path)时，称之为“简化属性绑定”，这种情况通过内置的默认属性处理职责链，尝试处理这个属性绑定配置。目前提供的简化属性绑定有：
    + 图片主属性绑定
    + 文字主属性绑定
    + 视图可见性属性绑定
    
    
###命令绑定
配置视图中的事件处理
目前支持OnClick，OnLongClick

###对常用场景提供一系列“脚手架”
+ MvvmArrayAdapter<T>：适用于数据源为数组的listview
+ MvvmCursorAdapter<T>：适用于数据源为游标的listview
+ ImmutableArraysItemViewBuilder：默认的view复用列表项生成器实现
+ IArraysItemViewBuilder：适用于需要更为灵活的列表项视图构建需求
+ BitmapAsyncBindToolkit：图片加载脚手架，提供异步加载和缓存管理特性


  丰富成熟高可用的移动端前后端技术架构积淀，为创业者提供一流的技术保障与效率。
