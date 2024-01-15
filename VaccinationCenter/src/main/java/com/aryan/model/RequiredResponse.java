package com.aryan.model;
import java.util.List;

import com.aryan.entity.VaccinationCenter;
public class RequiredResponse {
private VaccinationCenter center;
private List<Citizen> citizens;
//PDC+PPC+PSM+PGM

public RequiredResponse() {
	super();
}
public RequiredResponse(VaccinationCenter center, List<Citizen> citizens) {
	super();
	this.center = center;
	this.citizens = citizens;
}
public VaccinationCenter getCenter() {
	return center;
}
public void setCenter(VaccinationCenter center) {
	this.center = center;
}
public List<Citizen> getCitizens() {
	return citizens;
}
public void setCitizens(List<Citizen> citizens) {
	this.citizens = citizens;
}

}
