package com.umair.journalApp.repository;

import com.umair.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>
{
  User findByUsername(String username);
}
