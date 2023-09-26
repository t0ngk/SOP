package com.t0ng.lab06_redis;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WizardRepository extends MongoRepository<Wizard, String> {

}
