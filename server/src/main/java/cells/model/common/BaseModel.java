package cells.model.common;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// FIXME add  extends Auditable<String> and @EqualsAndHashCode(callSuper=true)

@Data
@MappedSuperclass
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = 4044849860361438135L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;
}
