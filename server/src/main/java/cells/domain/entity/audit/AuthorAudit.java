package cells.domain.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdBy", "editedBy"},
        allowGetters = true
)
public abstract class AuthorAudit {

    @CreatedBy
    @Column(updatable = false, nullable = false)
    private Instant createdBy;

    @LastModifiedBy
    @Column(nullable = false)
    private Instant editedBy;

}


