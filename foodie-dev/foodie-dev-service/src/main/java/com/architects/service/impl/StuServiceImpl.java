package com.architects.service.impl;

import com.architects.mapper.StuMapper;
import com.architects.pojo.Stu;
import com.architects.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveStu() {
        Stu stu = new Stu();
        stu.setId(110);
        stu.setAge(18);
        stu.setName("jack");
        stuMapper.insert(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateStu(int id) {
        Stu stu = new Stu();
        stu.setId(id);
        stu.setName("chaining");
        stuMapper.updateByPrimaryKey(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteStu(int id) {
        stuMapper.deleteByPrimaryKey(id);
    }


    @Override
    public void saveParent(){
        Stu stu = new Stu();
        stu.setName("parent");
        stu.setAge(18);
        stuMapper.insert(stu);
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public void saveChildren(){
        saveChildren1();
        int a = 1/0;
        saveChildren2();
    }

    public void saveChildren1(){
        Stu stu = new Stu();
        stu.setName("child-1");
        stu.setAge(18);
        stuMapper.insert(stu);
    }

    public void saveChildren2(){
        Stu stu = new Stu();
        stu.setName("child-2");
        stu.setAge(18);
        stuMapper.insert(stu);
    }
}
