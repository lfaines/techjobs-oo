package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */

@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "/job/{id}", method = RequestMethod.GET)
    public String index(Model model, @PathVariable int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job someJob = jobData.findById(id);
        model.addAttribute("jobs", someJob);
        Employer employers = jobData.getEmployers().findById(id);
        model.addAttribute("employers", employers);
        Location locations = jobData.getLocations().findById(id);
        model.addAttribute("locations", locations);
        CoreCompetency coreCompetencies = jobData.getCoreCompetencies().findById(id);
        model.addAttribute("coreCompetencies", coreCompetencies);
        PositionType positionTypes = jobData.getPositionTypes().findById(id);
        model.addAttribute("positionTypes", positionTypes);
        return "job-detail";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        if (errors.hasErrors()) {
            return "new-job";
        }
        if (!errors.hasErrors()) {
            Job newJob = new Job();
            jobData.add(newJob);
        }
        return "redirect:";
    }
}
// TODO #6 - Validate the JobForm model, and if valid, create a
    // new Job and add it to the jobData data store. Then
    // redirect to the job detail view for the new Job.
