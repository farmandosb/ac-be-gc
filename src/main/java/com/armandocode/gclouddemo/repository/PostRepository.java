package com.armandocode.gclouddemo.repository;


import com.armandocode.gclouddemo.model.Post;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface PostRepository extends DatastoreRepository<Post, Long> {

}
