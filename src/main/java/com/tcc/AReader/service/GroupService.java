package com.tcc.areader.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.areader.exception.BadRequestException;
import com.tcc.areader.model.Annotation;
import com.tcc.areader.model.Group;
import com.tcc.areader.model.LibraryBook;
import com.tcc.areader.repository.GroupRepository;
import com.tcc.areader.request.CreateGroupRequest;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private LibraryBookService libraryBookService;
    @Autowired
    private AnnotationService annotationService;

    public Group CreateGroup(CreateGroupRequest createGroupRequest) {
        LibraryBook libraryBook = libraryBookService.getLibraryBookById(createGroupRequest.getLibraryBookId()).get();
        if (groupExists(libraryBook))
            throw new BadRequestException("Grupo já existe");
        String password = createGroupRequest.getPassword();
        String owner = libraryBook.getUserEmail();

        Group group = Group.build(null, password, owner, libraryBook, new ArrayList<String>(), new ArrayList<Annotation>());
        return groupRepository.save(group);
    }

    private boolean groupExists(LibraryBook libraryBookParam) {
        return groupRepository.findGroupByLibraryBook(libraryBookParam).isPresent();
    }

    public List<Group> getGroupsByOwner(String userEmail) {
        List<Group> groups = groupRepository.findByOwner(userEmail);
        if (groups.isEmpty())
            throw new BadRequestException("Nenhum grupo encontrado");
        return groups;
    }

    public Optional<Group> getGroupById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent())
            return group;
        else
            throw new BadRequestException("Grupo não encontrado");
    }

    public Group addAnnotationToGroup(Long idGroup, Long idAnnotation) {
        Optional<Group> group = getGroupById(idGroup);
        Annotation annotation = annotationService.getAnnotationById(idAnnotation);
        if (!group.get().getOwner().equals(annotation.getUserEmail()))
            throw new BadRequestException("Anotação e Grupo não pertencem ao mesmo usuário");
        else {

            group.get().getAnnotations().add(annotation);
            return groupRepository.save(group.get());
        }
    }
    public Group removeAnnotationFromGroup(Long idGroup, Long idAnnotation) {
        Optional<Group> group = getGroupById(idGroup);
        Annotation annotation = annotationService.getAnnotationById(idAnnotation);
        if (!group.get().getOwner().equals(annotation.getUserEmail()))
            throw new BadRequestException("Anotação e Grupo não pertencem ao mesmo usuário");
        else {

            group.get().getAnnotations().remove(annotation);
            return groupRepository.save(group.get());
        }
    }

    public Group joinGroup(Long idGroup, String password, String member) {
        Optional<Group> group = getGroupById(idGroup);
        if (!group.get().getPassword().equals(password))
            throw new BadRequestException("Senha incorreta");
        else {
            group.get().getMembers().add(member);
            return groupRepository.save(group.get());
        }
    }

    public Group leaveGroup(Long idGroup, String member) {
        Optional<Group> group = getGroupById(idGroup);
        if (!group.get().getMembers().contains(member))
            throw new BadRequestException("Usuário não pertence ao grupo");
        else {
            group.get().getMembers().remove(member);
            return groupRepository.save(group.get());
        }
    }

    public List<Group> getGroupImMember(String userEmail) {
        List<Group> groups = groupRepository.findByMembersContain(userEmail);
        if (groups.isEmpty())
            throw new BadRequestException("Usuário não entrou em nenhum grupo");
        return groups;
    }

    public List<Group> getGroupImMemberAndIsbn(String userEmail, String isbn) {
        List<Group> groups = groupRepository.findByMembersContain(userEmail);
        groups = groups.stream().filter(group -> group.getLibraryBook().getIsbn().equals(isbn)).toList();
        if (groups.isEmpty())
            throw new BadRequestException("Usuário não entrou em nenhum grupo");
        return groups;
    }
}