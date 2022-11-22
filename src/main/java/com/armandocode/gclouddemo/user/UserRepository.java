package com.armandocode.gclouddemo.user;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends DatastoreRepository<Userapp, Long> {

	Optional <Userapp> findByUsername(String username);

}
