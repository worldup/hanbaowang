package com.upbest.mvc.service;

import org.springframework.data.domain.Pageable;

import com.upbest.mvc.vo.UserStatisticVO;
import com.upbest.utils.PageModel;

public interface IStatisticService {
    public PageModel<UserStatisticVO> queryStatisticResult(Pageable pageable);
}
