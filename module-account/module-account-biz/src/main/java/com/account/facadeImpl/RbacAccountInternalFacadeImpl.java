package com.account.facadeImpl;

import com.account.common.dal.dao.RbacAccount;
import com.account.common.dal.mapper.AccountMapper;
import com.account.common.dal.mapper.RbacAccountMapper;
import com.account.common.facade.RbacAccountInternalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("rbacAccountFacade")
public class RbacAccountInternalFacadeImpl implements RbacAccountInternalFacade {

    @Autowired
    private RbacAccountMapper rbacAccountMapper;

    @Override
    public RbacAccount getAccount(String name) {

        RbacAccount rbacAccount = rbacAccountMapper.selectByName(name);

        return rbacAccount;
    }

    @Override
    public void addAccount(RbacAccount rbacAccount){

        rbacAccountMapper.insertAccount(rbacAccount);
        rbacAccountMapper.insertRole(rbacAccount);

    }
}
