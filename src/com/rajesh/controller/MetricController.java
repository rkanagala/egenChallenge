package com.rajesh.controller;

import java.util.List;
import javax.ws.rs.FormParam;

import org.easyrules.api.RulesEngine;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rajesh.bean.Alerts;
import com.rajesh.bean.Metrics;
import com.rajesh.morphia.MorphiaService;
import com.rajesh.rules.RuleOverWeight;
import com.rajesh.rules.RuleUnderWeight;

@Controller
public class MetricController {
	private @Autowired RulesEngine rulesEng;
	final Datastore db;
	MorphiaService morphiaService = new MorphiaService();
	public MetricController(){
		this.morphiaService = new MorphiaService();
		db =  morphiaService.getDatastore();
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	  public ModelAndView metricForm() {
	    return new ModelAndView("MetricForm", "command", new Metrics());
	}
	
	@RequestMapping(value = "addMetrics", method = RequestMethod.GET)
	  public @ResponseBody ModelAndView addMetrics(@ModelAttribute("SpringWeb")Metrics metrics, ModelMap model, @FormParam("weight") float weight,
			  @FormParam("timeStamp") String timeStamp) {
		 db.save(metrics);
		 		 
		 
	     //Assuming baseWeight is the first value that is read from sensory emulator.
		 float baseWeight = GetBaseWeight(db);
		 
	     RuleOverWeight overWeightRule = new RuleOverWeight(metrics, baseWeight);
	     rulesEng.registerRule(overWeightRule);
	     RuleUnderWeight underWeightRule = new RuleUnderWeight(metrics, baseWeight);
	     rulesEng.registerRule(underWeightRule);
	     rulesEng.fireRules();
	     if(overWeightRule.IsEmployeeOverWeight)
	     {
	    	 Alerts alert = new Alerts();
	    	 alert.setTimestamp(timeStamp);
	    	 alert.setWeight(weight);
	    	 alert.setAlertMessage("Person is Over Weight.");
	    	 db.save(alert);
	     }
	     else if(underWeightRule.IsEmployeeUnderWeight)
	     {
	    	 //Create rule
	    	 Alerts alert = new Alerts();
	    	 alert.setTimestamp(timeStamp);
	    	 alert.setWeight(weight);
	    	 alert.setAlertMessage("Person is Under Weight.");
	    	 db.save(alert);
	     }
	     return new ModelAndView("SuccessForm", "model", model);
	   }
	
	@RequestMapping(value="readMetrics", method=RequestMethod.GET)
	  public @ResponseBody ModelAndView readMetrics(@ModelAttribute("SpringWeb")Metrics met, ModelMap model){
		  Query<Metrics> query = db.createQuery(Metrics.class);
		  final List<Metrics> metrics = query.asList();
		  model.put("read", metrics);
		  return new ModelAndView("ReadForm", "model", model);
	  }
	
	@RequestMapping(value="readMetricsByTime", method=RequestMethod.GET)
	public @ResponseBody ModelAndView readMetricsByTime(@ModelAttribute("SpringWeb")Metrics met, ModelMap model, @FormParam("fromDate") String fromDate,
			@FormParam("toDate") String toDate){
		  Query<Metrics> query = db.createQuery(Metrics.class);
		  query.and(query.criteria("timestamp").greaterThanOrEq(fromDate),
				  query.criteria("timestamp").lessThanOrEq(toDate)
				  );
		  final List<Metrics> metrics = query.asList();		  
		  model.put("readByDate", metrics);
		  return new ModelAndView("ReadByTimeForm", "model", model);
	  }
	
	@RequestMapping(value="readAlerts", method=RequestMethod.GET)
	  public @ResponseBody ModelAndView readAlerts(@ModelAttribute("SpringWeb")Alerts met, ModelMap model){
		  Query<Alerts> query = db.createQuery(Alerts.class);
		  final List<Alerts> metrics = query.asList();
		  model.put("readAlerts", metrics);
		  return new ModelAndView("ReadAlertForm", "model", model);
	  }
	
	@RequestMapping(value="readAlertsByTime", method=RequestMethod.GET)
	public @ResponseBody ModelAndView readAlertsByTime(@ModelAttribute("SpringWeb")Alerts met, ModelMap model, @FormParam("fromDate") String fromDate,
			@FormParam("toDate") String toDate){
		  Query<Alerts> query = db.createQuery(Alerts.class);
		  query.and(query.criteria("timestamp").greaterThanOrEq(fromDate),
				  query.criteria("timestamp").lessThanOrEq(toDate)
				  );
		  final List<Alerts> metrics = query.asList();		  
		  model.put("readAlertsByTime", metrics);
		  return new ModelAndView("ReadAlertByTimeForm", "model", model);
	  }
	
	//calculates the base weight assuming that the first weight value sent is the base weight
	private float GetBaseWeight(Datastore db){
		  float baseWeight = 0;
		  Query<Metrics> query = db.createQuery(Metrics.class);
		  final List<Metrics> metrics = query.order("timestamp").asList();
		  if(metrics.size() > 0){
			 baseWeight = metrics.get(0).getWeight();
		  }

          return baseWeight;		 
	}
	
}