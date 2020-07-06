package edu.gdpu.myssm.mybatis;

import edu.gdpu.myssm.mybatis.config.DaoConfiguration;
import edu.gdpu.myssm.mybatis.config.Environment;
import edu.gdpu.myssm.mybatis.config.MapperScanConfig;
import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.ApplicationListener;
import edu.gdpu.myssm.spring.InterfaceObject;
import edu.gdpu.myssm.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月26日 00:37:43
 */
public class MyBatisListener implements ApplicationListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void begin() {

    }

    @Override
    public void completeScan() {
        DaoConfiguration config = null;
        try {
            config = ApplicationContext.getApplicationContext().getBean(DaoConfiguration.class);
        }catch (NullPointerException e){
            logger.warn("Can't get MapperScanConfig or DaoConfiguration from IOC");
            return;
        }
        MapperScanConfig bean = config.getMapperScanConfig();
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(config);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        BeanUtils.putBean("transactionBean",sqlSession);
        List<String> exclude = bean.getExclude();
        String packageName = bean.getPackageName();
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        for(String o: beans.keySet()){
            Object object = beans.get(o);
            if(!(object instanceof InterfaceObject)){
                continue;
            }
            InterfaceObject iObject = (InterfaceObject)object;
            String pName = iObject.getaClass().getPackage().getName();
            String name = iObject.getaClass().getSimpleName();
            if(pName.startsWith(packageName)&&!isExcluded(exclude,name)){
                BeanUtils.putBean(o,sqlSession.getMapper(iObject.getaClass()));
            }
        }

        logger.info("complete MapperProxy replace");
    }

    @Override
    public void prepared() {

    }

    @Override
    public void start() {

    }

    public boolean isExcluded(List<String> exclude,String name){
        if(exclude==null){
            return false;
        }
        for(String s:exclude){
            if(name.equals(s)){
                return true;
            }
        }
        return false;
    }
}
