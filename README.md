[toc]
## 项目背景

大二Java期末搞成了个实训。一直有想自己模拟造一个ssm框架的想法

于是就动手造一个极简版，能实现简单ssm功能的框架当作业交了顺便练习

目前仅写完至

Spring的IOC,AOP及简单的事务功能

Mybatis(未加缓存)

(大佬勿喷....)
## 环境依赖
编译Jar版本:JDK8

```xml
  <properties>
    <logback.version>1.1.7</logback.version>
    <slf4j.version>1.7.21</slf4j.version>
  </properties>
  
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.tomcat.embed/tomcat-embed-core -->
    <dependency>
      <groupId>org.apache.tomcat.embed</groupId>
      <artifactId>tomcat-embed-core</artifactId>
      <version>9.0.30</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>${slf4j.version}</version>
    </dependency>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-core</artifactId>
      <version>${logback.version}</version>
    </dependency>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>${logback.version}</version>
    </dependency>
  </dependencies>
```

## 如何部署
将jar包下载到本地后上传至本地仓库即可通过maven导入

```xml
        <dependency>
            <groupId>edu.gdpu</groupId>
            <artifactId>spring</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

## Demo
### 启动类
模拟SpringBoot 需在入口方法调用Application.run()方法。需传入一个Class，以此Class的包名为基准进行包扫描

因此传入的类需是你所要扫描的包的上一级
```java
/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 00:31:18
 */
public class TestApplication {

    public static void main(String[] args) {
        Application.run(TestApplication.class);
        HelloController hellocontroller = (HelloController) ApplicationContext.getApplicationContext().getBeans().get("hellocontroller");
        hellocontroller.ha();
    }
}
```

正常启动则是

![image](http://www.ilanky.cn/upload/2020/06/image-dfc8678a2281452bac1896ed5f3f2ec3.png)


### Spring
支持注解:
 
@Aspect @UseAspect 

@Component @Controller @Service @Repository @AutoWired

@Configuration @Bean @Value

@Transaction

#### Ioc Demo 
正常像Spring注解版使用即可

目前仅支持 
```java
/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 00:31:55
 */

@Controller
public class HelloController {

    @AutoWired
    private HelloService helloService;

    public void ha(){
        helloService.test();
    }
}
```

#### aop Demo

首先你需要定义一个切面类,实现Advice接口并用@Aspect注解标注

value代表切面名，不传入则默认是类名

JoinPoint 切入点对象,可获取方法签名及代理对象和被代理对象

```java
/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 00:37:15
 */
@Aspect("hello")
public class HelloAspect implements Advice {
    @Override
    public void before(JoinPoint joinPoint) {
        System.out.println("before....");
    }

    @Override
    public void after(JoinPoint joinPoint) {
        System.out.println("after....");
    }

    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {

    }

    @Override
    public Object afterReturning(JoinPoint joinPoint, Object o) {
        System.out.println("return....");
        System.out.println(joinPoint);
        return null;
    }
}
```

定义好切面后在想要使用此切面的类标注上@UseAspect注解

```java
/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 00:33:51
 */
@UseAspect(value = "hello",exclude = "")
@Service
public class HelloServiceImpl implements HelloService{

    @Override
    public void test() {
            System.out.println("helloService test....");
    }
}
```

exclude传入排除增强的方法名

#### 事务Demo

在类上增加注解@Transaction

```java
/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月26日 12:25:32
 */
@Service
@Transaction(exclude = {"test1"})
public class AccountServiceImpl implements AccountService {

    @AutoWired
    private AccountDao accountDao;

    @Override
    public void test() {
        Account account = new Account();
        account.setMoney(1500);
        account.setName("张三");
        Account account1 = new Account();
        account1.setMoney(1500);
        account1.setName("李四");

        accountDao.update(account);
        int a = 10/0;
        accountDao.update(account1);

    }

    @Override
    public void test1() {
        Account account = new Account();
        account.setMoney(1000);
        account.setName("张三");
        Account account1 = new Account();
        account1.setMoney(2000);
        account1.setName("李四");

        accountDao.update(account);
        int a = 10/0;
        accountDao.update(account1);

    }
}
```


#### 如何自定义注解及其处理器

仅支持位于类上的注解

1. 自定义处理器 命名格式为 注解名+Handler并实现AnnotationHandler接口
2. 在resource目录下建立handlers.txt文件。添加处理器全类名即可
3. 传入的Object 强转为Class对象即可

#### 如何自定义监听器

1. 自定义监听器 实现ApplicationListener接口
2. 在resource目录下建立listener.txt。添加监听器全类名

### mybatis

#### config的Demo

```java
/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 15:52:52
 */
@Configuration
@Resource
public class DruidConfig {

    @Value("${mysql.url}")
    private String url;

    @Value("${mysql.user}")
    private String user;

    @Value("${mysql.password}")
    private String password;

    /**
     * 要扫描的包
     * @return
     */
    @Bean
    public MapperScanConfig mapperScanConfig(){
        MapperScanConfig mapperScanConfig = new MapperScanConfig();
        mapperScanConfig.setPackageName("edu.gdpu.myssm.mybatis.test");
        return mapperScanConfig;
    }

    /**
     * 必须注入
     * @return
     */
    @Bean
    public DaoConfiguration daoConfiguration(){
        DaoConfiguration daoConfiguration = new DaoConfiguration(new Environment(druidDataSource()));
        daoConfiguration.setMapperScanConfig(mapperScanConfig());
        return daoConfiguration;
    }

    public DataSource druidDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
```

#### 接口Demo

```java
/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 17:29:12
 */
@Repository
public interface AccountDao {

    @Select("select * from account")
    List<Account> findAll();

    @Update("update account set money = #{money} where name = #{name}")
    int update(Account account);

    @Insert("insert into account(name,money) values (#{name},#{money})")
    int insert(Account account);

    @Delete("delete from account where id = #{id}")
    int delete(int id);
}
```

#### 单元测试获取代理对象Demo

```java
/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 17:26:04
 */
public class MapperTest {

    @Test
    public void test(){
        Application.run(TestApplication.class);
        DaoConfiguration config = ApplicationContext.getApplicationContext().getBean(DaoConfiguration.class);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(config);
        SqlSession sqlSession = factory.openSqlSession();
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
//        Account account = new Account();
//        account.setMoney(2000);
//        account.setName("王五");
//        accountDao.delete(3);
        accountDao.delete(3);
        List<Account> all = accountDao.findAll();
        System.out.println(all);
    }
}
```



