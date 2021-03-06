package cells.domain.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public abstract class EntityAudit<U> {

    @CreatedBy
    @Column(updatable = false, nullable = false)
    protected U createdBy;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    protected Instant createdAt;

    @LastModifiedBy
    protected U editedBy;

    @LastModifiedDate
    protected Instant updatedAt;
}
