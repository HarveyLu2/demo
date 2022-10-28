package com.stori.sofa.mapper;

import com.bill.common.dal.dao.Bill;
import com.bill.common.dal.mapper.BillMapper;
import com.stori.sofa.base.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @param
 * @author Daniel Yang(sw)
 * @version 20220525
 * @description Test BillMapper
 * @date 2022/5/7 16:00
 * @return
 */
public class BillMapperTest extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillMapperTest.class);

    @Resource
    BillMapper billMapper;

    @Before
    public void setUp() {
        init();
    }

    @Test
    public void testSelectBillById() {
        Bill bill = billMapper.selectBillById(1L);
        Assert.assertNotNull(bill);
        LOGGER.info("BillMapper.selectBillById() passed.");
    }
}