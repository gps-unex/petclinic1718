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
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetBreed;
import org.springframework.samples.petclinic.model.PetCharacter;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.ClinicRepository;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClinicServiceImpl implements ClinicService {

	private PetRepository petRepository;
	private VetRepository vetRepository;
	private OwnerRepository ownerRepository;
	private VisitRepository visitRepository;
	private ClinicRepository clinicRepository;

	@Autowired
	public ClinicServiceImpl(PetRepository petRepository, VetRepository vetRepository, OwnerRepository ownerRepository,
			VisitRepository visitRepository, ClinicRepository clinicRepository) {
		this.petRepository = petRepository;
		this.vetRepository = vetRepository;
		this.ownerRepository = ownerRepository;
		this.visitRepository = visitRepository;
		this.clinicRepository = clinicRepository;
	}

	@Override
	@Transactional
	public void saveClinic(Clinic clinic) throws DataAccessException {
		clinicRepository.save(clinic);

	}

	@Override
	public Clinic getClinicInfo() throws DataAccessException {
		return clinicRepository.get();
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<PetCharacter> findPetCharacters() throws DataAccessException {
		return petRepository.findPetCharacters();
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<PetBreed> findPetBreeds() throws DataAccessException {
		return petRepository.findPetBreeds();
	}

	@Override
	@Transactional(readOnly = true)
	public Owner findOwnerById(int id) throws DataAccessException {
		return ownerRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	@Transactional
	public void saveOwner(Owner owner) throws DataAccessException {
		ownerRepository.save(owner);
	}

	@Override
	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}
	
	@Override
	@Transactional
	public void deleteVisit(int visitId) throws DataAccessException {
		visitRepository.delete(visitId);
	}

	@Override
	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Override
	@Transactional
	public void savePet(Pet pet) throws DataAccessException {
		petRepository.save(pet);
	}

	@Override
	@Transactional(readOnly = true)
	// @Cacheable(value = "vets")
	public Collection<Vet> findVets() throws DataAccessException {
		return vetRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Vet findVetById(int id) throws DataAccessException {
		return vetRepository.findById(id);
	}

	@Override
	@Transactional
	public void saveVet(Vet vet) throws DataAccessException {
		vetRepository.save(vet);
	}

	@Override
	@Transactional
	public void deletePetById(int petId) throws DataAccessException {
		petRepository.delete(petId);
	}

	@Override
	public Collection<Specialty> findSpecialties() throws DataAccessException {
		return vetRepository.findSpecialties();
	}

	@Override
	public Specialty findSpecialtyByName(String specialty) throws DataAccessException {
		return vetRepository.findSpecialtyByName(specialty);
	}

	@Override
	public Object findTodayVisits(int vetId) throws DataAccessException {
		return vetRepository.findTodayVisits(vetId);
	}

	@Override
	@Transactional(readOnly = true)
	public Visit findVisit(int visitId) throws DataAccessException {
		return visitRepository.findById(visitId);
	}

	@Override
	public Collection<Pet> findPetByNameTypeBreed(Pet pet) {
		return petRepository.findPetByNameTypeBreed(pet);
	}

	@Override
	public Collection<Visit> findVisitsByDate(Integer petId, String start, String end) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd");
		DateTime dtStart = formatter.parseDateTime(start);

		DateTime dtEnd = formatter.parseDateTime(end);
		return visitRepository.findVisitsByDate(petId, dtStart, dtEnd);
	}
	
	@Override
	public Collection<Visit> findVisits (Visit visit) {
		return visitRepository.findVisits(visit);
	}

}
