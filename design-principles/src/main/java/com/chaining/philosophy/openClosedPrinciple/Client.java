package com.chaining.philosophy.openClosedPrinciple;

/**
 * @ClassName Client
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/2/19 0019
 * @Version V1.0
 **/
public class Client {
    public static void main(String[] args) {
        IBook novel = new NovelBook("笑傲江湖",100,"金庸");
        System.out.println("书籍名字："+novel.getName()+"书籍作者："+novel.getAuthor()+"书籍价格："+novel.getPrice());
    }
}
