package mapping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.TableGenerator;
import lombok.Getter;

@Entity
@TableGenerator(
        name = "BOARD_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "BOARD_SEQ", allocationSize = 1)
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "BOARD_SEQ_GENERATOR")
    private Long id;
    
}
