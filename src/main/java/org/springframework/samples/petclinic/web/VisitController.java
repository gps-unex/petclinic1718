/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes("visit")
public class VisitController {

    private final ClinicService clinicService;


    @Autowired
    public VisitController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @ModelAttribute("vets")
    public Collection<Vet> populateVets() {
        return this.clinicService.findVets();
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/owners/*/pets/{petId}/visits/new", method = RequestMethod.GET)
    public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
        Pet pet = this.clinicService.findPetById(petId);
        Visit visit = new Visit();
        pet.addVisit(visit);
        model.put("visit", visit);
        return "pets/createOrUpdateVisitForm";
    }

    @RequestMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new", method = RequestMethod.POST)
    public String processNewVisitForm(@Valid Visit visit, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            this.clinicService.saveVisit(visit);
            status.setComplete();
            return "redirect:/owners/{ownerId}";
        }
    }
    
    @RequestMapping(value = "/owners/{ownerId}/pets/{petId}/visits/{visitId}/delete", method = RequestMethod.GET)
    public String processDeleteVisitForm(@PathVariable("visitId") int visitId, Map<String, Object> model) {      
        this.clinicService.deleteVisit(visitId);
        return "redirect:/owners/{ownerId}";
   
    }
    

    @RequestMapping(value = "/owners/*/pets/{petId}/visits/filter", method = RequestMethod.GET)
    public String initNewVisitFilter(@PathVariable("petId") int petId, Map<String, Object> model) {
        Pet pet = this.clinicService.findPetById(petId);
        model.put("visits", pet.getVisits());
        return "pets/visitsList";
    }

    @RequestMapping(value = "/owners/{ownerId}/pets/{petId}/visits/filter", method = RequestMethod.POST)
    public String processNewVisitFilter(@PathVariable("petId") int petId, @RequestParam("start") String start, @RequestParam("end") String end,   Map<String, Object> model) {
      model.put("visits",this.clinicService.findVisitsByDate(petId, start, end));
      return "pets/visitsList";
      
    }
    
    

    
    @RequestMapping(value = "/*/*/pets/*/visit/{visitId}/edit", method = RequestMethod.GET)
    public String initEditVisitForm(@PathVariable("visitId") int visitId, Map<String, Object> model) {
        Visit visit = this.clinicService.findVisit(visitId);
        model.put("visit", visit);
        return "pets/createOrUpdateVisitForm";
    }

   
    @RequestMapping(value = "/{editor}/{id}/pets/{petId}/visit/{visitId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public String processEditVisitForm(@ModelAttribute("visit") Visit visit, @PathVariable("editor") String editor, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            this.clinicService.saveVisit(visit);
            status.setComplete();            
            return "redirect:/"+editor+"/{id}";
        }
    }


    @RequestMapping(value = "/owners/*/pets/{petId}/visits", method = RequestMethod.GET)
    public ModelAndView showVisits(@PathVariable int petId) {
        ModelAndView mav = new ModelAndView("visitList");
        mav.addObject("visits", this.clinicService.findPetById(petId).getVisits());
        return mav;
    }
    
    @RequestMapping(value = "/pets/visits/find", method = RequestMethod.GET)
    public String initFindForm(Map<String, Object> model) {
    	model.put("visit", new Visit());
        return "pets/findVisits";
    }
    
    @RequestMapping(value = "/pets/visits", method = RequestMethod.GET)
    public String processFindForm(Visit visit, BindingResult result, Map<String, Object> model) {
        Collection<Visit> visits = this.clinicService.findVisits(visit);
        model.put("visits", visits);
        return "pets/visitsListComplete";
    }

}
