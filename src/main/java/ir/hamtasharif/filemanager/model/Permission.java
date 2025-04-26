package ir.hamtasharif.filemanager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    public enum AccessType {
        READ, WRITE, NONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    @ManyToOne
    private User user;

    @ManyToOne
    private Directory directory;
}
