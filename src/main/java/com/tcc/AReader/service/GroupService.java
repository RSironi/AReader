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

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private LibraryBookService libraryBookService;
    @Autowired
    private AnnotationService annotationService;

    public Group CreateGroup(Long libraryBookId, String pass) {
        LibraryBook libraryBook = libraryBookService.getLibraryBookById(libraryBookId).get();
        if (groupExists(libraryBook))
            throw new BadRequestException("Grupo já existe");
        String password = pass;
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

    private boolean ownerOfAnnotationAndGroup(Optional<Group> group, Annotation annotation) {
        if(group.get().getOwner().equals(annotation.getUserEmail()))
        return true;
    throw new BadRequestException("Anotação e Grupo não pertencem ao mesmo usuário");
    }

    private boolean annotationNotExistsInGroup(Optional<Group> group, Annotation annotation) {
        if(!group.get().getAnnotations().contains(annotation))
        return true;
    throw new BadRequestException("Anotação já existe no grupo");
    }

    public Group addAnnotationToGroup(Long idGroup, Long idAnnotation) {
        Optional<Group> group = getGroupById(idGroup);
        Annotation annotation = annotationService.getAnnotationById(idAnnotation);
        if (ownerOfAnnotationAndGroup(group, annotation) && annotationNotExistsInGroup(group, annotation)) {
            group.get().getAnnotations().add(annotation);
            return groupRepository.save(group.get());
        }
        throw new BadRequestException("Erro ao adicionar anotação ao grupo");
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


    private boolean memberNotInGroup(Optional<Group> group, String member) {
        if(!group.get().getMembers().contains(member))
        return true;    
    throw new BadRequestException("Usuário já pertence ao grupo");
    }

    private boolean passwordMatch(Optional<Group> group, String password) {
        if(group.get().getPassword().equals(password))
        return true;
    throw new BadRequestException("Senha incorreta");
    }

    public Group joinGroup(Long idGroup, String password, String member) {
        Optional<Group> group = getGroupById(idGroup);
        
        if(memberNotInGroup(group, member) && passwordMatch(group, password)){
            group.get().getMembers().add(member);
            return groupRepository.save(group.get());
        }
        throw new BadRequestException("Erro ao entrar no grupo");
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