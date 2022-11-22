package com.armandocode.gclouddemo.user;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface RoleRepository extends DatastoreRepository<Role, Integer> {

}
