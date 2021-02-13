package com.ebi.technicaltest.service;

import com.ebi.technicaltest.exception.NotFoundException;
import com.ebi.technicaltest.entity.PersonDocument;
import com.ebi.technicaltest.model.PersonRequest;
import com.ebi.technicaltest.model.PersonResponse;
import com.ebi.technicaltest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

  private final PersonRepository personRepository;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public PersonDocument save(PersonDocument personDocument) {
    return personRepository.save(personDocument);
  }


  public void update(PersonRequest personRequest,String personId) throws NotFoundException{

    PersonDocument personDocument = getPersonById(personId);

    if (personRequest.getFirst_name() != null && !personRequest.getFirst_name().isEmpty()) {
      personDocument.setFirst_name(personRequest.getFirst_name());
    }

    if (personRequest.getLast_name() != null && !personRequest.getLast_name().isEmpty()) {
      personDocument.setLast_name(personRequest.getLast_name());
    }

    if (personRequest.getAge() != null) {
      personDocument.setAge(personRequest.getAge());
    }

    if (personRequest.getFavorite_colour() != null
        && !personRequest.getFavorite_colour().isEmpty()) {
      personDocument.setFavorite_colour(personRequest.getFavorite_colour());
    }

    this.save(personDocument);
  }

  public void delete(final String personId) {
    personRepository.deleteById(personId);
  }

  public Page<PersonResponse> getAllPerson(final Pageable pageable) {

    List<PersonDocument> templateDocuments = personRepository.findAll();
    List<PersonResponse> templateResponse =
        templateDocuments.stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .map(
                    personDocument ->
                    new PersonResponse(
                        personDocument.getId(),
                        personDocument.getFirst_name(),
                        personDocument.getLast_name(),
                        personDocument.getFavorite_colour(),
                        personDocument.getAge()))
            .collect(Collectors.toList());
    return new PageImpl<>(templateResponse, pageable, getCount());
  }

  public PersonResponse getPersonWithId(final String personId) {

    PersonDocument personDocument = getPersonById(personId);

    PersonResponse personResponse =
        new PersonResponse(
            personDocument.getId(),
            personDocument.getFirst_name(),
            personDocument.getLast_name(),
            personDocument.getFavorite_colour(),
            personDocument.getAge());

    return personResponse;
  }

  private long getCount() {
    return personRepository.count();
  }

  private PersonDocument getPersonById(String personId) throws NotFoundException{

    Optional<PersonDocument> personDocument = personRepository.findById(personId);

    if (!personDocument.isPresent()) {
      throw new NotFoundException(PersonDocument.class.getSimpleName(), personId);
    }

    return personDocument.get();
  }
}
