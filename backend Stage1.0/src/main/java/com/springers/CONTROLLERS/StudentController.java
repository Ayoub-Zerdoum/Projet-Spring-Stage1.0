package com.springers.CONTROLLERS;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Offer;
import com.springers.ENTITIES.Role;
import com.springers.ENTITIES.Specialization;
import com.springers.ENTITIES.Student;
import com.springers.ENTITIES.StudentStatus;
import com.springers.SERVICES.Service_EmailSender;
import com.springers.SERVICES.Service_Offer;
import com.springers.SERVICES.Service_Student;

@RestController
@CrossOrigin
@RequestMapping("/api/students")
public class StudentController {
	@Autowired
    private Service_Student studentService;
	
	@Autowired
	private Service_EmailSender ServiceEmail;
	
	@Autowired
	private Service_Offer offerService;

	@Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student std) {
    	studentService.ajouter_Student(std);
        return ResponseEntity.ok("Student added successfully");
    }
    
	@Secured("ROLE_ADMIN")
    @PostMapping("/addv2")
    public ResponseEntity<Map<String, String>> addStudentv2(@RequestBody Map<String, Object> studentData) {
		
    	// Extract data from the map
        String username = (String) studentData.get("username");
        String password = (String) studentData.get("password");
        String email = (String) studentData.get("email");
        String telephone = (String) studentData.get("telephone");
        LocalDate dateOfBirth = LocalDate.parse((String) studentData.get("dateOfBirth"));
        Boolean enrolled = (Boolean) studentData.get("enrolled");
        Boolean sendVerificationEmail = (Boolean) studentData.get("sendVerificationEmail");
        Specialization specialization = Specialization.valueOf((String) studentData.get("specialization"));

        // Determine the StudentStatus based on the enrolled status
        StudentStatus studentStatus = enrolled ? StudentStatus.ENROLLED : StudentStatus.GRADUATED;

        // Set the account status to ACTIVE for all newly added students
        AccountStatus accountStatus = AccountStatus.ACTIVE;

        BCryptPasswordEncoder Bcrypt = new BCryptPasswordEncoder();
        //Create the Student object using builder pattern
        Student student = Student.builder()
                                .username(username)
                                .password(Bcrypt.encode(password))
                                .role(Role.STUDENT)
                                .email(email)
                                .telephone(telephone)
                                .dateOfBirth(dateOfBirth)
                                .studentStatus(studentStatus)
                                .accountStatus(accountStatus)
                                .specialization(specialization)
                                .build();

        // Call the service method to add the student
        studentService.ajouter_Student(student);
        
        if(sendVerificationEmail == true) {
        	ServiceEmail.sendemail(email, "Invitation a rejoindre l'application Stage1.0",
        		"L'administration de l'Enicar vous a inviter pour rejoindre l'application Stage1.0\n "
        		+ "Votre nom d'utilisateur est :" + student.getUsername() + "\nVotre Mot de passe  est :" + password);
        	System.out.print("email sent succesfully to :" + student.getUsername());
        }
        
        
        
        

     // Create a response map with a success message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student added successfully");

        // Return the response map as JSON
        return ResponseEntity.ok(response);
    }

	@Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
    	studentService.supprimer_Student(id);
    	Map<String, String> response = new HashMap<>();
        response.put("message", "Student deleted successfully");
        
        return ResponseEntity.ok(response);
    }
    
	@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.afficher_Students();
        return ResponseEntity.ok(students);
    }
    
	@Secured("ROLE_ADMIN")
    @GetMapping("/search-username")
    public ResponseEntity<List<Student>> searchStudentsByUsername(@RequestParam("username") String usernameQuery) {
        List<Student> students = studentService.searchStudentsByUsername(usernameQuery);
        return ResponseEntity.ok(students);
    }
    
	@Secured("ROLE_ADMIN")
    @GetMapping("/search-email")
    public ResponseEntity<List<Student>> searchStudentsByEmail(@RequestParam("email") String emailQuery) {
        List<Student> students = studentService.searchStudentsByEmail(emailQuery);
        return ResponseEntity.ok(students);
    }

	@Secured("ROLE_ADMIN")
    @GetMapping("/search-telephone")
    public ResponseEntity<List<Student>> searchStudentsByTelephone(@RequestParam("telephone") String telephoneQuery) {
        List<Student> students = studentService.searchStudentsByTelephone(telephoneQuery);
        return ResponseEntity.ok(students);
    }
    
	@Secured("ROLE_ADMIN")
    @GetMapping("/search-date-of-birth")
    public ResponseEntity<List<Student>> searchStudentsByDateOfBirth(@RequestParam("dateOfBirth") String dateOfBirthQuery) {
        List<Student> students = studentService.searchStudentsByDateOfBirth(dateOfBirthQuery);
        return ResponseEntity.ok(students);
    }
    
	@Secured("ROLE_ADMIN")
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudents(@RequestParam(required = false) String studentStatus,
                                                         @RequestParam(required = false) String specialization,
                                                         @RequestParam(required = false) String accountStatus,
                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dobMin,
                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dobMax) {
        List<Student> filteredStudents = studentService.filterStudents(studentStatus, specialization, accountStatus, dobMin, dobMax);
        return ResponseEntity.ok(filteredStudents);
    }
	
	@Secured("ROLE_ADMIN")
    @PutMapping("/suspend/{id}")
    public ResponseEntity<Map<String,String>> suspendAccount(@PathVariable Long id) {
        studentService.suspendAccount(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student account suspended successfully");

        // Return the response map as JSON
        return ResponseEntity.ok(response);
    }
    
	@Secured("ROLE_ADMIN")
    @PutMapping("/activate/{id}")
    public ResponseEntity<Map<String, String>> activateAccount(@PathVariable Long id) {
        studentService.activateAccount(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student account activated successfully");
        return ResponseEntity.ok(response);
    }
    
	@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @PutMapping("/edit/{id}")
    public ResponseEntity<Map<String, String>> editStudent(@PathVariable Long id, @RequestBody Map<String, Object> studentData) {
        studentService.editStudent(id, studentData);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student edited successfully");
        return ResponseEntity.ok(response);
    }
    
	@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @GetMapping("/offre/all")
    public ResponseEntity<List<Map<String, Object>>> getOfferApplications(@RequestParam Long studentId,
												    		@RequestParam(defaultValue = "0") int page,
												            @RequestParam(defaultValue = "5") int size) {
        Student student = studentService.getStudentById(studentId);
        if (student != null) {
        	List<Offer> offers = studentService.AfficherListOffre(studentId,page,size);
        	List<Map<String, Object>> offersWithRanks = new ArrayList<>();
    	    
    	    for (Offer offer : offers) {
    	        Integer rank = studentService.calculateRankInOffer(studentId, offer.getId());
    	        Map<String, Object> offerMap = new HashMap<>();
    	     // Add offer properties to the map
    	        offerMap.put("id", offer.getId());
    	        offerMap.put("specialization", offer.getSpecialization());
    	        offerMap.put("title", offer.getTitle());
    	        offerMap.put("description", offer.getDescription());
    	        offerMap.put("deadline", offer.getDeadline());
    	        offerMap.put("nomSociete", offer.getNomSociete());
    	        offerMap.put("isActive", offer.getIsActive());
    	        offerMap.put("mailRH", offer.getMailRH());
    	        offerMap.put("nbPlaces", offer.getNbPlaces());
    	        offerMap.put("localisation", offer.getLocalisation());
    	        offerMap.put("offerApplications", offer.getOfferApplications());
    	        offerMap.put("rank", rank);
    	        offersWithRanks.add(offerMap);
    	    }
            return ResponseEntity.ok(offersWithRanks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
	@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @GetMapping("/offre/all/taille/{studentId}")
	public ResponseEntity<Integer> getAllOffersSize(@PathVariable Long studentId){
		Integer taille = studentService.AfficherListOffre(studentId).size();

        return ResponseEntity.ok(taille);
	}
    
	@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @PostMapping("/offre/add")
    public ResponseEntity<Map<String, String>> addOffer(@RequestParam Long offerId,@RequestParam Long studentId) {
    	studentService.ReserveOffer(offerId, studentId);
    	
    	Map<String, String> response = new HashMap<>();
        response.put("message", "Student added successfully");

        // Return the response map as JSON
        return ResponseEntity.ok(response);
    }
    
	@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @GetMapping("/offre/rank")
    public ResponseEntity<Map<String, Integer>> getRankOffer(@RequestParam Long studentId,
    														 @RequestParam Long offerId) {
        Student student = studentService.getStudentById(studentId);
        if (student != null) {
        	int rank = studentService.calculateRankInOffer(studentId,offerId);
            Map<String, Integer> response = new HashMap<>();
            response.put("rank", rank);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
