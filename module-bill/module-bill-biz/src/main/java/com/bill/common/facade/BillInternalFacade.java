package com.bill.common.facade;

import com.stori.sofa.model.Result;

/**
 * Internal Facade
 *
 * @author king
 * @date 2022/05/07 13:51
 **/
public interface BillInternalFacade {

    /**
     * getBill
     * 
     * @date 2022/05/07 14:01
     * @return com.stori.sofa.model.Result<java.lang.String>
     */
    public Result<String> getBill();
}
