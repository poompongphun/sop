package com.example.lab71.repository;

import com.example.lab71.pojo.Wizard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WizardRepository extends MongoRepository<Wizard, String> {
}
