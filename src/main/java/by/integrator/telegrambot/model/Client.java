package by.integrator.telegrambot.model;

import javax.persistence.*;

import by.integrator.telegrambot.bot.state.ClientBotState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@Table(name = "client")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "client_bot_state", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ClientBotState clientBotState;

    @Column(name = "phone_number")
    private String phoneNumber;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private User user;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "CurrentBrandId")
    private Brand currentBrand;

    public String getTelegramId() {
        return user.getTelegramId();
    }

}
