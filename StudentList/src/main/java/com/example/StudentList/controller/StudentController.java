package com.example.StudentList.controller;

import com.example.StudentList.dto.request.AccountBalanceRequest;
import com.example.StudentList.dto.request.StudentRequest;
import com.example.StudentList.dto.response.StudentGroupResponse;
import com.example.StudentList.dto.response.StudentResponse;
import com.example.StudentList.entity.StudentGroup;
import com.example.StudentList.repository.GroupRepository;
import com.example.StudentList.service.StudentServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
  private final StudentServiceImpl studentService;

  @ApiOperation(value = "Add student", notes = "Adding a new student to the DB.")
  @PostMapping("/saveData")
  public void createDemo(@RequestBody StudentRequest request) throws IOException {
    studentService.saveStudentAllData(request);
  }

  @PostMapping("/save")
  public String save(@RequestBody StudentRequest request) {
    return studentService.save(request);
  }

  @ApiOperation(value = "Get student", notes = "Get student by entered ID")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "SUCCESSFULLY"), @ApiResponse(code = 404, message = "NOT FOUND")})
  @GetMapping("/get/{id}")
  public ResponseEntity<StudentResponse> getStudent(@ApiParam(name = "id", value = "Student ID", example = "1") @PathVariable Long id) {
    return new ResponseEntity<>(studentService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public List<StudentResponse> getByName(@PathVariable(name = "name") String name) {
    return studentService.getByName(name);
  }

  @ApiResponses(value = {@ApiResponse(code = 200, message = "SUCCESSFULLY"), @ApiResponse(code = 404, message = "NOT FOUND")})
  @ApiOperation(value = "Get students", notes = "Getting all saved students from DB")
  @GetMapping("/students")
  public ResponseEntity<List<StudentGroupResponse>> getAllStudents() {
    return studentService.getAll();
  }

  @ApiOperation(value = "Update by ID", notes = "Update student by entered ID ")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "SUCCESSFULLY"), @ApiResponse(code = 404, message = "NOT FOUND")})
  @PutMapping("/update/{id}")
  public String update(@RequestParam MultipartFile file, StudentRequest studentRequest,
                       @ApiParam(name = "id", value = "Student ID", example = "1")
                       @PathVariable Long id) throws IOException {
    return studentService.update(studentRequest, id, file);
  }

  @ApiOperation(value = "Delete by ID", notes = "Delete student by entered ID")
  @DeleteMapping("/delete/{id}")
  public String remove(@ApiParam(name = "id", value = "Student ID", example = "1") @PathVariable Long id) {
    return studentService.deleteById(id);
  }

  @GetMapping("/image")
  public ResponseEntity<Resource> getStudentImageById(@RequestParam Long id) {
    return studentService.getStudentImage(id);
  }

  @PatchMapping("/transaction")
  String cardToCard(@RequestParam Long from, @RequestParam Long to, @RequestParam Double amount) {
    return studentService.transaction(from, to, amount);
  }

  @PostMapping("/newAccount")
  public void createNewAccount(@RequestBody AccountBalanceRequest request) {
    studentService.creatBalanceAccount(request);
  }

}