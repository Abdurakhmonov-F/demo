package com.example.demo.controller;


import com.example.demo.data.Cars;
import com.example.demo.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/cars")
public class CarsController {

    private final CarsRepository carsRepository;

    public CarsController(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }
    @PostMapping("/save")
    public Mono<Cars> save(@RequestBody Cars cars){
        return carsRepository.save(cars).log("save method accessed");
    }
    @GetMapping("/getAll")
    public Flux<Cars> getAll(){
        return carsRepository.findAll().log("Get all method accessed");
    }
    @GetMapping("/finById/{id}")
    public Mono<ResponseEntity<Cars>> findById(@PathVariable String id){
       return carsRepository.findById(id).log("Found by Id")
                .map(x -> ResponseEntity.ok(x))
               .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping("/update")
    public Mono<ResponseEntity<Cars>> update(@RequestBody Cars cars){
     return carsRepository.findById(cars.getId()).log("update method accessed")
              .flatMap(x->{
                  x.setBrand(x.getBrand());
                  x.setId(x.getId());
                  x.setModel(x.getModel());
                  x.setPrice(x.getPrice());
                  x.setDescription(x.getDescription());
                 return carsRepository.save(x);

              })
              .map(x-> ResponseEntity.ok(x))
              .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
       return carsRepository.findById(id).log("delete method accessed")
               .flatMap(x -> {
                   Mono<Void> delete = carsRepository.delete(x);
                   return delete;
               })
               .map(x -> ResponseEntity.ok(x))
               .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
