package com.tcc.areader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.areader.model.Group;
import com.tcc.areader.request.AnnotationToGroupRequest;
import com.tcc.areader.service.GroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<Group> createGroup(@RequestParam Long libraryBookId, @RequestParam String password){
        return new ResponseEntity<>(groupService.CreateGroup(libraryBookId,password), HttpStatus.CREATED);
    }

    @PatchMapping("/addAnnotation")
    public ResponseEntity<Group> addAnnotationToGroup(@RequestBody @Valid AnnotationToGroupRequest addAnnotationToGroupRequest){
        Group group = groupService.addAnnotationToGroup(addAnnotationToGroupRequest.getIdGroup(), addAnnotationToGroupRequest.getIdAnnotation());
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PatchMapping("/removeAnnotation")
    public ResponseEntity<Group> removeAnnotationFromGroup(@RequestBody @Valid AnnotationToGroupRequest annotationToGroupRequest){
        Group group = groupService.removeAnnotationFromGroup(annotationToGroupRequest.getIdGroup(), annotationToGroupRequest.getIdAnnotation());
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PatchMapping("/join")
    public ResponseEntity<Group> addMemberToGroup(@RequestParam Long idGroup, @RequestParam String password, @RequestParam String userEmail){
        Group group = groupService.joinGroup(idGroup, password, userEmail);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PatchMapping("/leave")
    public ResponseEntity<Group> removeMemberFromGroup(@RequestParam Long idGroup, @RequestParam String userEmail){
        Group group = groupService.leaveGroup(idGroup, userEmail);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping("/member/{userEmail}")
    public List<Group> getGroupsImMember(@PathVariable String userEmail) {
        return groupService.getGroupImMember(userEmail);
    }

    @GetMapping("/owner/{userEmail}")
    public List<Group> getGroupsByOwner(@PathVariable String userEmail){
        return groupService.getGroupsByOwner(userEmail);
    }

    @GetMapping("/member/{userEmail}/{isbn}")
    public List<Group> getGroupsImMemberByIsbn(@PathVariable String userEmail, @PathVariable String isbn) {
        return groupService.getGroupImMemberAndIsbn(userEmail, isbn);
    }
}