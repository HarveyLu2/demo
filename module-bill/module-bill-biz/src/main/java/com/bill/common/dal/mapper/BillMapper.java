package com.bill.common.dal.mapper;


import com.bill.common.dal.dao.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface BillMapper {

    Bill selectBillById(Long id);
}
