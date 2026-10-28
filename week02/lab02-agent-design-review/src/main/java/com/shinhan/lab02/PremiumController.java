package com.shinhan.lab02;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/contracts") public class PremiumController {
 private final PremiumService service;
 public PremiumController(PremiumService service){this.service=service;}
 @GetMapping("/{id}/premium") public PremiumService.PremiumQuote premium(@PathVariable long id){return service.quote(id);}
 @ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<String> missing(IllegalArgumentException e){return ResponseEntity.notFound().build();}
 @ExceptionHandler(IllegalStateException.class) public ResponseEntity<String> inactive(IllegalStateException e){return ResponseEntity.badRequest().body(e.getMessage());}
}