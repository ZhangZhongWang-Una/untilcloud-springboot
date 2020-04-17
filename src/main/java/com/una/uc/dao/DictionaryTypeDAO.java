package com.una.uc.dao;

import com.una.uc.entity.DictionaryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/19 18:10
 */
public interface DictionaryTypeDAO  extends JpaRepository<DictionaryType,Integer>  {
    DictionaryType findById(int id);

    DictionaryType findByCode(int code);
}
