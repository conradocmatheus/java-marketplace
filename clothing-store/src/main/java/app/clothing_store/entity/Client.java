package app.clothing_store.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long id;

    private String name;

    private String cpf;

    private int age;

    private String phoneNumber;
}
