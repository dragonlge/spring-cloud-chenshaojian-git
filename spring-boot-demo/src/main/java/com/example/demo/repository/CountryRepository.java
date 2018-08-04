package com.example.demo.repository;



import com.example.demo.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yangyueming
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{
}
