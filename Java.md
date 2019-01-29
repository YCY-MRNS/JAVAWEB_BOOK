

# JSP

## JAVA_WEB 请求的转发和重定向 ##

**请求的转发:**

```java
`request.getRequestDispatcher("/c.jsp").forward(request, response);`    
```

**请求的重定向:**

```java
 `response.sendRedirect("c.jsp");`
```

**本质区别：**

- 区别1

	 请求的转发只发出一次请求（地址栏***最初的请求地址***）
	
	 重定向是发出两次（地址栏为***最后响应的地址***）  

- 区别2

	  请求的转发的对象在最终的servlet中为同一个对象
	
	  请求的重定向的对象不同

- 区别3
  
	  转发为当前的***web应用***的资源
	
	  重定向为***任何资源*** 如：[https://www.baidu.com](http://baidu.com)


- 区别4

	 转发的/代表WEB***目录***的根目录
	
	 重定向的/代表WEB***站点***的根目录 

## 和属性相关的方法 ##

- **方法**

	`void setAttribute(String name, Object o):` 设置属性
  
	`Object getAttribute(String name):` 获取指定的属性

	`Enumeration getAttributeNames():` 获取所有的属性的名字组成的Enumeration对象
	
	`removeAttribute(String name):` 移除指定的属性 


- **域对象**

  **pageContext**, **request**, **session**, **application**  

  **pageContext**: 属性的作用范围仅限于当前 JSP 页面

  **request**:  属性的作用范围仅限于同一个请求. 

  **session**: 属性的作用范围限于一次会话: 浏览器打开直到关闭称之为一次会话(在此期间会话不失效)

  **application**: 属性的作用范围限于当前 WEB 应用. 是范围最大的属性作用范围, 只要在一处设置属性, 在其他各处的 JSP 或 Servlet 中都可以获取到. 

   
# JSP指令

## JSP指令-PAGE ##


1. **import 属性:** 指定当前 JSP 页面对应的 Servlet 需要导入的类.

2. **session 属性:** 取值为 true 或 false, 指定当前页面的 session 隐藏变量是否可用, 也可以说访问当前页面时是否一定要生成 HttpSession

3. **errorPage** 和 **isErrorPage:** 


  -  errorPage 指定若当前页面出现错误的实际响应页面时什么. 其中 / 表示的是当前 WEB 应用的根目录. 
  
  - 在响应 error.jsp 时, JSP 引擎使用的请求转发的方式. 
  
  - isErrorPage 指定当前页面是否为错误处理页面, 可以说明当前页面是否可以使用 exception 隐藏变量. 需要注意的是: 若指定isErrorPage="true", 并使用 exception 的方法了, 一般不建议能够直接访问该页面. 

  - 如何使客户不能直接访问某一个页面呢 ? 对于 Tomcat 服务器而言, WEB-INF 下的文件是不能通过在浏览器中直接输入地址的方式来访问的. 但通过请求的转发是可以的!还可以在 web.xml 文件中配置错误页面: 

   

  ```java
  <error-page>
  <!-- 指定出错的代码: 404 没有指定的资源, 500 内部错误. -->
   	  	<error-code>404</error-code>
   	  	<!-- 指定响应页面的位置 -->
   	  	<location>/WEB-INF/error.jsp</location>
   	</error-page>
  ```
  ```java
   <error-page>
     	<!-- 指定异常的类型 -->
   	  	<exception-type>java.lang.ArithmeticException</exception-type>
   	  	<location>/WEB-INF/error.jsp</location>
   	</error-page>
  ```


4. **contentType**

 	指定当前 JSP 页面的响应类型. 实际调用的是 response.setContentType("text/html; charset=UTF-8");通常情况下, 对于 JSP 页面而言其取值均为 text/html; charset=UTF-8. charset 指定返回的页面的字符编码是什么. 通常取值为 UTF-8

5. **pageEncoding**
  
	指定当前 JSP 页面的字符编码. 通常情况下该值和 contentType 中的 charset 一致. 

6. **isELIgnored** 

  	指定当前 JSP 页面是否可以使用 EL 表达式. 通常取值为 false.

 


## JSP指令-include ##

**include:**

`<%@ include file="b.jsp" %>`

静态引入：include 指令用于通知 JSP 引擎在翻译当前 JSP 页面时将其他文件中的内容合并进当前 JSP 页面转换成的 Servlet 源文件中,这种在源文件级别进行引入的方式称之为静态引入, 当前JSP页面与静态引入的页面紧密结合为一个Servlet

**jsp:incluced:**

动态引入: 并不是像 include 指令生成一个 Servlet 源文件, 而是生成两个 Servlet 源文件, 然后通过一个方法的方式把目标页面包含
进来. 

```java
<jsp:include page="b.jsp"></jsp:include>
```

**jsp:forward:**

```java
<jsp:forward page="/include/b.jsp"></jsp:forward>

相当于：

request.getRequestDispatcher("/include/b.jsp").forward(request, response);
```



# MVC

## MVC案例 ##

- 使用到的技术
  
 1. MVC设计模式：JSP ， Servlet ， POJO
 2. 数据库连接MySql
 3. 数据库使用到C3P0数据库连接池
 4. JDBC工具采用DBUtils
 5. 页面jQuery


- 技术难点

 1. 多个请求如何使用一个Servlet

 2. 如何模糊查询


- 架构图

  ![](https://i.imgur.com/UDHAM17.png)


- 现阶段难点解决
  
 1. Servlet映射*.do

			 //获得servlet的path  并去掉 / .do
	        String servletPath = request.getServletPath().substring(1);
	        String methodName = servletPath.substring(0, servletPath.length() - 3);
            		  //利用反射去调用对应的方法
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);

 2. sevlet 加后缀 有 getParameter(后缀属性)


 3. 模糊查询使用数据库的sql语句

 			如：select * from user where name like "%a%"




## 面向接口编程 ##

在类中调用接口定义的方法，不用实现具体的方法内的逻辑，有利代码的解耦，让程序有更好地重用性和可拓展性。

```java
	public interface UserDAO {
	    public List<User> getAll();
	    public void save(User user);
	    public User get(Integer id);
	    public void delete(Integer id);
	    public long getCountWithName(String name);
	    public List<User> getForListWithCondition(ConditionUser conditionUser);
	    public  void update(User user);
	}
```

## 利用配置文件实现切换底层存储源（面向接口编程）

**切换底层的存储源**

- ## 创建DAOFactory类，设置单例，

- 在Servlet的init（）中读取配置文件，赋值给DAOFactory类的type值

- DAOFactory类根据type的值返回对应的需要的对象给Servlet


   ```java
   
   public class UserDAOFactory {
   private Map<String, UserDAO> dao = new HashMap<>();
   
       private UserDAOFactory() {
           dao.put("jdbc", new UserDaoJdbcImpl());
           dao.put("xml", new UserDaoXMLImpl());
       }
   
       private static UserDAOFactory instance = new UserDAOFactory();
   
       public static UserDAOFactory getInstance() {
           return instance;
       }
   
       private String type = null;
   
       public void setType(String type) {
           this.type = type;
       }
   
       public UserDAO getUserDAOImpl() {
           return dao.get(type);
       }
   }
   
   @WebServlet(name = "InitServlet", urlPatterns = {"/initServlet"}, loadOnStartup = 1)
   	public class InitServlet extends HttpServlet {
   	    @Override
   	    public void init() throws ServletException {
   	        UserDAOFactory.getInstance().setType("jdbc");
   	        InputStream in = getServletContext().getResourceAsStream("/WEB-INF/classes/Users.properties");
   	        Properties properties = new Properties();
   	        try {
   	            properties.load(in);
   	            String type = properties.getProperty("type");
   	            UserDAOFactory.getInstance().setType(type);
   	        } catch (IOException e) {
   	            e.printStackTrace();
   	        }
           }
   ```


   ​	



# Cookie

## Cookie是什么？

- **Cookie**是浏览器访问服务器的某一个资源的时候，有服务器在HTTP**响应消息头中附带**传送个浏览器的一个**小文本**
- 完成会话追踪的一种**机制**：采用的是在**客户端保持和HTTP状态**的一种方案
- 浏览器保存Cookie后，在之后每次访问服务器的时候，都会在HTTP请求头文件中将Cookie回传给服务器

## 会话Cookie和持久化Cookie

- 会话Cookie：不设置Cookie的时间，这个Cookie在浏览器关闭的时候就会消失，Cookie在浏览器的内存中
- 持久化Cookie:为Cookie设置上相应的时间，让其保持，Cookie会保存在磁盘中

## Cookie-实现自动登录

**主要的cookie核心类**

- **Cookie cookie = new Cookie(name,value)**   创建一个cookie 

- **cookie.setMaxAge() ;**   设置负数 不会启动存储  设置0为马上删除Cookies  设置正数为保持时间 单位（秒）

- **response.addCookie(cookie);**  添加一个Cookie

- **Cookie[] cookies = request.getCookies()**  获得全部的cookie 返回的是一个数组

- **cookie.getName() 以及 cookie.getValue ()**   j获得Cookie的名字和值

  ​    

    <!--简单实例-->

    ```java
    String name = request.getParameter("name");
    
    if (name != null && !name.trim().equals("")) {
        Cookie cookie = new Cookie("name", name);
        cookie.setMaxAge(10);
        response.addCookie(cookie);
    } else {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("name".equals(cookie.getName())) {
                    name = cookie.getValue();
                }
            }
        }
    }
    if (name != null && !name.trim().equals("")) {
        out.print("hello:" + name);
    } else {
        response.sendRedirect("login.jsp");
    }
    ```



## Cookie-显示最近的浏览记录

<!--简单实例-见源码-->

## Cookie-Path

**Cookie 的作用范围**：可以作用当前的目录和当前目录的子目录，但是不能作用于当前目录的上一级目录

**设置Cookie的作用范围：**    `cookie.setPath(request.getContextPath() + "/");`

# HttpSession

## HttpSession 的生命周期

- 什么时候创建？

  1.  对于 JSP: 是否浏览器访问服务端的任何一个 JSP, 服务器都会立即创建一个 HttpSession 对象呢？
     不一定。

  

  ​    若当前的 JSP 是客户端访问的当前 WEB 应用的第一个资源，且 JSP 的 page 指定的 session 属性值为 false, 则服务器就不会为 JSP 创建一个 HttpSession 对象;

  

  ​    若当前 JSP 不是客户端访问的当前 WEB 应用的第一个资源，且其他页面已经创建一个 HttpSession 对象，
  则服务器也不会为当前 JSP 页面创建一个 HttpSession 对象，而会把和当前会话关联的那个 HttpSession 对象返回给当前的 JSP 页面.

  

  2. 对于 Serlvet: 若 Serlvet 是客户端访问的第一个 WEB 应用的资源,则只有调用了 request.getSession() 或 request.getSession(true) 才会创建 HttpSession 对象

  ​      

- 什么时候销毁？

   1. 直接调用 HttpSession 的 invalidate() 方法: 该方法使 HttpSession 失效        

   2. 服务器卸载了当前 WEB 应用. 

   3. 超出 HttpSession 的过期时间.

      ```java
      > 设置 HttpSession 的过期时间: session.setMaxInactiveInterval(5); 单位为秒
      > 在 web.xml 文件中设置 HttpSession 的过期时间: 单位为 分钟. 
      <session-config>
              <session-timeout>30</session-timeout>
      </session-config>
      ```

   4. 并不是关闭了浏览器就销毁了 HttpSession. 



## 利用URL重写实现session跟踪

> Servlet规范中引入一种补充的会话管理机制	，允许不支持Cookie的浏览器也可以和服务器保持连续的会话
>
> 将会话标识号以参数的形式附加在URL地址后面的技术称为URL重写

```java
<%=response.encodeRedirectURL("hello.jsp")%>
```



## 关于路径

> **使用绝对路径**：使用相对路径可能会有问题, 但使用绝对路径肯定没有问题. 

>**若 / 需要服务器进行内部解析**, 则代表的就是 WEB 应用的根目录. 若是交给浏览器了, 则 / 代表的就是站点的根目录
>**若 / 代表的是 WEB 应用的根目录**, 就不需要加上 contextPath 了. 

## 表单的重复提交

**重复提交的情况:**

- 在表单提交到一个 Servlet, 而 Servlet 又通过请求转发的方式响应一个 JSP(HTML) 页面, 
  此时地址栏还保留着 Serlvet 的那个路径, 在响应页面点击 "刷新" 

![](C:\Users\Administrator\Desktop\NOTE\表单的重复提交.png)

- 在响应页面没有到达时重复点击 "提交按钮". 
- 点击 "返回", 再点击 "提交"

**不是重复提交的情况:**

- 点击 "返回", "刷新" 原表单页面, 再 "提交"。

**如何避免重复提交**

1. 在原表单页面, 生成一个随机值 Flag
2. 在原表单页面, 把 Flag 值放入 session 属性中
3. 在原表单页面, 把 Flag 值放入到 隐藏域 中.
4. 在目标的 Servlet 中: 获取 session 和 隐藏域 中的 Flag 值
5. 比较两个值是否一致: 若一致, 受理请求, 且把 session 域中的 Flag 属性清除
6. 若不一致, 则直接响应提示页面: "重复提交"j

## 验证码

> 同表单的重复提交



# 简单标签



## 创建使用

1. 创建一个简单标签：实现SimpleTag的接口
2. WEB-INF下面创建一个.tld（标签库描述文件），创建自定义的Tag

```java
<?xml version="1.0" encoding="UTF-8" ?>
<taglib xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd"
        version="2.0">
	
    <description>MyTag 1.1 core library</description>
    <display-name>MyTag core</display-name>
    <tlib-version>1.0</tlib-version>
    <short-name>my</short-name>
    <uri>http://ycy.pers/mytag/core</uri>

    <tag>	  
        <name>hello</name>
        <tag-class>com.ycy.demo.tag.HelloTag</tag-class>
        <body-content>empty</body-content>
    </tag>
</taglib>
```

3. 在Jsp中使用自定义标签   
   - 使用JSP指令的taglib导入标签库描述文件
   - 使用自定义的标签

## setJspContext:

 一定会被 JSP 引擎所调用, 先于 doTag, 把代表 JSP 引擎的 pageContext 传给标签处理器类. 

```java
private PageContext pageContext;
@Override
public void setJspContext(JspContext jspContext) {
    this.pageContext = (PageContext) jspContext;
    System.out.println("setJspContext");
}
```

## 带属性的自定义标签:

1. 先在标签处理器类中定义 setter 方法. 建议把所有的属性类型都设置为 String 类型. 

```java
private String value;
private int count;

public void setValue(String value) {
    this.value = value;
}

public void setCount(int count) {
    this.count = count;
}
```

2. 在 tld 描述文件中来描述属性:

```java
<!-- 描述当前标签的属性 -->
<attribute>
    <!-- 属性名, 需和标签处理器类的 setter 方法定义的属性相同 -->
    <name>value</name>
    <!-- 该属性是否被必须 -->
    <required>true</required>
    <!-- rtexprvalue: runtime expression value 
        当前属性是否可以接受运行时表达式的动态值 -->
    <rtexprvalue>true</rtexprvalue>
</attribute>
```

3. 在页面中使用属性, 属性名同 tld 文件中定义的名字. 

```java
<mytag:hello count="10" value="${param.name}"/>
```

4. 通常情况下开发简单标签直接继承 SimpleTagSupport

## 带标签体的自定义标签

1. 创建**带有标签体**的标签（在 tld 文件中, 使用 body-content 节点来描述标签体的类型:）

   ```java
   <body-content>scriptless</body-content>
   ```

   <body-content>: 指定标签体的类型, 大部分情况下, 取值为 **scriptless**。可能取值有 3 种：
       **empty**: 没有标签体	
       **scriptless**: 标签体可以包含 el 表达式和 JSP 动作元素，但不能包含 JSP 的脚本元素
       **tagdependent**: 表示标签体交由标签本身去解析处理。若指定 tagdependent，在标签体中的所有代码都会原封不动的交给标签处理器，而不是将执行结果传递给标签处理器

2. 若配置了标签含有标签体, 则 JSP 引擎会调用 setJspBody() 方法把 JspFragment 传递给标签处理器类
   在 SimpleTagSupport 中还定义了一个 getJspBody() 方法, 用于返回 JspFragment 对象. 
3. JspFragment 的 **invoke(Writer)** 方法: 把标签体内容从 Writer 中输出, 若为 null, 则等同于 invoke(getJspContext().getOut()), 即直接把标签体内容输出到页面上有时, 可以 借助于 StringWriter, 可以在标签处理器类中先得到标签体的内容: 

### 简单实例_1

> 将标签体中字母转换为大写显示在页面上

```java
@Override
public void doTag() throws JspException, IOException {
    JspFragment jspBody = getJspBody();
    // 利用 StringWriter 得到标签体的内容.
    StringWriter sw = new StringWriter();
    jspBody.invoke(sw);
    //将标签体中的内容转为大写
    String string = sw.toString().toUpperCase();
    getJspContext().getOut().print(string);
}
```

### 简单实例_2

> 实现c：forEach  利用自定义简单标签

- 两个属性: items(集合类型, Collection), var(String 类型)

- doTag:

       	1. 遍历 items 对应的集合
          	2. 把正在遍历的对象放入到 pageContext 中, 键: var, 值: 正在遍历的对象. 
             	3. 把标签体的内容直接输出到页面上.

   <!--自定义配置Tag参数-->

  ```java
  <tag>
      <name>TagForEach</name>
      <tag-class>com.ycy.demo2.TagForEach</tag-class>
      <body-content>scriptless</body-content>
  
      <attribute>
          <name>items</name>
          <required>true</required>
          <rtexprvalue>true</rtexprvalue>
      </attribute>
      <attribute>
          <name>var</name>
          <required>true</required>
          <rtexprvalue>true</rtexprvalue>
      </attribute>
  </tag>
  ```

​     <!-- doTag -->

```java
public class TagForEach extends SimpleTagSupport {
    private Collection<?> items;
    private String var;

    public void setItems(Collection<?> items) {
        this.items = items;
    }
    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public void doTag() throws JspException, IOException {

        if (items != null) {
            for (Object o : items) {
                getJspContext().setAttribute(var, o);
                getJspBody().invoke(null);
            }
        }
    }
}
```

<!-- jsp调用 -->

```java
<%@ page import="java.util.List" %>
<%@ page import="com.ycy.demo2.Info" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://mycompany.com" prefix="mytag" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<%
    request.setAttribute("show", "hello alex!");
    List<Info> infos = new ArrayList<Info>();
    infos.add(new Info("marry", "hello!"));
    infos.add(new Info("alex", "bye!"));
    infos.add(new Info("mark", "see you again!"));
    request.setAttribute("infos", infos);

%>


<mytag:TagForEach items="${requestScope.infos}" var="i">
    ${i.name}---${i.message}<br>
</mytag:TagForEach>

</body>
</html>
```



## 带父标签的自定义标签

1.   父标签**无法获取**子标签的引用, 父标签仅把子标签作为标签体来使用. 
2. 子标签可以通过 **getParent()** 方法来获取父标签的引用 

```java
public class SonTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        ParentTag parent = (ParentTag) getParent();
        String name = parent.getName();
        getJspContext().getOut().print("Son Tag :" + name);
        getJspContext().getOut().print("<br>");
    }
}
```

3. **注意**: 父标签的类型是 JspTag 类型. 该接口是一个空接口, 但是来统一 SimpleTag 和 Tag 的. 实际使用需要进行类型的**强制转换**.

# JSTL

1. *. c:out 主要用于对特殊字符进行转换. 真正进行输出时, 建议使用 c:out, 而不是使用 EL
2. *. c:set: 可以为域赋属性值。 而对域对象中的 JavaBean 的属性赋值用的并不多. 
3. c:remove: 移除指定域对象的指定属性值(较少使用, 即便移除也是在 Servlet 中完成)
4. c:if: 在页面上对现实的内容进行过滤, 把结果存储到域对象的属性中. 但不灵活, 会被其他的自定义标签所取代. 
5. c:choose, c:when, c:otherwise: 作用同上, 但麻烦, 不灵活.
6. *c:forEach: 对集合进行遍历的. 常用!
7. c:forTokens: 处理字符串, 类似于 String 累的 split() 方法(知道即可)
8. c:import: 导入页面到当前页面的. (了解)
9. c:redirect: 当前页面进行重定向的. (使用较少)
10. *c:url: 产生一个 URL 的, 可以进行 URL 重写, 变量值编码, 较为常用. 



# Filter

> Filter称之为**过滤器**，是用来做一些拦截的任务， 在Servlet接受请求之前，做一些事情，如果不满足限定，可以拒绝进入Servle

## 注册

1. web.xml注册

```java
<filter>
    <filter-name>SecondFilter</filter-name>
    <filter-class>com.ycy.Filter.SecondFilter</filter-class>
</filter>

<filter-mapping>
    <filter-name>SecondFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

1. 代码注解

   ```java
   @WebFilter(filterName = "com.ycy.Filter.SecondFilter", urlPatterns = {"/*"}, initParams = {
           @WebInitParam(name = "name", value = "root")
   })
   public class SecondFilter implements Filter {
       public void destroy() {
           System.out.println("destroy second filter");
       }
   
       public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
           chain.doFilter(req, resp);
           System.out.println("do filter");
       }
   
       public void init(FilterConfig config) throws ServletException {
           System.out.println("init second Filter");
       }
   
   }
   ```



 































