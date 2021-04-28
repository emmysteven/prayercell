package cells.service;

import cells.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import cells.exception.AppException;
import cells.model.Member;
import cells.repository.MemberRepository;


import java.util.List;
import java.util.Optional;

@Repository
public class MemberService {
    private MemberRepository repository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public List<Member> getAllMembers(){
        return repository.findAll();
    }

    public Member getMemberById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
    }

    public Member addMember(Member member) {
        Optional<Member> studentOptional =
                repository.findByEmail(member.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email already taken");
        }
        return repository.save(member);
    }

    public void deleteMember(Long id) {
        boolean exist = repository.existsById(id);
        if (!exist) {
            throw new ResourceNotFoundException("Member", "id", id);
        }
        repository.deleteById(id);
    }

    public Member updateMember(Member member) {
        return repository.save(member);
    }

}

