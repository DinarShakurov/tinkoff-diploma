package ru.tfs.spring.hw.data.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = BaseController.SERVICE_URL)
public interface BaseController {

    String SERVICE_URL = "/api/public/";
}
