package zeromax.interfaces;

import zeromax.domain.Blast;
import zeromax.domain.Bullet;

public interface Hitable extends Sizeable{

    Blast showBlast();

    boolean decreaseHP(Bullet bullet);
}
