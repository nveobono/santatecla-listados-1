package com.urjc.daw.models.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    public List<Question> findAll() {
        return repository.findAll();
    }

    public Optional<Question> findOne(Long idItem) {
        return repository.findById(idItem);
    }

    public void addQuestion(Question question) {
        repository.save(question);
    }

    public void deleteQuestionById(long id) {
        repository.deleteById(id);
    }

    public Iterable<Question> findAllById() {
        return repository.findAll();
    }

    public void delete(long id) { repository.delete(repository.getOne(id)); }

    //public question findByConceptAndId (Optional<concept> concept, long id){ return repository.findByConceptAndId(concept.get(), id);}
}