package cells.infrastructure.service;

import cells.application.exception.ResourceNotFoundException;
import cells.domain.entity.Member;
import cells.infrastructure.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

//    @Cacheable(cacheNames = "members")
    public List<Member> getAll(){
        return repository.findAll();
    }

    @Cacheable(cacheNames = "members", key="#id")
    public Member getById(Long id){
        log.info("fetching book from db");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
    }

    public Member add(Member member) {
        Optional<Member> email = repository.findByEmail(member.getEmail());
        if (email.isPresent()){
            throw new IllegalStateException("email already taken");
        }

        Optional<Member> telephone = repository.findByTelephone(member.getTelephone());
        if (telephone.isPresent()){
            throw new IllegalStateException("telephone already taken");
        }
        log.info(email.toString());
        return repository.save(member);
    }

    @CacheEvict(cacheNames = "members", key = "#id")
    public void delete(Long id) {
        boolean exist = repository.existsById(id);
        if (!exist) {
            throw new ResourceNotFoundException("Member", "id", id);
        }
        repository.deleteById(id);
    }

    @CachePut(cacheNames = "members", key="#member.id")
    public Member update(Member member) {
        return repository.save(member);
    }

}

