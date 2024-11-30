package mtm_jointable.entity;

import java.io.Serializable;
import lombok.Setter;

@Setter
public class MemberProductId implements Serializable {

    private String member;
    private String product;
    
}
