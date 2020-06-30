import com.alibaba.druid.pool.DruidDataSource;
import edu.gdpu.TestApplication;
import edu.gdpu.myssm.mybatis.*;
import edu.gdpu.myssm.mybatis.config.DaoConfiguration;
import edu.gdpu.myssm.mybatis.config.Environment;
import edu.gdpu.myssm.mybatis.config.MapperScanConfig;
import edu.gdpu.myssm.mybatis.test.Account;
import edu.gdpu.myssm.mybatis.test.AccountDao;
import edu.gdpu.myssm.spring.Application;
import edu.gdpu.myssm.spring.ApplicationContext;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

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
