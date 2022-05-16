package com.account.common.facade;

import com.account.common.dal.dao.RbacAccount;

public interface RbacAccountInternalFacade {

    public RbacAccount getAccount(String name);

    public void addAccount(RbacAccount rbacAccount);
}
