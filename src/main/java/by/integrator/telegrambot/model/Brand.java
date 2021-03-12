package by.integrator.telegrambot.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Table(name = "brand")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "link", nullable = false)
    private String link;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "currentBrand", cascade = CascadeType.ALL)
    private Set<Client> currentClients;
}
