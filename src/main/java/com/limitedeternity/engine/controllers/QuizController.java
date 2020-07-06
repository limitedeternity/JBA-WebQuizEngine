package com.limitedeternity.engine.controllers;

import com.limitedeternity.engine.models.*;
import com.limitedeternity.engine.repositories.QuizRepository;
import com.limitedeternity.engine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/quizzes")
@Validated
public class QuizController {

    @Autowired
    private QuizRepository quizzes;

    @Autowired
    private UserRepository users;

    @PostMapping(consumes = "application/json")
    public QuizQuestion addQuiz(@Valid @RequestBody QuizQuestion question, @AuthenticationPrincipal UserModel user) {
        question.setAuthor(user);
        return quizzes.save(question);
    }

    @GetMapping("/{id}")
    public QuizQuestion getQuiz(@PathVariable("id") @Min(0) Long id) {
        Optional<QuizQuestion> question = quizzes.findById(id);
        if (question.isPresent()) {
            return question.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such quiz");
    }

    @GetMapping
    public Page<QuizQuestion> getQuizzes(@RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, 10);
        return quizzes.findAll(pageable);
    }

    @PostMapping(value = "/{id}/solve", consumes = "application/json")
    public QuizResult solveQuiz(@PathVariable("id") @Min(0) Long id, @Valid @RequestBody QuizAnswer answer, @AuthenticationPrincipal UserModel user) {
        if (answer.getAnswer().equals(getQuiz(id).getAnswer())) {
            user.setSolvedQuizzes(Stream.concat(List.of(new SolvedQuiz(id)).stream(), user.getSolvedQuizzes().stream()).collect(Collectors.toList()));
            users.save(user);

            return new QuizResult(true, "Congratulations, you're right!");
        }

        return new QuizResult(false, "Wrong answer! Please, try again.");
    }

    @GetMapping("/completed")
    public Page<SolvedQuiz> getSolvedQuizzes(@RequestParam(required = false) Integer page, @AuthenticationPrincipal UserModel user) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, 10);
        List<SolvedQuiz> solvedQuizzes = user.getSolvedQuizzes();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), solvedQuizzes.size());
        return new PageImpl<>(user.getSolvedQuizzes().subList(start, end), pageable, solvedQuizzes.size());
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable("id") @Min(0) Long id, @AuthenticationPrincipal UserModel user) {
        QuizQuestion question = getQuiz(id);
        if (question.getAuthor().equals(user)) {
            quizzes.delete(question);
            return;
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed");
    }
}
