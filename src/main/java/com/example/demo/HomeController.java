package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    JobRepository jobRepository;


    @RequestMapping("/")
    public String homePage()
    {
        return "homePage";
    }

    @RequestMapping("/list")
    public String jobForm(Model model){
        model.addAttribute("jobs",jobRepository.findAll());
        return "listofJob";
    }
    @GetMapping("/add")
    public String jobF(Model model){
        model.addAttribute("job", new Job());
        return "jobForm";
    }
    @PostMapping("/process")
    public String processForm(@Valid Job job, BindingResult result)
    {
        if(result.hasErrors()){
            return "listofJob";
        }
        jobRepository.save(job);
        return "redirect:/list";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("job", jobRepository.findById(id).get());
        return "showJob";
    }

    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "jobForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") long id)
    {
        jobRepository.deleteById(id);
        return "redirect:/list";
    }

    @PostMapping("/searchJob")
    public String search(Model model, @RequestParam("searchString") String search)
    {
       model.addAttribute("jobs", jobRepository.findByTitleContainingIgnoreCase(search));
       return "jobSearch";
    }


}
