package com.yjlee.restapidemo.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjlee.restapidemo.accounts.Account;
import com.yjlee.restapidemo.accounts.AccountSerializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Event {

    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;

    @ManyToOne
    @JsonSerialize(using = AccountSerializer.class)
    private Account manager;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRFT;

    public Event setId(Integer id) {
        this.id = id;
        return this;
    }

    public boolean isBelongToUser(Account user) {
        return this.manager.equals(user);
    }

    public void update() {
        if(this.basePrice == 0 && this.maxPrice == 0) {
            this.free = true;
        } else {
            this.free = false;
        }

        if(this.location == null || this.location.isBlank()) {
            this.offline = false;
        } else {
            this.offline = true;
        }
    }
}
