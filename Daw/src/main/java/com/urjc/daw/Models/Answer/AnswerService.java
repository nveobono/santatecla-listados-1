package com.urjc.daw.Models.Answer;

import com.urjc.daw.Models.Question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository repository;

    public List<Answer> findAll(){
        return repository.findAll();
    }

    public Optional<Answer> findOne(Long idItem) {
        return repository.findById(idItem);
    }

    public Optional<Question> findQuestion(Long idQuestion){ return repository.findByIdQuestion(idQuestion);}

}