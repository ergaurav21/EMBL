package com.ebi.technicaltest.repository;

import com.ebi.technicaltest.entity.PersonDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<PersonDocument,String> {

      Optional<PersonDocument> findById(String personId);
}
