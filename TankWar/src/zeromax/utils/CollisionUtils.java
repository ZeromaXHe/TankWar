package zeromax.utils;

import zeromax.domain.Bullet;
import zeromax.domain.EnemyTank;
import zeromax.domain.MyTank;
import zeromax.interfaces.*;
import zeromax.model.Map;

import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionUtils {
    private CollisionUtils() {

    }

    /**
     * 判断两个矩形是否碰撞
     *
     * @param x1 第一个矩形的 x坐标
     * @param y1 第一个矩形的 y坐标
     * @param w1 第一个矩形的 宽度
     * @param h1 第一个矩形的 高度
     * @param x2 第二个矩形的 x坐标
     * @param y2 第二个矩形的 y坐标
     * @param w2 第二个矩形的 宽度
     * @param h2 第二个矩形的 高度
     * @return 布尔值。true代表碰撞，false表示不碰撞
     */
    private static boolean isCollisionWithRect(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        if (x1 >= x2 && x1 >= x2 + w2) {
            return false;
        } else if (x1 <= x2 && x1 + w1 <= x2) {
            return false;
        } else if (y1 >= y2 && y1 >= y2 + h2) {
            return false;
        } else return !(y1 <= y2 && y1 + h1 <= y2);
    }

    /**
     * 检查碰撞了地图还是移动物体
     *
     * @param moveable 移动物体
     * @param map      地图
     * @param facing   朝向
     * @param distance 移动距离
     * @return 是否碰撞了
     */
    public static boolean collideCheck(Moveable moveable, Map map, Facing facing, CopyOnWriteArrayList<Moveable> listMove, int distance) {

        int[] mapCheck = collideCheckMap(moveable, map, facing, distance);
        int[] listMoveCheck = collideCheckListmove(moveable, facing, listMove, distance);

        if (mapCheck[1] == 1 || listMoveCheck[1] == 1) {
            if (moveable instanceof MyTank || moveable instanceof Bullet || listMoveCheck[1] == 0) {
                switch (facing) {
                    case NORTH:
                        moveable.setPosY(Math.max(mapCheck[0], listMoveCheck[0]));
                        break;
                    case SOUTH:
                        moveable.setPosY(Math.min(mapCheck[0], listMoveCheck[0]));
                        break;
                    case WEST:
                        moveable.setPosX(Math.max(mapCheck[0], listMoveCheck[0]));
                        break;
                    case EAST:
                        moveable.setPosX(Math.min(mapCheck[0], listMoveCheck[0]));
                        break;
                }
            }
            return true;
        } else {
            switch (facing) {
                case NORTH:
                    moveable.setPosY(Math.max(mapCheck[0], listMoveCheck[0]));
                    break;
                case SOUTH:
                    moveable.setPosY(Math.min(mapCheck[0], listMoveCheck[0]));
                    break;
                case WEST:
                    moveable.setPosX(Math.max(mapCheck[0], listMoveCheck[0]));
                    break;
                case EAST:
                    moveable.setPosX(Math.min(mapCheck[0], listMoveCheck[0]));
                    break;
            }
            return false;
        }
    }

    private static int[] collideCheckMap(Moveable moveable, Map map, Facing facing, int distance) {
        int[] collideMapArea = moveCollideMapArea(moveable, facing, distance);
        return endAfterCollideMap(moveable, facing, map, distance, collideMapArea);

    }

    /**
     * 计算与移动物体间可能发生的碰撞，返回可能结束的地点和是否发生碰撞
     *
     * @param moveable 移动物体
     * @param facing   面朝方向
     * @param listMove 所有移动物体的列表
     * @param distance 移动距离
     * @return int[2]。[0]为可能移动的距离，[1]是是否发生碰撞。
     */
    private static int[] collideCheckListmove(Moveable moveable , Facing facing, CopyOnWriteArrayList<Moveable> listMove, int distance) {
        int[] collideBox = moveCollideBox(moveable, facing, distance);

        return endAfterCollide(moveable, facing, listMove, distance, collideBox);
    }

    /**
     * 用来求移动一定距离的物体可能的经过范围，将其想象为一个碰撞箱用以碰撞判定
     *
     * @param moveable 移动的物体
     * @param facing   面朝方向
     * @param distance 移动距离
     * @return 返回一个int[4]数组。其中[0]是碰撞box的x坐标，[1]是y坐标，[2]是碰撞box沿X轴长度，[3]是沿y轴高度。
     */
    private static int[] moveCollideBox(Moveable moveable, Facing facing, int distance) {
        int nowi1 = (moveable.getPosX() + 1);
        int nowj1 = (moveable.getPosY() + 1);
        int nowi2 = (moveable.getPosX() + moveable.getX() - 1);
        int nowj2 = (moveable.getPosY() + moveable.getY() - 1);
        int endi1 = nowi1;
        int endj1 = nowj1;
//        int endi2 = nowi2;
//        int endj2 = nowj2;

        switch (facing) {
            case NORTH: {
                endj1 = (moveable.getPosY() - distance);
//                endj2 = (moveable.getPosY() - distance + moveable.getY());
                return new int[]{endi1, endj1, moveable.getX(), nowj1 - moveable.getPosY()};
            }

            case SOUTH: {
//                endj1 = (moveable.getPosY() + distance);
//                endj2 = (moveable.getPosY() + distance + moveable.getY());

                return new int[]{nowi1, nowj2, moveable.getX(), moveable.getPosY() - nowj1};
            }

            case WEST: {
                endi1 = (moveable.getPosX() - distance);
//                endi2 = (moveable.getPosX() - distance + moveable.getX());
                return new int[]{endi1, endj1, nowi1 - moveable.getPosX(), moveable.getY()};

            }

            case EAST: {
//                endi1 = (moveable.getPosX() + distance);
//                endi2 = (moveable.getPosX() + distance + moveable.getX());
                return new int[]{nowi2, nowj1, moveable.getPosX() - nowi1, moveable.getY()};
            }
            default:
                return new int[4];
        }

    }

    /**
     * 计算用来表示碰撞箱所处位置的地图坐标
     *
     * @param moveable 移动物体
     * @param facing   面朝方向
     * @param distance 移动距离
     * @return int[5]。其中[0]是出发坐标(用于循环初始值)，[1]是结束坐标（用于循环条件），[2]正负一，用来控制计算方向（循环步进），[3][4]存储可能的两个碰撞坐标（最大和最小）
     */
    private static int[] moveCollideMapArea(Moveable moveable, Facing facing, int distance) {
        int posX = moveable.getPosX();
        int posY = moveable.getPosY();
        int x = moveable.getX();
        int y = moveable.getY();

        int nowi1;
        if (posX + 1 < 0) nowi1 = -1;
        else nowi1 = (posX + 1) / Config.TILEX;//开始的左边x坐标

        int nowj1;
        if (posY + 1 < 0) nowj1 = -1;
        else nowj1 = (posY + 1) / Config.TILEY;//开始时上边y坐标

        int nowi2;
        if (posX + x - 1 < 0) nowi2 = -1;
        else nowi2 = (posX + x - 1) / Config.TILEY;

        int nowj2;
        if (posY + y - 1 < 0) nowj2 = -1;
        else nowj2 = (posY + y - 1) / Config.TILEY;

        int endi1 ;
        int endj1 ;
        int endi2 ;
        int endj2 ;
        switch (facing) {
            case NORTH: {
                if (posY - distance < 0) endj1 = -1;
                else endj1 = (posY - distance) / Config.TILEY;
//                if (posY - distance + y < 0) endj2 = -1;
//                else endj2 = (posY - distance + y) / Config.TILEY;

                return new int[] {nowj2, endj1, -1, nowi1, nowi2};
            }
            case SOUTH: {
//                endj1 = (posY + distance) / Config.TILEY;
                endj2 = (posY + distance + y) / Config.TILEY;

                return new int[]{nowj1, endj2, 1, nowi1, nowi2};
            }
            case WEST: {
                if (posX - distance < 0) endi1 = -1;
                else endi1 = (posX - distance) / Config.TILEX;
//                if (posX - distance + x < 0) endi2 = -1;
//                else endi2 = (posX - distance + x) / Config.TILEX;

                return new int[]{nowi2, endi1, -1, nowj1, nowj2};
            }
            case EAST: {
//                endi1 = (posX + distance) / Config.TILEX;
                endi2 = (posX + distance + x) / Config.TILEX;

                return new int[]{nowi1, endi2, 1, nowj1, nowj2};
            }
            default:
                return new int[5];
        }
    }

    /**
     * 用来判断碰撞后移动物体的坐标
     *
     * @param moveable   移动物体
     * @param facing     面朝方向
     * @param listMove   所有移动物体的清单
     * @param distance   无碰撞时的移动距离
     * @param collideBox 碰撞箱
     * @return 返回一个int[2]数组。其中[0]是结束坐标，[1]是是否发生了碰撞，0为没发生，1为发生。
     */
    private static int[] endAfterCollide(Moveable moveable, Facing facing,
                                         CopyOnWriteArrayList<Moveable> listMove,
                                         int distance, int[] collideBox) {
        int[] ans = new int[2];
        ans[0] = endWithoutCol(moveable, facing, distance);
        for (Moveable move : listMove) {
            if (move == moveable) continue;
            if (moveable instanceof Bullet) {
                if (move instanceof Hitable && ((Bullet) moveable).getShotFrom() != move) {


                    if (move instanceof EnemyTank && ((Bullet) moveable).getShotFrom() instanceof EnemyTank) {
                        //这个if是防止敌军互相伤害的判定
                        System.out.println("敌军坦克与敌军子弹碰撞");
                    } else if (move instanceof Bullet && ((Bullet) move).getShotFrom() instanceof EnemyTank && ((Bullet) moveable).getShotFrom() instanceof EnemyTank) {
                        //这个是防止敌军子弹互相抵消的判定
                        System.out.println("敌军子弹相互碰撞");
                    } else if (CollisionUtils.isCollisionWithRect(
                            move.getPosX(), move.getPosY(), move.getX(), move.getY(),
                            collideBox[0], collideBox[1], collideBox[2], collideBox[3])) {

                        ((Bullet) moveable).setToBeCleared(true);
                        ((Bullet) moveable).setHit((Hitable) move);
                        if (move instanceof Bullet) {
                            ((Bullet) move).setToBeCleared(true);
                            ((Bullet) move).setHit((Hitable) moveable);
                        }

                        ans[0] = endingPosAftColSth(moveable, move, facing);
                        ans[1] = 1;
                    }

                }
            } else if (move instanceof Collideable) {
                if (CollisionUtils.isCollisionWithRect(
                        move.getPosX(), move.getPosY(), move.getX(), move.getY(),
                        collideBox[0], collideBox[1], collideBox[2], collideBox[3])) {

                    ans[0] = endingPosAftColSth(moveable, move, facing);
                    ans[1] = 1;
                }
            }
        }
        return ans;
    }

    private static int[] endAfterCollideMap(Moveable moveable, Facing facing, Map map, int distance,
                                            int[] collideMapArea) {
        int[] ans = new int[2];
        Drawable d;
        boolean signal = (facing == Facing.WEST || facing == Facing.EAST);

        for (int i = collideMapArea[0];
             collideMapArea[2] * (collideMapArea[1] - i) >= 0;
             i += collideMapArea[2]) {

            if (signal) d = map.getMapItem(i, collideMapArea[3]);
            else d = map.getMapItem(collideMapArea[3], i);

            if (moveable instanceof Bullet) {
                if (d instanceof Hitable) {
                    ((Bullet) moveable).setToBeCleared(true);
                    ((Bullet) moveable).setHit((Hitable) d);
                    ans[0] = endingPosAftColSth(moveable, (Hitable) d, facing);
                    ans[1] = 1;
                    break;
                }
            } else if (d instanceof Collideable) {
                ans[0] = endingPosAftColSth(moveable, (Collideable) d, facing);
                ans[1] = 1;
                break;
            }

            if (signal) d = map.getMapItem(i, collideMapArea[4]);
            else d = map.getMapItem(collideMapArea[4], i);

            if (moveable instanceof Bullet) {
                if (d instanceof Hitable) {
                    ((Bullet) moveable).setToBeCleared(true);
                    ((Bullet) moveable).setHit((Hitable) d);
                    ans[0] = endingPosAftColSth(moveable, (Hitable) d, facing);
                    ans[1] = 1;
                    break;
                }
            } else if (d instanceof Collideable) {
                ans[0] = endingPosAftColSth(moveable, (Collideable) d, facing);
                ans[1] = 1;
                break;
            }
            if (i == collideMapArea[1])
                ans[0] = endWithoutCol(moveable, facing, distance);
        }
        return ans;
    }

    /**
     * 用来求：在与一个物体发生碰撞后，去碰撞的物体结束碰撞时的位置
     *
     * @param moveable 去碰撞的物体
     * @param t        被碰撞的物体
     * @param facing   去碰撞物体的移动方向
     * @return int型，坐标
     */
    private static int endingPosAftColSth(Moveable moveable, Sizeable t, Facing facing) {
        switch (facing) {
            case NORTH:
                return t.getPosY() + t.getY();
            case SOUTH:
                return t.getPosY() - moveable.getY();
            case WEST:
                return t.getPosX() + t.getX();
            case EAST:
                return t.getPosX() - moveable.getX();
            default:
                return 0;
        }
    }

    private static int endWithoutCol(Moveable moveable, Facing facing, int distance) {
        switch (facing) {
            case NORTH:
                return moveable.getPosY() - distance;
            case SOUTH:
                return moveable.getPosY() + distance;
            case WEST:
                return moveable.getPosX() - distance;
            case EAST:
                return moveable.getPosX() + distance;
            default:
                return 0;
        }
    }

}
