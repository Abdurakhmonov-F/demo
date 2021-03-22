package com.example.demo.repository;

import com.example.demo.data.Cars;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CarsRepository extends ReactiveMongoRepository<Cars,String> {

}
