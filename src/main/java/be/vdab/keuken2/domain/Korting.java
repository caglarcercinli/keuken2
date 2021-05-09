package be.vdab.keuken2.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Korting {
    private int vanafAantal;
    private BigDecimal percentage;

    public Korting(int vanafAantal, BigDecimal percentage) {
        this.vanafAantal = vanafAantal;
        this.percentage = percentage;
    }

    protected Korting() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Korting)) return false;
        Korting korting = (Korting) o;
        return vanafAantal == korting.vanafAantal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vanafAantal);
    }
}
