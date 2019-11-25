package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    CloudinaryConfig cloudc;

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
    @GetMapping("/add")
    public String jobF(Model model){
        model.addAttribute("job", new Job());
        return "jobForm";
    }

    @PostMapping("/process")
    public String processForm(@Valid Job job, BindingResult result)
    {    if(result.hasErrors()){
        return "jobForm";
    }

        jobRepository.save(job);
        return "redirect:/list";
    }

    /*
    @PostMapping("/process")
    public String processForm(@Valid Job job, BindingResult result, @ModelAttribute Job jobc, @RequestParam("file") MultipartFile file )
    {    if(result.hasErrors()){
        return "jobForm";
    }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            job.setPhoto(uploadResult.get("url").toString());
            jobRepository.save(jobc);
        }
            catch(IOException e){
                e.printStackTrace();
                return "redirect:/list";
            }
        jobRepository.save(job);
        return "redirect:/";
    }


*/
}
