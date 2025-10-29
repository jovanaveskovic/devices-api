package challenge.devices.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "device")
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    @Enumerated(EnumType.STRING)
    private State state;

    @CreatedDate
    @Column(name = "creation_time", updatable = false, nullable = false)
    private Instant creationTime;

    public enum State {
        Available, In_use, Inactive
    }
}
