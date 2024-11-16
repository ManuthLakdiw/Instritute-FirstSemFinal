package lk.ijse.gdse.instritutefirstsemfinal.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectDto {

    private String subjectId;
    private String subjectName;
    private String[] subjectGrades;
    private String subjectDescription;

    public SubjectDto(String subjectId, String subjectName, String subjectDescription) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
    }
}
