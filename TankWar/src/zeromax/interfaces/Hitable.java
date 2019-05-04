package zeromax.interfaces;

import zeromax.domain.Blast;
import zeromax.domain.Bullet;

public interface Hitable {
    int getPosX();

    int getPosY();

    int getX();

    int getY();

    Blast showBlast();

    boolean decreaseHP(Bullet bullet);
}
