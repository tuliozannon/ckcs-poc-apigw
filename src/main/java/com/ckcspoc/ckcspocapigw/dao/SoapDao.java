package com.ckcspoc.ckcspocapigw.dao;

import com.ckcspoc.ckcspocapigw.model.DbSoap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoapDao extends CrudRepository<DbSoap, Integer> {
}
