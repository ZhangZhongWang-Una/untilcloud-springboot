package com.una.uc.dao;

import com.una.uc.entity.DictionaryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author Una
 * @date 2020/3/19 18:11
 */
public interface DictionaryInfoDAO extends JpaRepository<DictionaryInfo,Integer> {
    DictionaryInfo findById(int id);

    @Query(nativeQuery = true, value = "select * from dictionary_info where type_id = ?1 ")
    List<DictionaryInfo> findAllByDictionaryTypeOrderBySort(int dicTypeId);

    @Query(nativeQuery = true, value = "select di.name,di.value from dictionary_info di " +
            "left join dictionary_type dt on dt.id = di.type_id " +
            "where dt.code = ?1 and dt.`status` = '1' and di.`status` = '1' order by di.`sort`")
    List<Map<String,String>> findAllByCode(int code);
}
