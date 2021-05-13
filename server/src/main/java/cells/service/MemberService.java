package cells.service;

import cells.exception.ResourceNotFoundException;
import cells.model.Member;
import cells.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository repository;
    private final RedisTemplate<Long, Member> redisTemplate;

    public MemberService(
            MemberRepository repository,
            RedisTemplate<Long, Member> redisTemplate
    ) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    @Cacheable(cacheNames = "members")
    public List<Member> getAll(Member member){
        return repository.findAll();
    }

    @Cacheable(cacheNames = "members", key="#id")
    public Member getById(Long id){
        log.info("fetching book from db");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
    }

    public Member add(Member member) {
        Optional<Member> studentOptional =
                repository.findByEmail(member.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email already taken");
        }
//        redisTemplate.opsForValue().set(member.getId(), member);
        return repository.save(member);
    }

    public void delete(Long id) {
        boolean exist = repository.existsById(id);
        if (!exist) {
            throw new ResourceNotFoundException("Member", "id", id);
        }
        repository.deleteById(id);
    }

    public Member update(Member member) {
        return repository.save(member);
    }

}

