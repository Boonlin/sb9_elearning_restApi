package co.istad.elearningapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class 청구상세 {
    @Id
    private String use_intt_id;
    private String clm_ym;
    private String clm_reg_no;
    private Integer item_amt;
    private Integer vat_amt;
    private Integer totl_amt;
    @ManyToMany(mappedBy = "청구상세")
    List<부가상폼ㅂ>부가상폼ㅂ;
}
