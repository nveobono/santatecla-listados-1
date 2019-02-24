package com.urjc.daw.ViewsControllers;

import com.urjc.daw.Models.Answer.AnswerRepository;
import com.urjc.daw.Models.Concept.Concept;
import com.urjc.daw.Models.Concept.ConceptRepository;
import com.urjc.daw.Models.Concept.ConceptService;
import com.urjc.daw.Models.Item.ItemRepository;
import com.urjc.daw.Models.Item.ItemService;
import com.urjc.daw.Models.Lessons.LessonService;
import com.urjc.daw.Models.Question.Question;
import com.urjc.daw.Models.Question.QuestionRepository;
import com.urjc.daw.Models.User.User;
import com.urjc.daw.Models.User.UserComponent;
import com.urjc.daw.Models.User.UserRepository;
import com.urjc.daw.Models.Question.QuestionService;
import com.urjc.daw.Models.Answer.Answer;
import com.urjc.daw.Models.Answer.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    ItemService itemService;


    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    UserComponent userComponent;

    @Autowired
    LessonService lessonService;

    @Autowired
    ConceptRepository conceptRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ConceptService conceptService;


    @ModelAttribute
    public void addUserToModel(Model model) {
        boolean logged = userComponent.getLoggedUser() != null;
        model.addAttribute("logged", logged);
        if (logged) {
            boolean teacher;
            boolean student;
            if (userComponent.getLoggedUser().getUserType().equals("ROLE_STUDENT")) {
                student = true;
                teacher = false;
            } else {
                student = false;
                teacher = true;
            }
            model.addAttribute("admin", teacher);
            model.addAttribute("visitor", false);
            model.addAttribute("student", student);
            model.addAttribute("idUser", userComponent.getLoggedUser().getId());
        }
    }

    @GetMapping("/StudentConceptView/{idConcept}")
    public String showStudent(Model model, @PathVariable long idConcept) {
        boolean logged = userComponent.getLoggedUser() != null;
        model.addAttribute("logged", logged);
        Optional<Concept> concept = conceptRepository.findByIdConcept(idConcept);
        Optional<User> user = userRepository.findByIdUser(userComponent.getLoggedUser().getId());

        List<Answer> answerListPending = answerRepository.findByCorrectAndIdUser(false,user.get());
        List<Answer> answerListFinalPending = new ArrayList<>();
        for (Answer ans:answerListPending) {
            if(concept.get().getIdConcept()==ans.getIdConcept()){
                answerListFinalPending.add(ans);
            }
        }

        List<Answer> answerList = answerRepository.findByCorrectAndIdUser(true,user.get());
        List<Answer> answerListFinal = new ArrayList<>();
        for (Answer ans:answerList) {
            if(concept.get().getIdConcept()==ans.getIdConcept()){
                answerListFinal.add(ans);
            }
        }
        if (concept.isPresent() && user.isPresent()) {
            model.addAttribute("answer", answerListFinal);
            model.addAttribute("answerPending",answerListFinalPending );
            model.addAttribute("concept", concept.get());
            model.addAttribute("statitics",user.get().toStringStatistics(idConcept));
        }
        return "ConceptView/StudentConceptView";
    }

    @RequestMapping(path = "/")
    public String login(Model model, HttpSession session) {
        return "Login";
    }

    @GetMapping(path = "/logout")
    public String logout(Model model, HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(path = "/sign_in")
    public String signin(Model model) {
        return "Sign_in";
    }


    @PostMapping(path = "/add")
    public String add(Model model, User user) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        if (user.getName() == null || user.getpassword() == null) {
            return "redirect:/sign_in";
        } else {
            user.setUserType("ROLE_STUDENT");
            userRepository.save(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/TeacherConcept_View/{idConcept}")
    public String showConcept(Model model, @PathVariable long idConcept, @PageableDefault(value = 2) Pageable page) {
        boolean logged = userComponent.getLoggedUser() != null;
        model.addAttribute("logged", logged);
        Optional<Concept> concept = conceptRepository.findByIdConcept(idConcept);
        if (concept.isPresent()) {
            model.addAttribute("questions", questionRepository.findByidConcept(concept.get()));
            model.addAttribute("concept", concept.get());
            model.addAttribute("items", itemService.findAll(page));
        }
        return "ConceptView/TeacherConcept_View";
    }

    @GetMapping("addnewQuestion/{idQuestion}")
    public String showQuestion(Model model, @PathVariable long idQuestion) {
        Question question = questionRepository.findByidQuestion(idQuestion);
        model.addAttribute("question", question);
        return "addnewQuestion";
    }

    @RequestMapping(path = "/MainPage")
    public String showMainPage(Model model, @PageableDefault(value = 10) Pageable page) {
        boolean logged = userComponent.getLoggedUser() != null;
        model.addAttribute("logged", logged);
        model.addAttribute("lessons", lessonService.findAll(page));
        return "MainPage";
    }

    @RequestMapping(path = "/addVisitor")
    public String addVisitor(Model model, @PageableDefault(value = 10) Pageable page) {
        userRepository.save(new User("ROLE_VISITOR"));
        model.addAttribute("admin", false);
        model.addAttribute("student", false);
        model.addAttribute("visitor", true);
        model.addAttribute("logged", false);
        model.addAttribute("lessons", lessonService.findAll(page));
        model.addAttribute("concepts", conceptService.findAll(page));
        model.addAttribute("answers", answerService.findAll(page));
        model.addAttribute("questions", questionService.findAll(page));
        return "/MainPage";
    }
}
