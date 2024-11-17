##### <? extends T>
```java
private final List<? extends T> list;
```
- `?`表示类型通配符，即具体传什么参数类型，在List定义时不用考虑
- `<>`表示泛型，`T`表示泛型中装载的类型为T类型
- `<? extends T>` 类型上界，这里的?可以是T类型或者T的子类类型。只能返回T类型，但初始化的列表可以添加T类型或子类型
- `<? super T>` 类型下界，这里的?可以是T类型或者T的超类类型，但不代表我们可以往里面添加任意超类类型的元素。只能添加T类型或者子类型。
- `List<? extends T>`适用于读取数据，读取出来的数据全部用T类型接收。如果我们往此list中添加T类型不同的子类的话，各种子类无法相互转换，因此不能添加元素，但可接受初始赋值
- `List<? super T>`适用于添加元素，只能添加T类型或其子类类型。因为这些类型都能转换为T的任意超类类型（向上转型），因此我们可以对此list添加元素。只能用Object类型来接收获取到的元素，但是这些元素原本的类型会丢失

##### 静态泛型方法写法
```java
public static <T> PageRespDto<T> of(long pageNum, long pageSize, long total, List<T> list){}
```
- 静态方法代替构造器
- 这个是java声明泛型方法的固定格式 `public static <T> 返回值类型 方法名(参数)`
- <T>定义该方法所拥有的泛型标识符，个数可以是多个，例如：
```java
public static<T1,T2,T3> Response<T1> test(T2 t2,T3 t3){}
```
- 这样，在方法的返回值或者入参的地方，就可以使用“T”这个泛型

##### @EqualsAndHashCode
```java
// lombok注解
// 在生成equals和hashCode方法时，会包含父类的属性
@EqualsAndHashCode(callSuper = true)
public class A{}
```

##### @ControllerAdvice
https://www.cnblogs.com/yanggb/p/10859907.html
- Spring3.2中新增的注解
- 作用: 给Controller控制器添加统一的操作或处理
- 主要用法
  - 结合方法型注解@ExceptionHandler，用于捕获Controller中抛出的指定类型的异常，从而达到不同类型的异常区别处理的目的
  - 结合方法型注解@InitBinder，用于request中自定义参数解析方式进行注册，从而达到自定义指定格式参数的目的
  - 结合方法型注解@ModelAttribute，表示其注解的方法将会在目标Controller方法执行之前执行

##### @RestControllerAdvice
- 一个复合注解，结合了@ControllerAdvice和@ResponseBody的功能
- 专门用于构建RESTful控制器的全局异常处理器

##### ExcpetionHandler 捕获异常并统一处理
- @ExceptionHandler的作用主要在于声明一个或多个类型的异常，当符合条件的Controller抛出这些异常之后将会对这些异常进行捕获，然后按照其标注的方法的逻辑进行处理，从而改变返回的视图信息
- @ExceptionHandler的属性结构
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionHandler {
    // 指定需要捕获的异常的Class类型
    Class<? extends Throwable>[] value() default {};
}
```
- 使用@ExceptionHandler捕获RuntimeException异常的例子
```java
@ControllerAdvice
public class SpringControllerAdvice {
  @ExceptionHandler(RuntimeException.class)
  public ModelAndView runtimeExceptionHandler(RuntimeException e) {
      e.printStackTrace();
      return new ModelAndView("error");
  }
}
```
```java
@Controller
public class UserController {
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public void users() {
        throw new RuntimeException("没有任何用户。");
    }
}
```

##### 枚举
###### 自定义枚举类
```java
class Season {
    private String name;
    private String desc;

    // 定义了四个对象
    public static final Season SPRING = new Season("春天", "温暖");
    public static final Season SUMMER = new Season("夏天", "炎热");
    public static final Season FALL = new Season("秋天", "凉爽");
    public static final Season WINTER = new Season("冬天", "寒冷");

    // 1.构造器私有化：防止直接new
    // 2.去掉set相关方法：防止属性被修改
    // 3.在Season内部，直接创建固定的对象
    // 4.优化，加入final修饰符
    private Season(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
```
###### enum实现枚举类
```java
// 使用： Season2.SPRING
enum Season2 {
    // 1.使用enum替代class
    // 2.SPRING("春天", "温暖")  常量名(实参列表) 
    // 相当于public static final Season SPRING = new Season("春天", "温暖")的简化
    // 3.如果有多个常量(对象)，使用 , 间隔
    // 4.使用enum实现枚举，要求将定义常量对象写在前面
    SPRING("春天", "温暖"),
    SUMMER("夏天", "炎热"),
    FALL("秋天", "凉爽"),
    WINTER("冬天", "寒冷");
    
    private String name;
    private String desc;

    private Season2(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    // ... get方法等
}
```

##### SpringBoot实现跨域
https://blog.csdn.net/shaoming314/article/details/113937467
###### 什么是跨域？
- 当一个请求url的协议、域名、端口三者之间任意一个与当前页面url不同即为跨域
- 跨域问题来源于浏览器的同源策略。同源策略会阻止一个域的javascript脚本和另外一个域的内容进行交互。所谓同源（即指在同一个域）就是两个页面具有相同的协议（protocol），主机（host）和端口号（port）
###### 非同源限制
- 无法读取非同源网页的 Cookie、LocalStorage 和 IndexedDB
- 无法接触非同源网页的 DOM
- 无法向非同源地址发送 AJAX 请求
###### 后端 实现 CORS 跨域请求的方式
本质：修改响应头，向响应头中添加浏览器所要求的数据，进而实现跨域 \
全局跨域：(1-2) 局部跨域：(3-5)
1. 返回新的CorsFilter[本项目使用]
2. 重写 WebMvcConfigurer 
3. 使用注解 @CrossOrigin
4. 手动设置响应头 (HttpServletResponse)
5. 自定web filter 实现跨域 


##### mybatis-plus-generator
https://baomidou.com/guides/new-code-generator/

##### @Primary
- 用来发生依赖注入的歧义时决定要注入哪个 bean。
- 当存在多个相同类型的 bean 时，此注解定义了首选项
```java
    @Bean
    public Employee tomEmployee() {
        return new Employee("Tom");
    }
 
    @Bean
    @Primary
    public Employee johnEmployee() {
        return new Employee("john");
    }
 
 
    @Component
    public class Job1 {
        // 注入的是john Employee，因为它有@Primary注解
        @Autowired
        private Employee employee;
 
        // TODO
    }
```

##### Spring 对 cache的支持
https://blog.csdn.net/zl1zl2zl3/article/details/110987968

##### Mybatis-Plus IdWorker
- IDWorker通过Mybatis Plus的插件机制集成到项目中，利用雪花算法为每个数据库实体生成唯一的ID
- 