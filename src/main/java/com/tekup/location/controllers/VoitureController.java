package com.tekup.location.controllers;

import java.util.List;
import java.util.Optional;

import com.tekup.location.exception.EntityNotFoundException;
import com.tekup.location.services.IModeleService;
import com.tekup.location.services.IVoitureService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tekup.location.entities.Modele;
import com.tekup.location.entities.Voiture;
import com.tekup.location.services.ModeleService;
import com.tekup.location.services.VoitureService;


@Controller

public class VoitureController {
	
	
	@Autowired
	private IVoitureService voitureService;
	
	@Autowired
	private IModeleService modelService;
	

	//Ajouter une voiture 
	
	//1- Retourner un forumlaire d'ajout
	
	@GetMapping("/addVoiture")
	public String showaddVoiture(Model model) {
		
		Voiture voiture = new Voiture();
		List<Modele> listModel = modelService.getAllModele();
		model.addAttribute("modelList", listModel);
		model.addAttribute("VoitureForm", voiture);

		return "new_voiture";
	}
	
	//2- Ajout + redirection vers la liste des voitures
	
	@RequestMapping(value="/addVoiture", method=RequestMethod.POST)
	public String saveVoiture(Model model,@ModelAttribute("VoitureForm") Voiture voiture) {
		voitureService.addVoiture(voiture);
	
		return "redirect:/voitures";
	}
	
	//Liste des voitures
	
	@RequestMapping("/voitures")
	public String listVoitures(Model model) {
	    List<Voiture> Voitures = voitureService.getAllVoiture();

	    model.addAttribute("listVoitures", Voitures);
	     
	    return "liste_voitures";
	}

	@RequestMapping("/voituresModel/{id}")
	public String listVoituresModel(@PathVariable("id") Long id,Model model) {

		List<Voiture> Voitures = voitureService.findAllVoitureByModeleId(id);

		model.addAttribute("listVoituresModele", Voitures);

		return "liste_voitures_Modele";
	}

	@GetMapping("/editVoiture/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) throws Exception
	{
		Optional<Voiture> voiture = voitureService.getVoiture(id);
		Voiture v = voiture.get();
		model.addAttribute("voiture",v);
//Modelemodele=voiture.getModel();
//model.addAttribute("modele",modele);
		List<Modele>listModel=modelService.getAllModele();
		model.addAttribute("modelList",listModel);
		return "update_voiture";
	}
   /* public String showUpdateForm(@PathVariable("id") Long id, Model model) throws Exception {
        Optional<Voiture> voiture = voitureService.getVoiture(id);
		if (voiture != null) {
			model.addAttribute("VoitureForm", voiture);
			//Modele modele=voiture.getModel();
			//model.addAttribute("modele",modele);

			List<Modele> listModel = modelService.getAllModele();
			model.addAttribute("modelList", listModel);
		}
        return "update_voiture";
    }*/

    @PostMapping("/updateVoiture/{id}")

	public String updateVoiture(@PathVariable("id") Long id, @ModelAttribute("voiture") Voiture voitureForm, Model model ) throws Exception
	{
	Optional<Voiture> voiture= voitureService.getVoiture(id);
	Voiture v = voiture.get();
	v.setId(voitureForm.getId());
	v.setImmatriculation(voitureForm.getImmatriculation());
	v.setDateMiseEnCirculation(voitureForm.getDateMiseEnCirculation());
	v.setModel(voitureForm.getModel());
	v.setImagePath(voitureForm.getImagePath());
	v.setPrixJour(voitureForm.getPrixJour());
	voitureService.updateVoiture(v);
		model.addAttribute("listVoitures", voitureService.getAllVoiture());
		return "redirect:/voitures";
	}
    /*public String updateVoiture(@PathVariable("id") Long id, @ModelAttribute("VoitureForm") Voiture voiture, Model model
    		) throws Exception {
		Optional<Voiture> V = voitureService.getVoiture(id);
		if (V != null) {
			voitureService.updateVoiture(V);

		}
        model.addAttribute("listVoitures", voitureService.getAllVoiture());
        return "redirect:/voitures";
    }*/

	@RequestMapping(value = { "/deleteVoiture/{id}" }, method = RequestMethod.GET)
	public String deleteVoiture(Model model, @PathVariable("id") Long id ) throws EntityNotFoundException {

		Optional<Voiture> V = voitureService.getVoiture(id);

		if (V != null) {
			voitureService.deleteVoiture(id);

		}

		return "redirect:/voitures";
	}
    


}
