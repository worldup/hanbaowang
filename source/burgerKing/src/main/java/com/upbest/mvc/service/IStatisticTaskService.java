package com.upbest.mvc.service;

import java.util.List;

import com.upbest.mvc.entity.BWorkType;
import com.upbest.mvc.vo.BStsVO;
import com.upbest.mvc.vo.StsStoreVO;

public interface IStatisticTaskService {
    void autoSts();

    List<BStsVO> queryStsRs(Integer userId, String year, String type, String sord, String month, String quarter);

    List<StsStoreVO> queryStsStore(Integer userId, String year, String quarter, String month);

    List<Object[]> queryStsStoreObj(List<BWorkType> workTypeList, Integer userId, String year, String quarter, String month);

    List<StsStoreVO> doformatToStsStoreVO(List<Object[]> list, List<BWorkType> workTypeList, Integer userId, String year, String quarter, String month);

    List<StsStoreVO> getListWithAllStores(List<StsStoreVO> result, List<BWorkType> workTypeList, String month, Integer userId);

}
