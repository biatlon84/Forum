package com.biathlon84.forum.section;


import com.biathlon84.forum.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/sections/")
public class SectionController {

    @Autowired private SectionService sectionService;
    @Autowired private TopicService topicService;

    @RequestMapping("{id}")
    public String getTopicsFromSection(@PathVariable int id, Model model, Authentication authentication) {

        System.out.println("get /a/sections/'id'");

        //authentication.getAuthorities().

        model.addAttribute("section", sectionService.findOne(id));
        model.addAttribute("topics", topicService.findBySection(id));
        //return "section";
        return "redirect:/a/sections";
    }

}
