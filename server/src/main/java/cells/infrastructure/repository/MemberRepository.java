package cells.infrastructure.repository;

import cells.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.id = ?1")
    Optional<Member> findById(Long id);

    @Query("SELECT m FROM Member m WHERE m.email = ?1")
    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.telephone = ?1")
    Optional<Member> findByTelephone(String telephone);

}


