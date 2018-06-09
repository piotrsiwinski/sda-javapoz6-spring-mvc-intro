package pl.sda.poznan.springmvcintro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {

  @GetMapping({"", "/index"})
  public String hello() {
    return "home/index";
  }

  @GetMapping("/contact")
  public String contact() {
    return "home/contact";
  }

  @GetMapping("/about")
  public String about() {
    return "home/about";
  }


}
