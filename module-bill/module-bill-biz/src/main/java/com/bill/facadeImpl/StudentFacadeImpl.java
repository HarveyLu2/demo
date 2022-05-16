package com.bill.facadeImpl;

import com.bill.common.dal.dao.Student;
import com.bill.common.dal.mapper.StudentMapper;
import com.bill.common.facade.StudentInternalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentFacadeImpl")
public class StudentFacadeImpl implements StudentInternalFacade {

    @Autowired
    StudentMapper studentMapper;


    @Override
    public Student getStudent() {

        Student student = studentMapper.selectStudentById(1);
        return student;
    }
}
