package lk.ijse.gdse.instritutefirstsemfinal.dto.tm;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectTm {

    private String subjectId;
    private String subjectName;
    private String subjectGrades;
    private String subjectDescription;

    public SubjectTm(String subjectId, String subjectName, String subjectDescription) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
    }
}
