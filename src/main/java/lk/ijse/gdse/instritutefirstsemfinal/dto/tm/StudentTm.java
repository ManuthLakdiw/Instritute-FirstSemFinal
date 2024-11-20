package lk.ijse.gdse.instritutefirstsemfinal.dto.tm;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentTm {
    private String id;
    private String name;
    private LocalDate birthday;
    private double admissionFee;
    private String grade;
    private String subjects;
    private String addedBy;

}
