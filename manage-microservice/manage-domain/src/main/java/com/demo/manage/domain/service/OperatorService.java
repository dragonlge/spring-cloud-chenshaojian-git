package com.demo.manage.domain.service;

import com.demo.manage.domain.config.CacheComponent;
import com.demo.manage.domain.entity.Operator;
import com.demo.manage.domain.repository.OperatorRepository;
import com.demo.manage.domain.util.CommonUtils;
import com.demo.manage.domain.util.Constant;
import com.demo.manage.object.OperatorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yangyueming
 */
@Service
@Transactional
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private CacheComponent cacheComponent;

    public void save(Operator operator) {
        //删除缓存
        if (!StringUtils.isEmpty(operator.getId())) {
            String key = operator.getId().toString();
            cacheComponent.remove(Constant.BOSS_BACKEND_OPERATOR_ID, key);//删除原有缓存
        }
        operatorRepository.save(operator);
        //保存缓存
        if (!StringUtils.isEmpty(operator.getId())) {
            String key = operator.getId().toString();
            cacheComponent.put(Constant.BOSS_BACKEND_OPERATOR_ID, key, operator, 12);//增加缓存，保存12秒
        }
    }

    public void delete(Long id) {
        //删除缓存
        cacheComponent.remove(Constant.BOSS_BACKEND_OPERATOR_ID, id.toString());
        operatorRepository.deleteById(id);
    }

    public List<Operator> findAll() {
        return operatorRepository.findAll();
    }

    public Operator findOne(Long id) {
        Optional<Operator> operators;
        //使用缓存
        Object object = cacheComponent.get(Constant.BOSS_BACKEND_OPERATOR_ID, id.toString());
        if (CommonUtils.isNull(object)) {
            operators = operatorRepository.findById(id);
            if (operators != null) {
                cacheComponent.put(Constant.BOSS_BACKEND_OPERATOR_ID, id.toString(), operators, 12);
            }
        } else {
//            operators = (Operator) object;
        }
        return null;
//        return operators;
    }

    public Operator findByName(String name) {
        return operatorRepository.findByName(name);
    }


    public List<Operator> findByPartId(Long partId) {
        return operatorRepository.findByPartId(partId);
    }


    public Page<Operator> findAll(OperatorVo operatorVo) {
        Sort sort = new Sort(Sort.Direction.DESC, "created");
        Pageable pageable = new PageRequest(operatorVo.getPage(), operatorVo.getSize(), sort);

        return operatorRepository.findAll(new Specification<Operator>() {
            @Override
            public Predicate toPredicate(Root<Operator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<Predicate>();

                if (CommonUtils.isNotNull(operatorVo.getName())) {
                    predicatesList.add(criteriaBuilder.like(root.get("name"), "%" + operatorVo.getName() + "%"));
                }
                if (CommonUtils.isNotNull(operatorVo.getCreated())) {
                    predicatesList.add(criteriaBuilder.greaterThan(root.get("created"), operatorVo.getCreated()));
                }

                query.where(predicatesList.toArray(new Predicate[predicatesList.size()]));

                return query.getRestriction();
            }
        }, pageable);
    }

}
