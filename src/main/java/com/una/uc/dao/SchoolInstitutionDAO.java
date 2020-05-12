package com.una.uc.dao;

import com.una.uc.entity.SchoolInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Una
 * @date 2020/5/11 19:33
 */
public interface SchoolInstitutionDAO extends JpaRepository<SchoolInstitution,Integer> {
    SchoolInstitution findById(int id);

    @Query(nativeQuery = true, value = "select level from school_institution where id = ?1 ")
    String findLevelByParentId(int pid);

    List<SchoolInstitution> findAllByNameLike(String keywords);

    List<SchoolInstitution> findAllByParentId(int pid);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from school_institution where level like ?1 ")
    int deleteAllByLevelLike(String level);

    List<SchoolInstitution> findAllByLevelLike(String keywords);
}
