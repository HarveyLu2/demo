package com.bill.common.dal.mapper;


import com.bill.common.dal.dao.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface StudentMapper {

    Student selectStudentById(int id);
}
