package app.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("sales")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Field (name) must not be blank")
    private String name;

    @Column(unique = true)
    @CPF(message = "Field (cpf) is not a valid CPF")
    private String cpf;

    @Max(120)
    @Positive(message = "Field (age) must be positive")
    @NotNull(message = "Field (age) must not be null")
    private int age;

    @NotNull(message = "Field (phoneNumber) must not be null")
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{5}-\\d{4}$", message = "Field (phoneNumber) must be of format (XX) XXXXX-XXXX")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Sale> sales;
}
