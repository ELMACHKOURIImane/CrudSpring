package org.fst.patientmvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.fst.patientmvc.entities.Patient;
import org.fst.patientmvc.repositories.PatientRepository;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository ;
    @GetMapping(path = "/index")
    public String patients(Model model ,
                           @RequestParam(name = "page" , defaultValue = "0") int page
                            ,@RequestParam(name = "size" , defaultValue = "5") int size,
                           @RequestParam(name = "keyword" ,defaultValue = "") String keyword ){
        Page<Patient> PagePatients =patientRepository.findByNameContains( keyword ,PageRequest.of(page,size));
        model.addAttribute("listPatients" , PagePatients.getContent());
        model.addAttribute("pages",new int[PagePatients.getTotalPages()]);
        model.addAttribute("CurrentPage" , page);
        model.addAttribute("keyword" , keyword);
        return "patients";
    }

    @GetMapping(path = "/delete")
    public String delete(Long id , String keyword , int page)
    {
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping(path = "/")
    public String home()
    {
        return "redirect:/index";
    }

    @GetMapping("/formPatient")
    public String formPatient(Model model)
    {
        model.addAttribute("patient" , new Patient());
       return "formPatient";
    }

    @PostMapping("/save")
    public  String save(@Valid  Patient patient , BindingResult bindingResult ,
                        @RequestParam(defaultValue = "") String keyword ,
                        @RequestParam(defaultValue = "0") int page)
    {
        if(bindingResult.hasErrors()) return "formPatient" ;
     patientRepository.save(patient);
     return  "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping(path = "/editPatient")
    public String editPatient(Long id , Model model , String keyword , int page)
    {
        Patient patient = patientRepository.findById(id).orElse(null) ;
        if(patient==null)throw  new RuntimeException("Patient introuvable");
        model.addAttribute("patient" ,patient);
        model.addAttribute("page" , page);
        model.addAttribute("keyword" ,keyword);
        return "editPatient";
    }

}
