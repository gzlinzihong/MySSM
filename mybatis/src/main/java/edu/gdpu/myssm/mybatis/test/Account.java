package edu.gdpu.myssm.mybatis.test;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 17:29:04
 */
public class Account {

    private Integer id;
    private String name;
    private Integer money;

    public Account(Integer id, String name, Integer money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
