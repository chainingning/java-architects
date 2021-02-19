package com.chaining.philosophy.openClosedPrinciple;

/**
 * @ClassName NovelBook
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/2/19 0019
 * @Version V1.0
 **/
public class NovelBook implements IBook {
    private String name;
    private int price;
    private String author;

    public NovelBook(String name, int price, String author) {
        this.name = name;
        this.price = price;
        this.author = author;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPrice() {
        return price;
    }


    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
