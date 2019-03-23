package demo_app.domain;

import demo_app.domain.validation.OnCreate;
import demo_app.domain.validation.OnUpdate;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name="users")
@RequiredArgsConstructor
public class User {

    @Column(updatable = false)
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    @NotNull(groups = OnCreate.class, message = "User name is mandatory.")
    @NotBlank(groups = OnCreate.class, message = "Path value should not be blank.")
    @Size(min = 1,
          max = 255,
          message = "Path length should be between 1 and 255 characters.",
          groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "^(/endpoints/)?[\\p{Alnum}_-]+$",
             message = "Only word characters [a-zA-Z_0-9-] are allowed.",
             groups = {OnCreate.class, OnUpdate.class})
    private String nickname;

    @NotNull(groups = OnCreate.class, message = "User active flag is mandatory.")
    private Boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nickname, user.nickname) &&
               Objects.equals(active, user.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, active);
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", nickname='" + nickname + '\'' +
               ", active=" + active +
               '}';
    }
}
