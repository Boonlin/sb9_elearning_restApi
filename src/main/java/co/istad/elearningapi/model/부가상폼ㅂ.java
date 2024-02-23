package co.istad.elearningapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table()
public class 부가상폼ㅂ {
    @Id
private String use_intt_id;
private String addl_prod_cd;
private String pric_plcy;
private String cacl_meth;
private String svc_str_dt;
private Integer intr_amt;
private Integer monyt_free;
private Integer alot_fee;
@ManyToMany()
Set<청구상세> 청구상세;

}

