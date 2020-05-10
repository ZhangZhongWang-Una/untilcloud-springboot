package com.una.uc.dao;


import com.una.uc.entity.SysParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Una
 * @date 2020/5/10 14:58
 */
public interface SysParamDAO extends JpaRepository<SysParam,Integer> {
    SysParam findById(int id);

    List<SysParam> findAllByNameLikeOrNameZhLikeOrValueLike(String keyword1, String keyword2, String keyword3);
}
