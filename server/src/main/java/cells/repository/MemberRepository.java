package cells.repository;

import cells.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.email = ?1")
    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.id = ?1")
    Optional<Member> findById(Long id);

}


