package com.demo.manage.domain.repository;

import com.demo.manage.domain.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author yangyueming
 */
@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long>, JpaSpecificationExecutor<Operator> {
    @Query("select t from Operator t where t.name =?1 and t.email =?2")
    Operator findByNameAndEmail(String name, String email);

    @Query("select distinct u from Operator u where u.name= :name")
    Operator findByName(@Param("name") String name);

    @Query("select distinct u from Operator u where u.id= :id")
//    Operator findById(@Param("id") Long id);
    Optional<Operator> findById(@Param("id") Long id);

    @Query("select o from Operator o " +
            "left join o.parts p " +
            "where p.id= :id")
    List<Operator> findByPartId(@Param("id") Long id);
}
