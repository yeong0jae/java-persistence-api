package mapping.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @Setter
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public void setTeam(Team team) {
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }
}
