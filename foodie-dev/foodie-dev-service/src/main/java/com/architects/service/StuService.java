package com.architects.service;

import com.architects.pojo.Stu;

public interface StuService {

    Stu getStuInfo(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);

    public void saveParent();
    public void saveChildren();
}
