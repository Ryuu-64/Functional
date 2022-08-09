# Functional

[![](https://jitpack.io/v/Ryuu-64/Functional.svg)](https://jitpack.io/#Ryuu-64/Functional)

## 这是什么？

预定义常用的泛型函数式接口。

无参数到至多 8 个参数**无返回值** IAction ~ IAction8Arg， 无参数到至多 8 个参数**含返回值** IFunc ~ IFunc8Arg[^1]。

基于预定义函数式接口的多播 Action ~ Action8Arg， Func ~ Func8Arg。

多播函数式接口[^2]是对函数式接口集合的封装，调用多播函数接口的 invoke[^3] 将会依次调用该集合中的所有函数式接口的 invoke。

多播函数式接口支持添加及删除操作，支持 invoke 期间的添加及修改操作。

## 为何使用？

复用此库中预设的函数式接口能减少系统中的类数量，简化代码。

多播函数式接口方便开发者基于事件编程[^4]。

## 详细

自 java 8 函数式接口 (functional interface) 特性加入后，开发者们很容易在有需要用到函数式接口时就声明一个新的函数式接口。

声明的函数式接口越来越多，但他们大都相同，**除了接口名及接口中的方法名可能不同外，接口中方法的返回值及参数表中的参数都相同**。没有必要定义这么多实质相同函数式接口。

推荐复用此库中预设的函数式接口，而不是定义更多的函数式接口。减少系统中的类数量，简化代码。

## 关于

### 联系我

如果您发现任何错误或有任何建议，请联系我（ryuu）。

### 更新计划

暂无

## 脚注

[^1]: 一般地，由于 java 类型签名未包含泛型参数，因此泛型参数不同的接口名称不得相同，不能像 C# 一样定义多个名称相同但泛型参数不同的委托。<br>.NET 中类似 Java 函数式接口的后期绑定 ([late binding](https://en.wikipedia.org/wiki/Late_binding)) 实现被称为委托 ([delegate](https://docs.microsoft.com/zh-cn/dotnet/csharp/delegate-class))。<br>不同于 Java 函数式接口，.NET 中的委托默认为多播委托，并且 .NET 声明了从无参到 16 参，从无返回值到有返回值的各种泛型委托。<br> 因此，开发者可使用 .NET 已声明的委托，不必再自行声明委托。详情请见 [CSharp-委托](https://blog.ryuu64.top/CSharp-%E5%A7%94%E6%89%98/)。

[^2]: **java 不允许开发者自定义运算符重载**，因此多播函数式接口的操作没有如 C# 中委托的 +、+=、- 及 -= 等语法糖。

[^3]:执行函数式接口不是**调用 (call)** 目标方法，而是**援引 (invoke)** 目标方法。

[^4]:如观察者 (observer) 模式，又称发布订阅 (publish-subscribe) 模式。