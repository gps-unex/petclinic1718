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
package org.springframework.samples.petclinic.repository.jpa;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetBreed;
import org.springframework.samples.petclinic.model.PetCharacter;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of the {@link PetRepository} interface.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @since 22.4.2006
 */
@Repository
public class JpaPetRepositoryImpl implements PetRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<PetType> findPetTypes() {
        return this.em.createQuery("SELECT ptype FROM PetType ptype ORDER BY ptype.name").getResultList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<PetCharacter> findPetCharacters() {
        return this.em.createQuery("SELECT c FROM PetCharacter c ORDER BY c.name").getResultList();
    }

    
    @Override
    @SuppressWarnings("unchecked")
    public List<PetBreed> findPetBreeds() {
        return this.em.createQuery("SELECT pbreed FROM PetBreed pbreed ORDER BY pbreed.id").getResultList();
    }
    
    @Override
    public Pet findById(int id) {
        return this.em.find(Pet.class, id);
    }

    @Override
    public void save(Pet pet) {
    	if (pet.getId() == null) {
    		this.em.persist(pet);     		
    	}
    	else {
    		this.em.merge(pet);    
    	}
    }

	@Override
	public void delete(int petId) {
		Pet pet=this.findById(petId);
		em.remove(pet);		
	}


	@Override
	@SuppressWarnings("unchecked")
	public Collection<Pet> findPetByNameTypeBreed(Pet pet) {
		
		String strQuery= "SELECT DISTINCT pet FROM Pet pet WHERE pet.name LIKE :name ";

		if (pet.getType()!= null){
			strQuery  = strQuery + "and pet.type =:type ";
		}
		
		if (pet.getBreed()!= null){
			strQuery  = strQuery + "and pet.breed =:breed";
		}
		
		
		Query query = this.em.createQuery(strQuery);

		query.setParameter("name", pet.getName() + "%");

        if (pet.getType()!= null){
        	query.setParameter("type", pet.getType());
        }
        if (pet.getBreed()!= null){
        	query.setParameter("breed", pet.getBreed());
        }        
        
        return query.getResultList();
	}

}
