package com.example.demo;


import com.example.demo.config.JpaConfiguration;
import com.example.demo.domain.Country;
import com.example.demo.repository.CountryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {JpaConfiguration.class, SpringBootDemoApplication.class})
public class SpringBootDemoApplicationTests {
    private static Logger logger = LoggerFactory.getLogger(SpringBootDemoApplicationTests.class);
    @Autowired
    private CountryRepository countryRepository;

    @Before
    public void createCountry() {
        Country country = new Country();
        country.setCountrycode("86");
        country.setCountryname("中国");
        countryRepository.save(country);
        assert country.getId() > 0 : "create error";
    }

    @Test
    public void getData() {
        List<Country> countryList = countryRepository.findAll();
        assert countryList != null : "get data is null";
        for (Country country : countryList) {
            logger.info("==== country name = {}", country.getCountryname());
        }
    }

}
