package com.bill.common.facade;

import com.stori.sofa.model.Result;

/**
 * External Facade
 *
 * @author king
 * @date 2022/05/07 13:51
 **/
public interface BillExternalFacade {

    /**
     * getBill
     *
     * @date 2022/05/07 14:01
     * @return com.stori.sofa.model.Result<java.lang.String>
     */
    public Result<String> getBill();
}
