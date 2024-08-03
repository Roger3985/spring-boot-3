package com.atguigu.boot.bean;

/**
 * @author Roger
 * @Description
 * @create 2024-07-25
 */
public class Cat {
    private Long id;
    private String name;

    public Cat() {
    }

    public Cat(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
