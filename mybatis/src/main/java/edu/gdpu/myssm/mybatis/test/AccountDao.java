package edu.gdpu.myssm.mybatis.test;

import edu.gdpu.myssm.mybatis.annotation.Delete;
import edu.gdpu.myssm.mybatis.annotation.Insert;
import edu.gdpu.myssm.mybatis.annotation.Select;
import edu.gdpu.myssm.mybatis.annotation.Update;
import edu.gdpu.myssm.spring.annotation.Repository;

import java.util.List;

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
