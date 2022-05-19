package ru.tfs.spring.hw.data.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tfs.spring.hw.data.common.JsonItem;
import ru.tfs.spring.hw.data.common.JsonItemFactory;
import ru.tfs.spring.hw.data.dto.PersonDto;
import ru.tfs.spring.hw.data.dto.PrettyPersonDto;
import ru.tfs.spring.hw.data.entity.DocumentType;
import ru.tfs.spring.hw.data.services.person.PersonService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class PersonController implements BaseController {

    private final JsonItemFactory jsonItemFactory;
    private final PersonService personService;

    @PutMapping(path = {"/person"})
    public JsonItem update(@RequestBody @Valid PersonDto personDto) {
        personService.update(personDto);
        return jsonItemFactory.successResult();
    }

    @PostMapping(path = {"/person"})
    public JsonItem create(@RequestBody @Valid PersonDto personDto) {
        personService.create(personDto);
        return jsonItemFactory.successResult();
    }

    @GetMapping("/person/{personId}")
    public JsonItem<PersonDto> findPersonById(@PathVariable Long personId) {
        return jsonItemFactory.successResult(personService.getById(personId));
    }

    @GetMapping("/person")
    public JsonItem<PersonDto> findPerson(@RequestParam String passport) {
        return jsonItemFactory.successResult(personService.findByDocument(DocumentType.PASSPORT, passport));
    }

    @GetMapping("/persons")
    public JsonItem<Collection<PrettyPersonDto>> findActivePersons(
            @PageableDefault(sort = "createdBy", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String region) {
        return jsonItemFactory.successResult(personService.findActivePersons(pageable, region));
    }

    @GetMapping("/person/verify")
    public JsonItem<Boolean> verifyNameAndPassport(@RequestParam String name,
                                                   @RequestParam String passport) {
        return jsonItemFactory.successResult(
                personService.verifyNameAndPassport(name, passport, DocumentType.PASSPORT));
    }
}
